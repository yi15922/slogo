package slogo.compiler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.ResourceBundle;
import slogo.SLogoException;
import slogo.compiler.command.AndCommand;
import slogo.compiler.command.EvaluateNumberCommand;
import slogo.compiler.command.OrCommand;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.model.Turtle;

/**
 * This class reads from a properties file to determine how to handle extra parameters for a
 * given command. The "types" of commands are as follows:
 * 1. Stackable: command calls should be stacked one after the other using the appropriate
 * number of parameters each time. An error should be thrown if an incorrect multiple of parameters
 * is given. Examples: {@code fd 20 30 40} becomes {@code fd 20 fd 30 fd 40}, and {@code towards
 * 10 10 20 20} becomes {@code towards 10 10 towards 20 20}
 *
 * 2. Nestable: command calls should be nested in one another so the parameters are all 'computed'
 * at once. Examples: {@code sum 10 20 30} becomes {@code sum sum 10 20 30}, and {@code and 10 20 30}
 * becomes {@code and and 10 20 30}
 *
 * 3. No parameter: any parameters given should be ignored. Examples: {@code pd, pi, turtles}
 *
 * 4. Equal: the Equal command is a special case. In order to correctly return whether all parameters
 * given are equal, Equal must be run on every pair of parameters, and all of these results must be
 * And'ed together
 *
 * 5. NotEqual: the NotEqual command is also a special case. In order to correctly return whether all
 * parameters given are not equal, NotEqual must be run on every pair of parameters, and all of these
 * results must be Or'ed together
 */
public class GroupHelper {
  private SLogoCommand initCommand;
  private Deque<SLogoToken> parameterQueue;
  private Deque<SLogoToken> functionQueue;
  private Turtle modelTurtle;
  private SLogoTokenMaker tokenMaker;

  private final String DEFAULT_RESOURCE_PACKAGE = "resources.languages.";
  private final String GROUP_TYPE_BUNDLE = "GroupingTypes";

  public GroupHelper(SLogoList tokenList, Turtle modelTurtle) {
    functionQueue = new ArrayDeque<>();
    this.modelTurtle = modelTurtle;
    tokenMaker = new SLogoTokenMaker(new Workspace());
    Deque<SLogoToken> tokenQueue = new ArrayDeque<>(tokenList.getTokenList());
    try {
      initCommand = (SLogoCommand) tokenQueue.poll();
      parameterQueue = tokenQueue;
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid group definition");
    }
  }

  /**
   * Creates a SLogoFunction out of a list of Tokens depending on how the initial command handles
   * multiple parameters. Uses reflection to call a private method read from a properties file.
   * @return - a function that produces the result of running the command with multiple parameters
   * @throws SLogoException - if command type is not recognized
   */
  public SLogoFunction createGroupFunction() throws SLogoException {
    ResourceBundle groupingBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + GROUP_TYPE_BUNDLE);
    String groupingType = groupingBundle.getString(initCommand.toString());
    try {
      Method m = this.getClass().getDeclaredMethod(groupingType);
      return (SLogoFunction) m.invoke(GroupHelper.this);
    }
    catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new SLogoException("Invalid command type");
    }
  }

  // has to evaluate inner commands in order to get correct number of tokens for each command call
  private SLogoFunction stackable() {
    int numParametersExpected = initCommand.getNumExpectedTokens();
    while (! parameterQueue.isEmpty()) {
      functionQueue.add(tokenMaker.make("Command", initCommand.toString()));
      for (int i = 0; i < numParametersExpected; i++) {
        if (parameterQueue.isEmpty()) {
          throw new SLogoException("Invalid group list syntax");
        }
        SLogoToken nextToken = parameterQueue.poll();
        if (! nextToken.isEqualTokenType(new SLogoConstant(0)) && ! nextToken.isEqualTokenType(new SLogoVariable("var")) &&
        ! nextToken.isEqualTokenType(new SLogoList("list"))) {
          nextToken = evaluateInnerCommand(nextToken);
        }
        functionQueue.add(nextToken);
      }
    }
    return new SLogoFunction(functionQueue, modelTurtle);
  }

  // has to evaluate inner commnads to determine how many command calls to place in the queue
  private SLogoFunction nestable() {
    while (! parameterQueue.isEmpty()) {
      functionQueue.addFirst(tokenMaker.make("Command", initCommand.toString()));
      SLogoToken nextToken = parameterQueue.poll();
      if (! nextToken.isEqualTokenType(new SLogoConstant(0)) && ! nextToken.isEqualTokenType(new SLogoVariable("var")) &&
          ! nextToken.isEqualTokenType(new SLogoList("list"))) {
        nextToken = evaluateInnerCommand(nextToken);
      }
      functionQueue.add(nextToken);
    }
    functionQueue.remove(); // there should be params-1 nested command calls
    return new SLogoFunction(functionQueue, modelTurtle);
  }

  // ignores all parameters
  private SLogoFunction noParams() {
    functionQueue.add(initCommand);
    return new SLogoFunction(functionQueue, modelTurtle);
  }

  // a bit complicated: essentially, nested and commands are built that take in equal commands
  // between every pair of tokens
  private SLogoFunction equal() {
    List<SLogoToken> evaluatedParamsList = new ArrayList<>();
    while (! parameterQueue.isEmpty()) {
      SLogoToken nextToken = parameterQueue.poll();
      if (! nextToken.isEqualTokenType(new SLogoConstant(0)) && ! nextToken.isEqualTokenType(new SLogoVariable("var"))) {
        nextToken = evaluateInnerCommand(nextToken);
      }
      evaluatedParamsList.add(nextToken);
    }
    for (int i = 0; i < evaluatedParamsList.size() - 1; i++) {
      for (int j = i+1; j < evaluatedParamsList.size(); j++) {
        functionQueue.addFirst(new AndCommand());
        functionQueue.add(initCommand);
        functionQueue.add(evaluatedParamsList.get(i));
        functionQueue.add(evaluatedParamsList.get(j));
      }
    }
    functionQueue.remove(); // there should be params-1 nested command calls
    return new SLogoFunction(functionQueue, modelTurtle);
  }

  // nested or commands are built that take in notequal commands between every pair of tokens
  private SLogoFunction notEqual() {
    List<SLogoToken> evaluatedParamsList = new ArrayList<>();
    while (! parameterQueue.isEmpty()) {
      SLogoToken nextToken = parameterQueue.poll();
      if (! nextToken.isEqualTokenType(new SLogoConstant(0)) && ! nextToken.isEqualTokenType(new SLogoVariable("var"))) {
        nextToken = evaluateInnerCommand(nextToken);
      }
      evaluatedParamsList.add(nextToken);
    }
    for (int i = 0; i < evaluatedParamsList.size() - 1; i++) {
      for (int j = i+1; j < evaluatedParamsList.size(); j++) {
        functionQueue.addFirst(new OrCommand());
        functionQueue.add(initCommand);
        functionQueue.add(evaluatedParamsList.get(i));
        functionQueue.add(evaluatedParamsList.get(j));
      }
    }
    functionQueue.remove(); // there should be params-1 nested command calls
    return new SLogoFunction(functionQueue, modelTurtle);
  }

  private SLogoToken evaluateInnerCommand(SLogoToken nextToken) throws SLogoException {
    try {
      SLogoCommand innerCommand = (SLogoCommand) nextToken;
      parameterQueue.addFirst(innerCommand);
      parameterQueue.addFirst(new EvaluateNumberCommand());
      return new SLogoFunction(parameterQueue, modelTurtle).runSingleCommand();
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid group list syntax");
    }
  }

}

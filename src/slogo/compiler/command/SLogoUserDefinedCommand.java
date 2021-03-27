package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoVariable;

/**
 * This type of command is unique in that it runs a set of commands specified by the user. Hence,
 * a user-defined command must store the command list and also a variable list with all parameters
 * that the command expects. This command is then stored in the Workspace by the Parser.
 */
public class SLogoUserDefinedCommand extends SLogoCommand {
  private Deque<SLogoToken> tokenQueue;
  private Map<String, Integer> variableMap;

  /**
   * Initializes the command with input name
   */
  public SLogoUserDefinedCommand(String name) throws SLogoException {
    super(name);
    variableMap = new HashMap<>();
    tokenQueue = new ArrayDeque<>();
  }

  /**
   * Takes in a list of expected parameters and commands to be executed. Parameters are added
   * to the expectedParameters list just like a regular command.
   * @param variables - expected parameters
   * @param commands - commands to be run when this command is called
   * @throws SLogoException - if a variable is referenced that is not in the expected parameters
   */
  public void giveParameters(SLogoList variables, SLogoList commands) throws SLogoException {
    for (SLogoToken token : variables.getTokenList()) {
      expectedParameters.add(new SLogoVariable(token.toString()));
      // map variable name to location in expectedParameters
      variableMap.put(token.toString(), expectedParameters.size() - 1);
    }
    tokenQueue = new ArrayDeque<>(commands.getTokenList());
    if (! verifyCommandDefinition()) {
      throw new SLogoException("Invalid command definition");
    }
  }

  /**
   * Runs stored command list
   * @return - result of final command run, or 0 if no commands are run
   * @throws SLogoException - if variable name is referenced that is not in parameter list
   */
  @Override
  public SLogoToken run() throws SLogoException {
    Deque<SLogoToken> replacedCommandQueue = new ArrayDeque<>();
    for (SLogoToken token : tokenQueue) {
      if (token.isEqualTokenType(new SLogoVariable("parameter"))) { // needs variable reference
        if (variableMap.containsKey(token.toString())) {
          token = expectedParameters.get(variableMap.get(token.toString()));
        }
        else {
          throw new SLogoException("Variable name not recognized");
        }
      }
      replacedCommandQueue.add(token);
    }
    System.out.println(replacedCommandQueue);
    return new SLogoFunction(replacedCommandQueue, modelTurtle).run();
  }

  // "runs" the given commands to ensure that the command can be successfully defined.
  // since command syntax is checked at runtime, we need to disable execution of all commands
  // to ensure that checking the syntax won't affect the model
  private boolean verifyCommandDefinition() throws SLogoException {
    Deque<SLogoToken> dummyCommandQueue = new ArrayDeque<>();
    for (SLogoToken token : tokenQueue) {
      if (token.isEqualTokenType(new SLogoVariable("dummy"))) { // needs variable reference
        if (variableMap.containsKey(token.toString())) {
          token = new SLogoConstant(1);
        }
        else {
          return false;
        }
      }
      dummyCommandQueue.add(token);
    }
    try {
      SLogoFunction dummyFunction = new SLogoFunction(dummyCommandQueue, modelTurtle);
      dummyFunction.disableExecution();
      dummyFunction.run();
    }
    catch (SLogoException e) {
      return false;
    }
    return true;
  }
}

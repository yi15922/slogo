package slogo.compiler.token;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import slogo.SLogoException;
import slogo.Turtle;
import slogo.compiler.WorkspaceEntry;
import slogo.compiler.command.SLogoCommand;

/**
 * A {@code Function} is a {@code Token} than extends {@code SLogoRunnable} and
 * {@code WorkspaceEntry}. Contains a collection of {@code Token} objects. This object
 * can also be added as an entry into the {@code Workspace}.
 *
 * This object is runnable and can recursively call other runnable types.
 *
 * @author Yi Chen
 * @author Patrick Liu
 */
public class SLogoFunction extends WorkspaceEntry implements SLogoRunnable {
  protected List<SLogoCommand> runnableCommandList;
  protected Turtle modelTurtle;
  protected Deque<SLogoToken> functionTokens;

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For Function objects, this name will act as the
   * name of the function.
   *
   * @param name - the specified name of the Token
   */
  public SLogoFunction(String name) {
    super(name);
  }

  /**
   * Compiler is responsible for ensuring that the first {@code Token} given to the {@code Function}
   * is a {@code Command} since SLogo syntax dictates that all user interactions are in the form of commands
   * @param initCommand - the first command intended to be run
   * @param parameterTokens - a list of all remaining tokens in the user-entered String
   * @param modelTurtle
   * @throws SLogoException - if there is invalid syntax in the command
   */
  public SLogoFunction(SLogoCommand initCommand, Deque<SLogoToken> parameterTokens,
      Turtle modelTurtle) throws SLogoException  {
    super("Function");
    runnableCommandList = new ArrayList<>();
    this.modelTurtle = modelTurtle;
    parseParameterTokens(initCommand, parameterTokens);
  }

  public SLogoFunction(Deque<SLogoToken> functionTokens, Turtle modelTurtle) {
    super("Function");
    this.functionTokens = functionTokens;
    this.modelTurtle = modelTurtle;
  }

  public SLogoToken runFunction() {
    SLogoToken returnToken = new SLogoConstant(0);
    Deque<SLogoToken> runnableTokens = new ArrayDeque<>(functionTokens);
    while (! runnableTokens.isEmpty()) {
      SLogoCommand nextCommand;
      try {
        nextCommand = (SLogoCommand) runnableTokens.poll();
      }
      catch (ClassCastException e) {
        throw new SLogoException("Invalid syntax");
      }
      returnToken = runCommand(nextCommand, runnableTokens);
    }
    return returnToken;
  }

  private SLogoToken runCommand(SLogoCommand command, Deque<SLogoToken> remainingTokens) {
    command.attachTurtle(modelTurtle);
    while (! command.isReady()) {
      if (remainingTokens.isEmpty()) {
        throw new SLogoException("Invalid syntax");
      }
      SLogoToken nextToken = remainingTokens.poll();
      if (nextToken.isEqualTokenType(new SLogoConstant(0))) {
        double tokenValue = nextToken.getValue();
        nextToken = new SLogoVariable("wrapper", tokenValue);
      }
      if (! command.giveNextExpectedToken(nextToken)) {
        SLogoCommand innerCommand;
        try {
          innerCommand = (SLogoCommand) nextToken;
        }
        catch (ClassCastException e) {
          throw new SLogoException("Invalid syntax");
        }
        SLogoToken resultToken = runCommand(innerCommand, remainingTokens);
        remainingTokens.addFirst(resultToken);
      }
    }
    SLogoToken returnToken = command.run();
    command.resetCommand();
    return returnToken;
  }

  public SLogoToken runSingleCommand() {
    SLogoCommand commandToRun;
    try {
      commandToRun = (SLogoCommand) functionTokens.poll();
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid syntax");
    }
    return runCommand(commandToRun, functionTokens);
  }

  // recursively assembles and runs a command
  // takes in Deque of all remaining Tokens in the user-entered String, polls Tokens that it uses
  // to create parameters for the initial command and all nested commands
  private void parseParameterTokens(SLogoCommand initCommand, Deque<SLogoToken> parameterTokens) throws SLogoException {
    initCommand.attachTurtle(modelTurtle);
    while (! initCommand.isReady()) {
      if (parameterTokens.isEmpty()) {
        throw new SLogoException("Invalid syntax");
      }
      SLogoToken nextToken = parameterTokens.poll();
      if (nextToken.isEqualTokenType(new SLogoConstant(0))) { // wrap constants inside a variable Token
        double tokenValue = nextToken.getValue();
        nextToken = new SLogoVariable("wrapper", tokenValue);
      }
      if (! initCommand.giveNextExpectedToken(nextToken)) {
        try {
          SLogoCommand nextCommand = (SLogoCommand) nextToken;
          nextCommand.attachTurtle(modelTurtle);
          SLogoToken resultToken = new SLogoFunction(nextCommand, parameterTokens, modelTurtle).run();
          parameterTokens.addFirst(resultToken);
          nextCommand.resetCommand();
        }
        catch (ClassCastException e) {
          throw new SLogoException("Invalid syntax"); // received a generic Token, List, or Function
        }
      }
    }
    runnableCommandList.add(initCommand);
  }

  /**
   * Adds the passed {@code Token} to the collection of parameters. This is used to create
   * {@code WorkspaceEntry} objects of parameters of the function. When the function is run,
   * these {@code WorkspaceEntry} objects are set to the values that are passed to them.
   * @param params a {@code Collection} of parameter names.
   */
  public void addParams(Collection<SLogoToken> params) {

  }


  @Override
  public boolean isReady() {
    return false;
  }

  @Override
  public boolean giveNextExpectedToken(SLogoToken token) {
    return false;
  }

  /**
   * Calls {@code run()} recursively to take care of nested {@code SLogoRunnables}.
   * If {@code runnable.isReady()} is true, call {@code run()} on the it and return the return value.
   *
   * If runnable is not ready, call {@code getNextToken()} and check what type of token it is.
   *
   * If it is type {@code Constant} or {@code Variable}, pass it to the SLogoRunnable using
   * {@code runnable.giveNextExpectedToken()} and make recursive {@code run(runnable)} call on it again.
   * If the next token is a generic {@code Token}, check the {@code Workspace} and get the concrete
   * token type. If the type is {@code SLogoRunnable}, make recursive {@code run()} call on the
   * inner runnable, passing the return value to the outer runnable using
   * {@code runnable.giveNextExpectedToken()}.
   *
   * @return the return value of the last runnable object, wrapped as a {@code Constant} token.
   */
  @Override
  public SLogoToken run() {
    SLogoToken resultToken = null;
    for (SLogoCommand command : runnableCommandList) {
      resultToken = command.run();
    }
    return resultToken;
  }

  public void resetFunction() {
    for (SLogoCommand command : runnableCommandList) {
      command.resetCommand();
    }
  }
}
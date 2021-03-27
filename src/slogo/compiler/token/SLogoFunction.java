package slogo.compiler.token;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import slogo.SLogoException;
import slogo.model.Turtle;
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
  protected Turtle modelTurtle;
  protected Deque<SLogoToken> functionTokens;
  protected boolean executeCommands;

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
   * {@code SLogoFunction} takes in a {@code Deque} of {@code Token} objects
   * representing one or more commands entered by the user.
   * @param functionTokens - all tokens entered by the user up to a comment token
   * @param modelTurtle - the {@code Turtle} object containing the model state
   */
  public SLogoFunction(Deque<SLogoToken> functionTokens, Turtle modelTurtle) {
    super("Function");
    this.functionTokens = functionTokens;
    this.modelTurtle = modelTurtle;
    executeCommands = true;
  }

  /**
   * Iterates through the {@code Deque} of {@code Token} objects representing the commands the
   * user has entered, creating and running commands. The method {@code runCommand} takes care
   * of running individual commands.
   * @return - the final command's return value in the form of a {@code Token}
   */
  @Override
  public SLogoToken run() {
    SLogoToken returnToken = new SLogoConstant(0);
    Deque<SLogoToken> runnableTokens = new ArrayDeque<>(functionTokens);
    while (! runnableTokens.isEmpty()) {
      SLogoToken nextToken = runnableTokens.poll();
      if (nextToken.isEqualTokenType(new SLogoFunction("function"))) {
        SLogoFunction nextFunction = (SLogoFunction) nextToken;
        returnToken = nextFunction.run();
      }
      else {
        SLogoCommand nextCommand;
        try {
          nextCommand = (SLogoCommand) nextToken;
        }
        catch (ClassCastException ignore) {
          throw new SLogoException("Invalid syntax in user-entered String");
        }
        returnToken = runCommand(nextCommand, runnableTokens);
      }
    }
    return returnToken;
  }

  /**
   * Calls {@code runCommand()} recursively to take care of nested {@code SLogoRunnables}.
   * If {@code runnable.isReady()} is true, call {@code run()} on the it and return the return value.
   *
   * If runnable is not ready, call {@code getNextToken()} and check what type of token it is.
   *
   * If it is type {@code Constant} or {@code Variable}, pass it to the SLogoRunnable using
   * {@code runnable.giveNextExpectedToken()} and make check if it is ready. Constants are wrapped
   * in a variable token for consistency in parameters.
   * If the type is {@code SLogoRunnable}, make recursive {@code run()} call on the
   * inner runnable, passing the return value to the outer runnable using
   * {@code runnable.giveNextExpectedToken()}.
   *
   * @return the return value of the last runnable object in the form of a {@code Token}
   */
  private SLogoToken runCommand(SLogoCommand command, Deque<SLogoToken> remainingTokens) {
    command.resetCommand();
    command.attachTurtle(modelTurtle);
    while (! command.isReady()) {
      if (remainingTokens.isEmpty()) {
        throw new SLogoException("Invalid command syntax");
      }
      SLogoToken nextToken = remainingTokens.poll();
      if (nextToken.isEqualTokenType(new SLogoConstant(0))) {
        double tokenValue = nextToken.getValue();
        nextToken = new SLogoVariable("wrapper", tokenValue);
      }
      else if (nextToken.isEqualTokenType(new SLogoFunction("function"))) {
        SLogoFunction nextFunction = (SLogoFunction) nextToken;
        nextToken = new SLogoVariable("wrapper", nextFunction.run().getValue());
      }
      if (! command.giveNextExpectedToken(nextToken)) {
        SLogoCommand innerCommand;
        try {
          innerCommand = (SLogoCommand) nextToken;
        }
        catch (ClassCastException e) {
          throw new SLogoException("Invalid inner command syntax");
        }
        SLogoToken resultToken = runCommand(innerCommand, remainingTokens);
        remainingTokens.addFirst(resultToken);
      }
    }
    if (executeCommands) {
      SLogoToken returnToken = command.run();
      return returnToken;
    }
    return new SLogoConstant(1);
  }

  /**
   * This method should be used when only one command should be evaluated instead of the entire
   * {@code Deque} of tokens. This method is especially helpful with the {@code EvaluateNumberCommand}
   * class, which is used when evaluating arguments in a parameter list.
   * @return - the return value of the command in the form of a {@code Token}
   */
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

  /**
   * Enables execution of commands if they were disabled. Commands execute by default.
   */
  public void enableExecution() {
    executeCommands = true;
  }

  /**
   * Disables execution of commands. Needed by user-defined commands, which need to "run"
   * the function to prove the command can be successfully defined, but the commnads should
   * not affect the model.
   */
  public void disableExecution() {
    executeCommands = false;
  }

  /**
   * Inherited from SLogoRunnable, but functions are always ready because they do not take in
   * parameters. This method should not be called.
   * @return - false
   */
  @Override
  public boolean isReady() {
    return false;
  }

  /**
   * Inherited from SLogoRunnable, but functions do not take in parameters as commands do.
   * @param token - a Token expected
   * @return - false
   */
  @Override
  public boolean giveNextExpectedToken(SLogoToken token) {
    return false;
  }



}
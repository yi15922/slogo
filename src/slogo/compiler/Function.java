package slogo.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Stack;
import slogo.SLogoException;
import slogo.compiler.SLogoRunnable;
import slogo.compiler.Token;
import slogo.compiler.command.Command;
import slogo.compiler.command.Constant;

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
public class Function extends Token implements SLogoRunnable {
  protected List<Command> commandList;

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For Function objects, this name will act as the
   * name of the function.
   *
   * @param name - the specified name of the Token
   */
  public Function(String name) {
    super(name);
  }

  /**
   * Compiler is responsible for ensuring that the first {@code Token} given to the {@code Function}
   * is a {@code Command} since SLogo syntax dictates that all user interactions are in the form of commands
   * @param initCommand - the first command intended to be run
   * @param parameterTokens - a list of all remaining tokens in the user-entered String
   * @throws SLogoException - if there is invalid syntax in the command
   */
  public Function(Command initCommand, List<Token> parameterTokens) throws SLogoException  {
    super("Function");
    commandList = new ArrayList<>();
    Stack<Token> tokenStack = new Stack<>();
    for (int i = parameterTokens.size() - 1; i >= 0; i--) {
      tokenStack.push(parameterTokens.get(i));
    }
    parseParameterTokens(initCommand, tokenStack);
  }

  // todo: clean up the competing constructors, finalize stack vs. list for parameters
  public Function(Command initCommand, Stack<Token> parameterTokens) throws SLogoException {
    super("Function");
    commandList = new ArrayList<>();
    parseParameterTokens(initCommand, parameterTokens);
  }

  // recursively assembles and runs a command
  // takes in Stack of all remaining Tokens in the user-entered String, pops off Tokens that it uses
  // to create parameters for the initial command and all nested commands
  private void parseParameterTokens(Command initCommand, Stack<Token> parameterTokens) throws SLogoException {
    while (! initCommand.isReady()) {
      Token nextToken = parameterTokens.pop();
      if (nextToken.isEqualTokenType(new Constant(0))) { // wrap constants inside a variable Token
        double tokenValue = nextToken.getValue();
        nextToken = new Variable("wrapper", tokenValue);
      }
      if (! initCommand.giveNextExpectedToken(nextToken)) {
        try {
          Token resultToken = new Function((Command) nextToken, parameterTokens).run();
          parameterTokens.push(resultToken);
        }
        catch (ClassCastException e) {
          throw new SLogoException("Invalid syntax"); // received a generic Token, List, or Function
        }
      }
    }
    commandList.add(initCommand);
  }

  /**
   * Adds the passed {@code Token} to the collection of parameters. This is used to create
   * {@code WorkspaceEntry} objects of parameters of the function. When the function is run,
   * these {@code WorkspaceEntry} objects are set to the values that are passed to them.
   * @param params a {@code Collection} of parameter names.
   */
  public void addParams(Collection<Token> params) {

  }


  @Override
  public boolean isReady() {
    return false;
  }

  @Override
  public boolean giveNextExpectedToken(Token token) {
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
  public Token run() {
    Token resultToken = null;
    for (Command command : commandList) {
      resultToken = command.run();
    }
    return resultToken;
  }
}
package slogo.compiler;

import java.util.Collection;
import slogo.SLogoException;
import slogo.compiler.SLogoRunnable;
import slogo.compiler.Token;
import slogo.compiler.command.Command;

/**
 * A {@code Function} is a {@code Token} than extends {@code SLogoRunnable} and
 * {@code WorkspaceEntry}. Contains a collection of {@code Token} objects. This object
 * can also be added as an entry into the {@code Workspace}.
 *
 * This object is runnable and can recursively call other runnable types.
 *
 * @author Yi Chen
 */
public class Function extends Token implements SLogoRunnable {
  protected Collection<Token> tokenList;
  protected Collection<Command> commandList;

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
   * @param initCommand
   * @param parameterTokens
   * @throws SLogoException
   */
  public Function(Command initCommand, Collection<Token> parameterTokens) throws SLogoException  {
    super("Function");
    tokenList = parameterTokens;
  }

  private void parseParameterTokens(Command initCommand, Collection<Token> parameterTokens) throws SLogoException {

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
    return null;
  }
}
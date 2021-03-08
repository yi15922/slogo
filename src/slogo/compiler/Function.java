package slogo.compiler;

import java.util.Collection;
import slogo.compiler.SLogoRunnable;
import slogo.compiler.Token;

/**
 * A {@code Function} is a {@code Token} than extends {@code SLogoRunnable} and
 * {@code WorkspaceEntry}. Contains a collection of {@code Token} objects. This object
 * can also be added as an entry into the {@code Workspace}.
 *
 * This object is runnable and can recursively call other runnable types.
 *
 * @author Yi Chen
 */
public interface Function extends SLogoRunnable, slogo.WorkspaceEntry {

  /**
   * Adds the passed {@code Token} to the collection of parameters. This is used to create
   * {@code WorkspaceEntry} objects of parameters of the function. When the function is run,
   * these {@code WorkspaceEntry} objects are set to the values that are passed to them.
   * @param params a {@code Collection} of parameter names.
   */
  public void addParams(Collection<Token> params);


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
  public Token run();
}
package slogo;

/**
 * An interface that marks an object as runnable and expects 0 or more parameters.
 *
 * @author Patrick Liu
 * @author Yi Chen
 */
public interface SLogoRunnable {

  /**
   * Indicates whether a {@code SLogoRunnable} is ready to be run based on whether it has the
   * correct parameters. A runnable is initialized with no parameters by the {@code Parser} class,
   * but the {@code Function} class will continuously call {@code isReady()} on each of its runnable
   * objects and give them parameters until they are ready to be run.
   *
   * @return - true if the given parameters match the expected number and type(s), false otherwise
   */
  public boolean isReady();

  /**
   * Gives the runnable the next {@code Token} it expects as a parameter. For example, the {@code
   * fd} command expects a constant or variable, so calling this method with one of those {@code
   * Token} types will cause {@code isReady()} to return true and allow the command to be properly
   * run. By contract, it is the {@code Function} class's responsibility to call this method with a
   * proper {@code Token} type (i.e. by wrapping return values from a command into a {@code
   * Constant} object).
   *
   * @param token - a Token expected by the Command as a parameter
   */
  public boolean giveNextExpectedToken(Token token);

  /**
   * Depending on the concrete implementation, this either performs a single action or is a
   * recursive method that runs all nested {@code SLogoRunnable} type objects.
   *
   * @return the return value of the last {@code SLogoRunnable} object ran, wrapped in a {@code
   * Constant} token object.
   */
  public Token run();
}
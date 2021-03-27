package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of calculating the sine of an input.
 */
public class SineCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public SineCommand() {
    super("Sine");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  /**
   * Runs command
   * @return - sine of input
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.sin(Math.toRadians(expectedParameters.get(0).getValue())));
  }
}

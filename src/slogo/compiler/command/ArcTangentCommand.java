package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of calculating the arc tangent of an input.
 */
public class ArcTangentCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public ArcTangentCommand() {
    super("ArcTangent");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  /**
   * Runs command
   * @return - arc tangent of input
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.atan(Math.toRadians(expectedParameters.get(0).getValue())));
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning the exponent calculation given
 * a base and exponent.
 */
public class PowerCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public PowerCommand() {
    super("Power");
    expectedParameters.add(new SLogoVariable("base"));
    expectedParameters.add(new SLogoVariable("exponent"));
  }

  /**
   * Runs command
   * @return - base to the power of the exponent
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.pow(expectedParameters.get(0).getValue(), expectedParameters.get(1).getValue()));
  }
}

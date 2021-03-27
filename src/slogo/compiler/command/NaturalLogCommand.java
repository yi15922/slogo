package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of calculating the natural log of an input.
 */
public class NaturalLogCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public NaturalLogCommand() {
    super("NaturalLog");
    expectedParameters.add(new SLogoVariable("expr"));
  }

  /**
   * Runs command
   * @return - natural log of input
   * @throws SLogoException - if input is not positive
   */
  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() <= 0) {
      throw new SLogoException("Invalid arithmetic: log of non-positive number");
    }
    return new SLogoConstant(Math.log(expectedParameters.get(0).getValue()));
  }
}

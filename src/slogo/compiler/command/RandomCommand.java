package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning a random value below a specified
 * threshold.
 */
public class RandomCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public RandomCommand() {
    super("Random");
    expectedParameters.add(new SLogoVariable("max"));
  }

  /**
   * Runs command
   * @return - random value strictly less than input
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.max(Math.random() * expectedParameters.get(0).getValue(), 0));
  }
}

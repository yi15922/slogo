package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning a turtle to the center of the screen.
 */
public class HomeCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public HomeCommand() {
    super("Home");
  }

  /**
   * Runs command
   * @return - distance turtle moved
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.home());
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning whether the turtle is visible.
 */
public class IsShowingCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public IsShowingCommand() {
    super("IsShowing");
  }

  /**
   * Runs command
   * @return - 1 if turtle is showing, 0 if not
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.showingP());
  }
}

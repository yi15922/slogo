package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of clearing all turtle paths and sending
 * them back to their original position.
 */
public class ClearScreenCommand extends SLogoCommand {

  /**
   * Initializes the command with name and no expected parameters
   */
  public ClearScreenCommand() {
    super("ClearScreen");
  }

  /**
   * Runs command
   * @return - result of clearing screen
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.clearScreen());
  }
}

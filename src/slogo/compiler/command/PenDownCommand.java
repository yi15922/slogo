package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of setting the turtle pen down.
 */
public class PenDownCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public PenDownCommand() {
    super("PenDown");
  }

  /**
   * Runs command
   * @return - result of putting pen down
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.penDown());
  }
}

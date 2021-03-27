package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returns the total number of turtles created.
 */
public class TurtlesCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public TurtlesCommand() {
    super("Turtles");
  }

  /**
   * Runs command
   * @return - number of turtles created
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.turtles());
  }
}

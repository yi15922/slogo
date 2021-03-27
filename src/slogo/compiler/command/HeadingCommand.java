package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning the heading of a turtle.
 */
public class HeadingCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public HeadingCommand() {
    super("Heading");
  }

  /**
   * Runs command
   * @return - the heading of the turtle in degrees
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.heading());
  }
}

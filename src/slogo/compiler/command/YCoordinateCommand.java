package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning the turtle's y-coordinate.
 */
public class YCoordinateCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public YCoordinateCommand() {
    super("YCoordinate");
  }

  /**
   * Runs command
   * @return - y-coordinate of turtle
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.yCor());
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning the turtle's x-coordinate.
 */
public class XCoordinateCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public XCoordinateCommand() {
    super("XCoordinate");
  }

  /**
   * Runs command
   * @return - x-coordinate of turtle
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.xCor());
  }
}

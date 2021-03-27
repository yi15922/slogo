package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning the index of the turtle shape.
 */
public class GetShapeCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public GetShapeCommand() {
    super("GetShape");
  }

  /**
   * Runs command
   * @return - index of turtle shape
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.shape());
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of return the index of the pen.
 */
public class GetPenColorCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public GetPenColorCommand() {
    super("GetPenColor");
  }

  /**
   * Runs command
   * @return - index of pen color
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.penColor());
  }
}

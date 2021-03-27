package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of changing the turtle's shape.
 */
public class SetShapeCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public SetShapeCommand() {
    super("SetShape");
    expectedParameters.add(new SLogoVariable("index"));
  }

  /**
   * Runs command
   * @return - given index
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.setShape((int) expectedParameters.get(0).getValue()));
  }
}

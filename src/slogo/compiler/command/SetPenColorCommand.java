package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of changing the pen color.
 */
public class SetPenColorCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public SetPenColorCommand() {
    super("SetPenColor");
    expectedParameters.add(new SLogoVariable("index"));
  }

  /**
   * Runs command
   * @return - given index
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.setPenColor((int) expectedParameters.get(0).getValue()));
  }
}

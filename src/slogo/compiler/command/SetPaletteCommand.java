package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of changing the color of a member of the palette.
 */
public class SetPaletteCommand extends SLogoCommand {

  /**
   * Initializes the command with name and four expected parameters
   */
  public SetPaletteCommand() {
    super("SetPalette");
    expectedParameters.add(new SLogoVariable("index"));
    expectedParameters.add(new SLogoVariable("r"));
    expectedParameters.add(new SLogoVariable("g"));
    expectedParameters.add(new SLogoVariable("b"));
  }

  /**
   * Calls Turtle method to set the given index's color to the rgb values specified.
   * The command does not error check the rgb values (which must be in the range 0-255 inclusive),
   * so it is up to the Turtle class to do so.
   * @return - the result of the Turtle set palette method
   */
  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method
    return new SLogoConstant(expectedParameters.get(0).getValue());
  }
}

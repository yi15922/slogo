package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of turning the turtles counter-clockwise.
 */
public class LeftCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public LeftCommand() {
    super("Left");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  /**
   * Runs command
   * @return - degrees turned
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.left(expectedParameters.get(0).getValue()));
  }

}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of turning the turtles clockwise.
 */
public class RightCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public RightCommand() {
    super("Right");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  /**
   * Runs command
   * @return - degrees turned by turtle
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.right(expectedParameters.get(0).getValue()));
  }

}

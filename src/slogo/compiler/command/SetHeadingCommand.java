package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of manually setting the turtle's heading.
 */
public class SetHeadingCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public SetHeadingCommand() {
    super("SetHeading");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  /**
   * Runs command
   * @return - degrees turned by turtle
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.setHeading(expectedParameters.get(0).getValue()));
  }

}

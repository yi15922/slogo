package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of manually setting a turtle's position.
 */
public class SetPositionCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public SetPositionCommand() {
    super("SetPosition");
    expectedParameters.add(new SLogoVariable("x"));
    expectedParameters.add(new SLogoVariable("y"));
  }

  /**
   * Runs command
   * @return - distance moved by turtle
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.setXY(expectedParameters.get(0).getValue(), expectedParameters.get(1).getValue()));
  }
}

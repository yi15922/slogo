package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of rotating the turtle to face a point.
 */
public class SetTowardsCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public SetTowardsCommand() {
    super("SetTowards");
    expectedParameters.add(new SLogoVariable("x"));
    expectedParameters.add(new SLogoVariable("y"));
  }

  /**
   * Runs command
   * @return - degrees turned by turtle
   */
  @Override
  public SLogoToken run() throws SLogoException {
    System.out.println("Setting heading towards (" + expectedParameters.get(0).getValue() + ", " + expectedParameters.get(1).getValue() + ")");
    return new SLogoConstant(modelTurtle.towards(expectedParameters.get(0).getValue(), expectedParameters.get(0).getValue()));
  }
}

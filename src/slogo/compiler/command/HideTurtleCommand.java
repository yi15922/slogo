package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of causing the turtle to not be visible.
 */
public class HideTurtleCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public HideTurtleCommand() {
    super("HideTurtle");
  }

  /**
   * Runs command
   * @return - result of hiding the turtle
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.hideTurtle());
  }
}

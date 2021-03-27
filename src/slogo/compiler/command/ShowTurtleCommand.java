package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of making the turtle visible.
 */
public class ShowTurtleCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public ShowTurtleCommand() {
    super("ShowTurtle");
  }

  /**
   * Runs command
   * @return - result of showing turtle
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.showTurtle());
  }
}

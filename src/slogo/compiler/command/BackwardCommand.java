package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of moving the turtles backwards.
 */
public class BackwardCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public BackwardCommand() {
    super("Backward");
    expectedParameters.add(new SLogoVariable("pixels"));
  }

  /**
   * Runs command
   * @return - amount turtle moved
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.back(expectedParameters.get(0).getValue()));
  }

}

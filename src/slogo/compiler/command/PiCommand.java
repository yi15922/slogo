package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning the mathematical value pi.
 */
public class PiCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public PiCommand() {
    super("Pi");
  }

  /**
   * Runs command
   * @return - mathematical value of pi
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.PI);
  }
}

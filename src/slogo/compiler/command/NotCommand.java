package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning the logical inverse of an input.
 */
public class NotCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public NotCommand() {
    super("Not");
    expectedParameters.add(new SLogoVariable("test"));
  }

  /**
   * Runs command
   * @return - 1 if the input is 0, 0 otherwise
   */
  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() == 0) {
      return new SLogoConstant(1.0);
    }
    else {
      return new SLogoConstant(0.0);
    }
  }

}

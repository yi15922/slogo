package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of logically or-ing two inputs together.
 */
public class OrCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public OrCommand() {
    super("Or");
    expectedParameters.add(new SLogoVariable("test1"));
    expectedParameters.add(new SLogoVariable("test2"));
  }

  /**
   * Runs command
   * @return - 1 if either input is non-zero, 0 otherwise
   */
  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() == 0 && expectedParameters.get(1).getValue() == 0) {
      return new SLogoConstant(0.0);
    }
    else {
      return new SLogoConstant(1.0);
    }
  }

}

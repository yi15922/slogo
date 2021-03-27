package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of logically and-ing two inputs together.
 */
public class AndCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public AndCommand() {
    super("And");
    expectedParameters.add(new SLogoVariable("test1"));
    expectedParameters.add(new SLogoVariable("test2"));
  }

  /**
   * Runs the command and returns the result of the logic expression
   * @return - 1 if the two inputs are non-zero, 0 otherwise
   */
  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() * expectedParameters.get(1).getValue() != 0) {
      return new SLogoConstant(1.0);
    }
    else {
      return new SLogoConstant(0.0);
    }
  }

}

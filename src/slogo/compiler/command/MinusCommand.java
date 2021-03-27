package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning the opposite sign of an input.
 */
public class MinusCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public MinusCommand() {
    super("Minus");
    expectedParameters.add(new SLogoVariable("expr"));
  }

  /**
   * Runs command
   * @return - the input times negative one
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(-1 * expectedParameters.get(0).getValue());
  }
}

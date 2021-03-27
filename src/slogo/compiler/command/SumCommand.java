package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of adding two inputs together.
 */
public class SumCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public SumCommand() {
    super("Sum");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  /**
   * Runs command
   * @return - sum of inputs
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(expectedParameters.get(0).getValue() + expectedParameters.get(1).getValue());
  }
}

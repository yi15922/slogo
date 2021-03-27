package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of modulo given two inputs.
 */
public class RemainderCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public RemainderCommand() {
    super("Remainder");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  /**
   * Runs command
   * @return - remainder when dividing first input by second input
   * @throws SLogoException - if second input is 0
   */
  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(1).getValue() == 0) {
      throw new SLogoException("Invalid arithmetic: division by zero");
    }
    return new SLogoConstant(expectedParameters.get(0).getValue() % expectedParameters.get(1).getValue());
  }
}

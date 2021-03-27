package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of subtracting one input from the other.
 */
public class DifferenceCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public DifferenceCommand() {
    super("Difference");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  /**
   * Runs command
   * @return - difference between two inputs
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(expectedParameters.get(0).getValue() - expectedParameters.get(1).getValue());
  }
}

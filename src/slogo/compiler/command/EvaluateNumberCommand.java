package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

/**
 * Helper class that evaluates the given tokens into a number. Useful for evaluating
 * parameters, such as for a loop command.
 */
public class EvaluateNumberCommand extends SLogoCommand {

  /**
   * Initializes the command with name and one expected parameter
   */
  public EvaluateNumberCommand() {
    super("EvaluateNumber");
    expectedParameters.add(new SLogoVariable("number to evaluate"));
  }

  /**
   * Will store the result of the next command
   * @return - the value of the expression immediately following the EvaluateNumberCommand
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(expectedParameters.get(0).getValue());
  }
}

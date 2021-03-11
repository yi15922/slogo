package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

/**
 * Helper class that evaluates the given tokens into a number but does not affect the model
 */
public class EvaluateNumberCommand extends SLogoCommand {

  public EvaluateNumberCommand() {
    super("EvaluateNumber");
    expectedParameters.add(new SLogoVariable("number to evaluate"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(expectedParameters.get(0).getValue());
  }
}

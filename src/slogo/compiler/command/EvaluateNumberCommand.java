package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;

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

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;

public class SumCommand extends SLogoCommand {

  public SumCommand() {
    super("Sum");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(expectedParameters.get(0).getValue() + expectedParameters.get(1).getValue());
  }
}

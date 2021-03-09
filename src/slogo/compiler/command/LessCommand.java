package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;

public class LessCommand extends SLogoCommand {

  public LessCommand() {
    super("Less");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() < expectedParameters.get(1).getValue()) {
      return new SLogoConstant(1.0);
    }
    else {
      return new SLogoConstant(0.0);
    }
  }
}

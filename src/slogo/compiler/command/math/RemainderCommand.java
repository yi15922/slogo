package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class RemainderCommand extends SLogoCommand {

  public RemainderCommand() {
    super("Remainder");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    try {
      return new SLogoConstant(expectedParameters.get(0).getValue() % expectedParameters.get(1).getValue());
    }
    catch (ArithmeticException e) { // division by 0
      throw new SLogoException("Invalid arithmetic: division by zero");
    }
  }
}

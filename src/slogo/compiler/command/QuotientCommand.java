package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class QuotientCommand extends SLogoCommand {

  public QuotientCommand() {
    super("Quotient");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(1).getValue() == 0) {
      throw new SLogoException("Invalid arithmetic: division by zero");
    }
    return new SLogoConstant(expectedParameters.get(0).getValue() / expectedParameters.get(1).getValue());
  }
}

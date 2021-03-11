package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class DifferenceCommand extends SLogoCommand {

  public DifferenceCommand() {
    super("Difference");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(expectedParameters.get(0).getValue() - expectedParameters.get(1).getValue());
  }
}

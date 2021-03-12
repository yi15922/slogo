package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

public class MinusCommand extends SLogoCommand {

  public MinusCommand() {
    super("Minus");
    expectedParameters.add(new SLogoVariable("expr"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(-1 * expectedParameters.get(0).getValue());
  }
}

package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class PowerCommand extends SLogoCommand {

  public PowerCommand() {
    super("Power");
    expectedParameters.add(new SLogoVariable("base"));
    expectedParameters.add(new SLogoVariable("exponent"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.pow(expectedParameters.get(0).getValue(), expectedParameters.get(1).getValue()));
  }
}

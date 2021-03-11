package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class TangentCommand extends SLogoCommand {

  public TangentCommand() {
    super("Tan");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.tan(Math.toRadians(expectedParameters.get(0).getValue())));
  }
}

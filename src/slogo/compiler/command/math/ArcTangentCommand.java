package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class ArcTangentCommand extends SLogoCommand {

  public ArcTangentCommand() {
    super("ArcTangent");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.atan(Math.toRadians(expectedParameters.get(0).getValue())));
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class TangentCommand extends SLogoCommand {

  public TangentCommand() {
    super("Tangent");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.tan(Math.toRadians(expectedParameters.get(0).getValue())));
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class SetPositionCommand extends SLogoCommand {

  public SetPositionCommand() {
    super("Set XY");
    expectedParameters.add(new SLogoVariable("x"));
    expectedParameters.add(new SLogoVariable("y"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.setXY(expectedParameters.get(0).getValue(), expectedParameters.get(1).getValue()));
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

public class SetPenColorCommand extends SLogoCommand {

  public SetPenColorCommand() {
    super("SetPenColor");
    expectedParameters.add(new SLogoVariable("index"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.setPenColor((int) expectedParameters.get(0).getValue()));
  }
}

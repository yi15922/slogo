package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

public class SetPenSizeCommand extends SLogoCommand {

  public SetPenSizeCommand() {
    super("SetPenSize");
    expectedParameters.add(new SLogoVariable("pixels"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.setPenSize(expectedParameters.get(0).getValue()));
  }
}

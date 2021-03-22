package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;

public class GetShapeCommand extends SLogoCommand {

  public GetShapeCommand() {
    super("GetShape");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.shape());
  }
}

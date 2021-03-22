package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;

public class GetPenColorCommand extends SLogoCommand {

  public GetPenColorCommand() {
    super("GetPenColor");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.penColor());
  }
}

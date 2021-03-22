package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

public class SetShapeCommand extends SLogoCommand {

  public SetShapeCommand() {
    super("SetShape");
    expectedParameters.add(new SLogoVariable("index"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method
    return new SLogoConstant(expectedParameters.get(0).getValue());
  }
}

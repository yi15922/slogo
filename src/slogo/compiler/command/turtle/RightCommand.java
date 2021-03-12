package slogo.compiler.command.turtle;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class RightCommand extends SLogoCommand {

  public RightCommand() {
    super("Right");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.right(expectedParameters.get(0).getValue()));
  }

}

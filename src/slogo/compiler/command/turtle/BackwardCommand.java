package slogo.compiler.command.turtle;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class BackwardCommand extends SLogoCommand {

  public BackwardCommand() {
    super("Backward");
    expectedParameters.add(new SLogoVariable("pixels"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.back(expectedParameters.get(0).getValue()));
  }

}

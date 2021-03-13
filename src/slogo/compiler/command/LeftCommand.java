package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class LeftCommand extends SLogoCommand {

  public LeftCommand() {
    super("Left");
    expectedParameters.add(new SLogoVariable("degrees"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.left(expectedParameters.get(0).getValue()));
  }

}

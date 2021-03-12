package slogo.compiler.command.turtle;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

public class PenUpCommand extends SLogoCommand {

  public PenUpCommand() {
    super("Pen Up");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.penUp());
  }
}

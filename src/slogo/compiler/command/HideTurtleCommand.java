package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

public class HideTurtleCommand extends SLogoCommand {

  public HideTurtleCommand() {
    super("HideTurtle");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.hideTurtle());
  }
}

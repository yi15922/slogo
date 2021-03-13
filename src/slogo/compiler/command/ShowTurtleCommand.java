package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

public class ShowTurtleCommand extends SLogoCommand {

  public ShowTurtleCommand() {
    super("Show Turtle");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.showTurtle());
  }
}

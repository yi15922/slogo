package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;

public class TurtlesCommand extends SLogoCommand {

  public TurtlesCommand() {
    super("Turtles");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.turtles());
  }
}

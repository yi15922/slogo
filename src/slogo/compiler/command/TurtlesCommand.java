package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoToken;

public class TurtlesCommand extends SLogoCommand {

  public TurtlesCommand() {
    super("Turtles");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method
    return null;
  }
}

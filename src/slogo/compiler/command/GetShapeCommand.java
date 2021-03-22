package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoToken;

public class GetShapeCommand extends SLogoCommand {

  public GetShapeCommand() {
    super("GetShape");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method
    return null; // todo: write test method
  }
}

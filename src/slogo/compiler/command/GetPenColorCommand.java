package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoToken;

public class GetPenColorCommand extends SLogoCommand {

  public GetPenColorCommand() {
    super("Get Pen Color");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method
    return null; // todo: write test method
  }
}

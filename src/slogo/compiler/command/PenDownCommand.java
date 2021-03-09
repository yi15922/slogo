package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;

public class PenDownCommand extends SLogoCommand {

  public PenDownCommand() {
    super("Pen Down");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method
    System.out.println("Putting pen down");
    return new SLogoConstant(1);
  }
}

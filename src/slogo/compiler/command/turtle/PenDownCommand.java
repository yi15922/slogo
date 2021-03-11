package slogo.compiler.command.turtle;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.command.SLogoCommand;

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

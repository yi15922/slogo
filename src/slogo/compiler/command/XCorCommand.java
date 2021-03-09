package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;

public class XCorCommand extends SLogoCommand {

  public XCorCommand() {
    super("XCor");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method
    System.out.println("Returning turtle's x coordinate");
    return new SLogoConstant(1.0); // todo: replace with x coordinate
  }
}

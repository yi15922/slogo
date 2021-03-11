package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;

public class XCoordinateCommand extends SLogoCommand {

  public XCoordinateCommand() {
    super("XCor");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method
    System.out.println("Returning turtle's x coordinate");
    return new SLogoConstant(1.0); // todo: replace with x coordinate
  }
}

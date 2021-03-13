package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

public class XCoordinateCommand extends SLogoCommand {

  public XCoordinateCommand() {
    super("XCor");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call slogo.Turtle method
    System.out.println("Returning turtle's x coordinate");
    return new SLogoConstant(1.0); // todo: replace with x coordinate
  }
}

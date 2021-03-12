package slogo.compiler.command.turtle;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.command.SLogoCommand;

public class YCoordinateCommand extends SLogoCommand {

  public YCoordinateCommand() {
    super("YCor");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(modelTurtle.ycor());
  }
}

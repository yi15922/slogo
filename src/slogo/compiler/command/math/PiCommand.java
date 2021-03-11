package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.command.SLogoCommand;

public class PiCommand extends SLogoCommand {

  public PiCommand() {
    super("Pi");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.PI);
  }
}

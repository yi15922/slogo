package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class NotCommand extends SLogoCommand {

  public NotCommand() {
    super("Not");
    expectedParameters.add(new SLogoVariable("test"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() == 0) {
      return new SLogoConstant(1.0);
    }
    else {
      return new SLogoConstant(0.0);
    }
  }

}

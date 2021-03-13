package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class OrCommand extends SLogoCommand {

  public OrCommand() {
    super("Or");
    expectedParameters.add(new SLogoVariable("test1"));
    expectedParameters.add(new SLogoVariable("test2"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() * expectedParameters.get(1).getValue() == 0) {
      return new SLogoConstant(1.0);
    }
    else {
      return new SLogoConstant(0.0);
    }
  }

}

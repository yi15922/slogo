package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class RandomCommand extends SLogoCommand {

  public RandomCommand() {
    super("Random");
    expectedParameters.add(new SLogoVariable("max"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.max(Math.random() * expectedParameters.get(0).getValue(), 0));
  }
}

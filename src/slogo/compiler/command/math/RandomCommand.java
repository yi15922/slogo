package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class RandomCommand extends SLogoCommand {

  public RandomCommand() {
    super("Random");
    expectedParameters.add(new SLogoVariable("max"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoConstant(Math.random() * expectedParameters.get(0).getValue());
  }
}

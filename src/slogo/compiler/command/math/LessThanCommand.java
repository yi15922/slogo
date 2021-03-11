package slogo.compiler.command.math;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class LessThanCommand extends SLogoCommand {

  public LessThanCommand() {
    super("Less");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() < expectedParameters.get(1).getValue()) {
      return new SLogoConstant(1.0);
    }
    else {
      return new SLogoConstant(0.0);
    }
  }

  public static class MinusCommand extends SLogoCommand {

    public MinusCommand() {
      super("Minus");
      expectedParameters.add(new SLogoVariable("expr"));
    }

    @Override
    public SLogoToken run() throws SLogoException {
      return new SLogoConstant(-1 * expectedParameters.get(0).getValue());
    }
  }

  public static class NaturalLogCommand extends SLogoCommand {

    public NaturalLogCommand() {
      super("NaturalLog");
      expectedParameters.add(new SLogoVariable("expr"));
    }

    @Override
    public SLogoToken run() throws SLogoException {
      return new SLogoConstant(Math.log(expectedParameters.get(0).getValue()));
    }
  }
}

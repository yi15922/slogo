package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;

public class TowardsCommand extends SLogoCommand {

  public TowardsCommand() {
    super("Towards");
    expectedParameters.add(new SLogoVariable("x"));
    expectedParameters.add(new SLogoVariable("y"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method
    System.out.println("Moving " + expectedParameters.get(0).getValue() + " x and " +
        expectedParameters.get(1).getValue() + " y");
    return new SLogoConstant(0); // todo: return degrees turtle turned
  }
}

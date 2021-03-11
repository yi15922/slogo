package slogo.compiler.command.turtle;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class SetTowardsCommand extends SLogoCommand {

  public SetTowardsCommand() {
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

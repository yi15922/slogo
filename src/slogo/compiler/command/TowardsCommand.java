package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.Token;
import slogo.compiler.Variable;

public class TowardsCommand extends Command {

  public TowardsCommand() {
    super("Towards");
    expectedParameters.add(new Variable("x"));
    expectedParameters.add(new Variable("y"));
  }

  @Override
  public Token run() throws SLogoException {
    // todo: call Turtle method
    System.out.println("Moving " + expectedParameters.get(0).getValue() + " x and " +
        expectedParameters.get(1).getValue() + " y");
    return new Constant(0); // todo: return degrees turtle turned
  }
}

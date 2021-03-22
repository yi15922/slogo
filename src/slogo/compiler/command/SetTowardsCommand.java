package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class SetTowardsCommand extends SLogoCommand {

  public SetTowardsCommand() {
    super("SetTowards");
    expectedParameters.add(new SLogoVariable("x"));
    expectedParameters.add(new SLogoVariable("y"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    System.out.println("Setting heading towards (" + expectedParameters.get(0).getValue() + ", " + expectedParameters.get(1).getValue() + ")");
    return new SLogoConstant(modelTurtle.towards(expectedParameters.get(0).getValue(), expectedParameters.get(0).getValue()));
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

public class SetBackgroundCommand extends SLogoCommand {

  public SetBackgroundCommand() {
    super("SetBackground");
    expectedParameters.add(new SLogoVariable("index"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: call Turtle method with index
    return new SLogoConstant(expectedParameters.get(0).getValue());
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;

public class MakeVariableCommand extends SLogoCommand {

  public MakeVariableCommand() {
    super("Make");
    expectedParameters.add(new SLogoToken("variable name"));
    expectedParameters.add(new SLogoVariable("expr"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    // todo: add variable to workspace
    SLogoVariable var = new SLogoVariable(expectedParameters.get(0).toString(), expectedParameters.get(1).getValue());
    return new SLogoConstant(var.getValue());
  }
}

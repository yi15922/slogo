package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

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

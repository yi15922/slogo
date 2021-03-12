package slogo.compiler.command.advanced;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoTokenList;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.command.SLogoUserDefinedCommand;

public class MakeUserInstruction extends SLogoCommand {

  public MakeUserInstruction() {
    super("To");
    expectedParameters.add(new SLogoToken("command name"));
    expectedParameters.add(new SLogoTokenList("variables"));
    expectedParameters.add(new SLogoTokenList("commands"));
  }
  @Override
  public SLogoToken run() throws SLogoException {
    try { // todo: replace with returning 1, command should place itself in the workspace
      SLogoUserDefinedCommand userDefinedCommand = (SLogoUserDefinedCommand) expectedParameters.get(0);
      userDefinedCommand.giveParameters((SLogoTokenList) expectedParameters.get(1), (SLogoTokenList) expectedParameters.get(2));
      return new SLogoConstant(1);
    }
    catch (SLogoException e) {
      return new SLogoConstant(0);
    }
  }
}

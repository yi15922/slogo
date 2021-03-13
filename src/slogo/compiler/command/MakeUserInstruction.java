package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;

public class MakeUserInstruction extends SLogoCommand {

  public MakeUserInstruction() {
    super("To");
    expectedParameters.add(new SLogoToken("command name"));
    expectedParameters.add(new SLogoList("variables"));
    expectedParameters.add(new SLogoList("commands"));
  }
  @Override
  public SLogoToken run() throws SLogoException {
    try {
      SLogoUserDefinedCommand userDefinedCommand = (SLogoUserDefinedCommand) expectedParameters.get(0);
      userDefinedCommand.giveParameters((SLogoList) expectedParameters.get(1), (SLogoList) expectedParameters.get(2));
      return new SLogoConstant(1);
    }
    catch (SLogoException e) {
      return new SLogoConstant(0);
    }
  }
}

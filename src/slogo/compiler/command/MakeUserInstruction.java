package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoTokenList;

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
      return new SLogoUserDefinedCommand(expectedParameters.get(0).toString(),
          (SLogoTokenList) expectedParameters.get(1), (SLogoTokenList) expectedParameters.get(2));
    }
    catch (SLogoException e) {
      return new SLogoConstant(0);
    }
  }
}

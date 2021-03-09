package slogo.compiler;

import java.util.List;

// todo: either clean up or delete this entire class, might be negated by SLogoUserDefinedCommand
public class SLogoUserDefinedFunction extends SLogoFunction {
  private List<SLogoToken> expectedParameters;
  private List<SLogoToken> commandList;

  public SLogoUserDefinedFunction(String name) {
    super(name);
  }

  public SLogoUserDefinedFunction(String name, SLogoTokenList variableTokens, SLogoTokenList commandTokens) {
    super(name);
    for (SLogoToken token : variableTokens.getTokenList()) {
      expectedParameters.add(new SLogoVariable(token.toString()));
    }
    commandList = commandTokens.getTokenList();
  }


}

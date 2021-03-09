package slogo.compiler;

import java.util.List;

public class SLogoTokenList extends SLogoToken {
  List<SLogoToken> myTokens;

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For Variable or Function objects, this name will act as the
   * name of the variable or function.
   *
   * @param name - the specified name of the Token
   */
  public SLogoTokenList(String name) {
    super(name);
  }

  public SLogoTokenList(List<SLogoToken> innerTokens) {
    super("");
    myTokens = innerTokens;
  }
}

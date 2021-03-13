package slogo.compiler.token;

import java.util.Collections;
import java.util.List;

public class SLogoList extends SLogoToken {
  List<SLogoToken> myTokens;

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For Variable or Function objects, this name will act as the
   * name of the variable or function.
   *
   * @param name - the specified name of the Token
   */
  public SLogoList(String name) {
    super(name);
  }

  public SLogoList(List<SLogoToken> innerTokens) {
    super("List");
    myTokens = innerTokens;
  }

  public List<SLogoToken> getTokenList() {
    return Collections.unmodifiableList(myTokens);
  }

  @Override
  public String toString(){
    return myTokens.toString();
  }
}

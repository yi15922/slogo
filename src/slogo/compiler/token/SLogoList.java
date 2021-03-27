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

  /**
   * Initialize list token with collection of tokens
   * @param innerTokens - collection of tokens in between the brackets
   */
  public SLogoList(List<SLogoToken> innerTokens) {
    super("List");
    myTokens = innerTokens;
  }

  /**
   * Returns contents of the token list in a more easily manipulable format. However,
   * tokens cannot be added/removed from the returned list, an example of not trusting
   * other classes.
   * @return - unmodifiable version of tokens
   */
  public List<SLogoToken> getTokenList() {
    return Collections.unmodifiableList(myTokens);
  }

  /**
   * Converts token list to printable format
   * @return - String representation of list of tokens
   */
  @Override
  public String toString(){
    return myTokens.toString();
  }
}

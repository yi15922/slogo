package slogo.compiler;

import java.util.List;

public class TokenList extends Token {
  List<Token> myTokens;

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For Variable or Function objects, this name will act as the
   * name of the variable or function.
   *
   * @param name - the specified name of the Token
   */
  public TokenList(String name) {
    super(name);
  }

  public TokenList(List<Token> innerTokens) {
    super("");
    myTokens = innerTokens;
  }
}

package slogo.compiler.token;

public class SLogoComment extends SLogoToken {

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For a Comment object, the name should be the remainder
   * of the commented line.
   *
   * @param name - the specified name of the Token
   */
  public SLogoComment(String name) {
    super("Comment");
  }

}

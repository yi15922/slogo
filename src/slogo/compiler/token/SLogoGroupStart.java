package slogo.compiler.token;

public class SLogoGroupStart extends SLogoToken {

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For Variable or Function objects, this name will act as the
   * name of the variable or function.
   */
  public SLogoGroupStart(String name) {
    super("GroupStart");
  }

}

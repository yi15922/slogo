package slogo.compiler;

import slogo.compiler.SLogoToken;

public class SLogoConstant extends SLogoToken {

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For a {@code Constant} object, the name is irrelevant.
   *
   */
  public SLogoConstant(double value) {
    super("Constant");
    tokenValue = value;
  }

}

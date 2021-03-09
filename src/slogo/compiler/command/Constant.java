package slogo.compiler.command;

import slogo.compiler.Token;

public class Constant extends Token {

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For a {@code Constant} object, the name is irrelevant.
   *
   */
  public Constant(double value) {
    super("Constant");
    tokenValue = value;
  }

}

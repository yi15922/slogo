package slogo.compiler.token;

import slogo.compiler.Parser;

public class SLogoConstant extends SLogoToken {

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For a {@code Constant} object, the name is irrelevant.
   * @param value - the {@code double} value of the constant.
   */
  public SLogoConstant(double value) {
    super("Constant");
    tokenValue = value;
  }

  /**
   * For the purpose of being able to create an instance of this class using the
   * {@link Parser}, this constructor takes the string value of the user input
   * and converts it to a {@code double}. This has the same effect as constructing this
   * object with a double.
   *
   * @param name - the value of the numerical constant as a {@code String}.
   */
  public SLogoConstant(String name) {
    super("Constant");
    tokenValue = Double.parseDouble(name);
  }





}

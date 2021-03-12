package slogo.compiler.token;

import slogo.compiler.WorkspaceEntry;

public class SLogoVariable extends WorkspaceEntry {
  private double myValue;

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For Variable objects, this name will act as the
   * name of the variable. If no value is specified, then value will be set to null.
   *
   * @param name - the specified name of the variable
   */
  public SLogoVariable(String name) {
    super(name);
  }

  /**
   * Initializes a {@code Variable} with a name and value. Used most often when the user declares a variable,
   * as a {@code Variable} object will be created and then placed in the {@code Workspace}.
   *
   * @param name - the specified name of the variable
   * @param value - the specified numeric value of the variable
   */
  public SLogoVariable(String name, double value) {
    this(name);
    myValue = value;
  }

  /**
   * Allows {@code Command} or {@code Function} objects to access a variable's value.
   * @return
   */
  public double getValue() {
    return myValue;
  }

  public void setValue(double updatedValue) {
    myValue = updatedValue;
  }
}

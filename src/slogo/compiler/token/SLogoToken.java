package slogo.compiler.token;

/**
 * Superclass of all possible tokens recognized by the Parser: Command, Variable, Constant,
 * List, and Comment. This inheritance hierarchy enables us to take advantage of polymorphism
 * when parsing through user-entered Strings, since every String can be converted into a Token.
 * Strings that match known regex for certain Token types (i.e. a supported command name, a number)
 * will be automatically converted into their respective subclass. Otherwise, a generic Token
 * will first be created and the appropriate type will be created later by the Function class.
 *
 * @author Patrick Liu
 */
public class SLogoToken {
  protected String tokenName;
  protected double tokenValue;

  /**
   * All Tokens must be initialized with a name, which is almost always the contents of the String
   * that is being converted into a Token. For Variable or Function objects, this name will act
   * as the name of the variable or function.
   * @param name - the specified name of the Token
   */
  public SLogoToken(String name) {
    tokenName = name;
  }

  /**
   * Enables the creation of a more specific Token type from a generic Token
   * Example: given a Token genericToken, Variable var = new Variable(genericToken.toString());
   * @return - the name of the Token as a String
   */
  public String toString() {
    return tokenName;
  }

  /**
   * Compares two tokens to see if they are the same type. This method is useful when running
   * a command, since we need to feed commands the correct parameter types. Limited in that there
   * is no easy way to compare commands. Our workaround is to try casting a token to a
   * SLogoCommand, and performing another action in the catch block.
   * @param otherToken - the token we are comparing this one to
   * @return - true if the tokens are equal type, false otherwise
   */
  public boolean isEqualTokenType(SLogoToken otherToken) {
    return this.getClass().getSimpleName().equals(otherToken.getClass().getSimpleName());
  }

  /**
   * Returns the value of a Token, which may be 0 at default.
   * @return - double value of token
   */
  public double getValue() {
    return tokenValue;
  }

}

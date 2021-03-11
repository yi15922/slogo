package slogo.compiler;

import java.lang.reflect.*;

/**
 * The {@code SLogoTokenMaker} class creates {@link SLogoToken} objects of the
 * correct type given a user input. Calling {@code make()} will return an instance
 * of the desired token, constructed with only a name as the "bare minimum". Tokens
 * that require more parameters will later be filled in by the {@link Compiler}.
 */
public class SLogoTokenMaker {

  private final String CLASSPATH = "slogo.compiler.";
  private Workspace workspace;

  public SLogoTokenMaker(Workspace workspace){
    this.workspace = workspace;
  }

  /**
   * Creates a {@link SLogoToken} object from a {@code String} describing the
   * type of token, and the user input string. In the case of
   * {@link slogo.compiler.command.SLogoCommand}, {@link SLogoFunction}, or
   * {@link SLogoVariable}, the created objects will be named by the user input
   * string.
   * @param tokenType {@code String} description of the token type, obtained
   *                  from a {@code .properties} file.
   * @param inputString the user input {@code String} that is used to name the
   *                    token.
   * @return a {@code SLogoToken} object, or {@code null} if no object could be
   * created from the given strings.
   */
  public SLogoToken make(String tokenType, String inputString){
    try {
      Object obj;
      if (tokenType.equals("Command")) {
        obj = getCommandConstructor(inputString).newInstance();
      } else {
        if (tokenType.equals("Variable")) {

        }
        obj = getTokenConstructor(tokenType).newInstance(inputString);
      }
      return (SLogoToken) obj;

    } catch (Throwable exception) {
      System.err.println(exception);
      return null;
    }
  }

  private Constructor getTokenConstructor(String tokenType){
    try {
      Class tokenClass = Class.forName(CLASSPATH + "SLogo" + tokenType);
      return tokenClass.getConstructor(String.class);

    } catch (Throwable exception) {
      System.err.println(exception);
      return null;
    }

  }

  private Constructor getCommandConstructor(String inputString){
    try {
      Class commandClass = Class.forName(CLASSPATH + "command." + inputString + "Command");
      return commandClass.getConstructor();

    } catch (Throwable exception) {
      System.err.println(exception);
      return null;
    }
  }

}

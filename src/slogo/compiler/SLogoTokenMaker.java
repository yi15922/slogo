package slogo.compiler;

import java.lang.reflect.*;
import slogo.SLogoException;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;


/**
 * The {@code SLogoTokenMaker} class creates {@link SLogoToken} objects of the
 * correct type given a user input. Calling {@code make()} will return an instance
 * of the desired token, constructed with only a name as the "bare minimum". Tokens
 * that require more parameters will later be filled in by the {@link Compiler}.
 *
 * @author Yi Chen
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
   * {@link slogo.compiler.command.SLogoUserDefinedCommand} or {@link SLogoVariable}, the method
   * will first search the {@link Workspace} for the a matching {@link WorkspaceEntry}. If none are
   * found, the method creates a new entry of the correct subclass, adds it to the
   * workspace, and returns it.
   *
   *
   * @param tokenType {@code String} description of the token type, obtained
   *                  from a {@code .properties} file.
   * @param inputString the user input {@code String} that is used to name the
   *                    token.
   * @return a {@code SLogoToken} object, or {@code null} if no object can be
   * created from the given strings.
   */
  public SLogoToken make(String tokenType, String inputString) throws SLogoException{
    Object obj = null;
    if (tokenType.equals("Command")) {
      try {
        obj = getCommandConstructor(inputString).newInstance();
      } catch (ClassNotFoundException | NoSuchMethodException |
          InstantiationException | IllegalAccessException | InvocationTargetException exception) {
        System.out.printf("Searching workspace for function %s\n", inputString);
        obj = workspace.search(inputString);
        if (obj == null) {
          throw new SLogoException("Undefined command %s", inputString);
        }
      }
    } else if (tokenType.equals("Variable")) {
      System.out.printf("Searching workspace for variable %s\n", inputString);
      return workspace.searchAndAddIfAbsent(tokenType, inputString);
    } else {
      try {
        obj = getTokenConstructor(tokenType).newInstance(inputString);
      } catch (IllegalAccessException | IllegalArgumentException | InstantiationException
          | InvocationTargetException | ClassNotFoundException | NoSuchMethodException exception) {
        System.err.printf("Cannot create token from string \"%s\"\n", inputString);
        exception.printStackTrace();
        return null;
      }
    }
    return (SLogoToken) obj;
  }

  private Constructor getTokenConstructor(String tokenType)
      throws ClassNotFoundException, NoSuchMethodException {
    Class tokenClass = Class.forName(CLASSPATH + "token.SLogo" + tokenType);
    return tokenClass.getConstructor(String.class);

  }

  private Constructor getCommandConstructor(String inputString)
      throws ClassNotFoundException, NoSuchMethodException {

    Class commandClass = Class.forName(CLASSPATH + "command." + inputString + "Command");
    return commandClass.getConstructor();
  }

}

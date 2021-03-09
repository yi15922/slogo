package slogo.compiler;

import java.util.ResourceBundle;
import slogo.compiler.command.*;

/**
 * The {@code Parser} class takes user inputs and converts them into {@link SLogoToken} objects
 * of the correct type. This class simply splits {@code String} inputs by blank spaces and
 * performs no syntax related error checking.
 *
 * While parsing, if the beginning of a {@link SLogoTokenList} is encountered, the corresponding
 * token will be created and all tokens until the list closing token is found.
 *
 * While parsing, any {@code String} matching {@code [a-zA-Z_]+(\?)?} will be used to search
 * for a valid {@link SLogoCommand}. If no runnable object with that name is found, a generic
 * {@code SLogoToken} will be created and the compiler will be responsible for checking whether
 * a {@link SLogoRunnable} with the same name exists in the {@link Workspace}.
 *
 * The parser is initialized with the language it has to interpret in. This language is read from a
 * {@code .properties} file in the {@code src.resources.languages} folder.
 */
public class Parser {

  private final ResourceBundle languageBundle;
  // use Java's dot notation, like with import, for properties files
  public static final String DEFAULT_RESOURCE_PACKAGE = "resources.languages.";
  // use file system notation, standard Unix slashes, for other kinds of files
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");


  public Parser(String language){
    languageBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
  }

  public String getCommandName(String userInput){
    return languageBundle.getString(userInput);
  }

  public SLogoToken getNextToken(){
    return null;
  }

  public boolean hasNextToken(){
    return false;
  }




}

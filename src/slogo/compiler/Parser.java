package slogo.compiler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import slogo.compiler.command.SLogoCommand;

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
  private final ResourceBundle syntaxBundle;
  // use Java's dot notation, like with import, for properties files
  private final String DEFAULT_RESOURCE_PACKAGE = "resources.languages.";
  private final String SYNTAX_BUNDLE = "Syntax";
  // use file system notation, standard Unix slashes, for other kinds of files
  private final String DEFAULT_RESOURCE_FOLDER = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private final HashMap<String, Pattern> builtinCommands;
  private final HashMap<String, Pattern> tokenTypes;

  public Parser(String language){
    languageBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    syntaxBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + SYNTAX_BUNDLE);
    builtinCommands = createRegexMap(languageBundle);
    tokenTypes = createRegexMap(syntaxBundle);
  }

  public SLogoToken createTokenFromString(String userInput){
    String tokenType = determineTokenType(userInput);
    SLogoTokenMaker tokenMaker= new SLogoTokenMaker();
    return tokenMaker.make(tokenType, userInput);
  }


  public String determineTokenType(String userInput){
    return getKeyFromRegex(tokenTypes, userInput);
  }

  public String determineCommandType(String userInput){
    return getKeyFromRegex(builtinCommands, userInput);
  }


  /**
   * Searches the given {@code Map} for any value {@code Pattern}s that has a
   * regex match with {@code userInput} string. If there is a match, returns the
   * {@code String} key of the corresponding pattern. If not, returns null.
   * @param regexMap a map containing {@code String} and {@code Pattern} pairs
   * @param userInput the {@code String} to search for in the map
   * @return returns {@code String} match from the map, or null if none is found.
   */
  public String getKeyFromRegex(Map<String, Pattern> regexMap, String userInput){
    for (String commandName : regexMap.keySet()) {
      Pattern p = regexMap.get(commandName);
      Matcher m = p.matcher(userInput);

      if (m.find()) {
        return commandName;
      }
    }
    return null;
  }

  /**
   * Creates a {@code HashMap} of {@code String} and {@code Pattern} pairs so that
   * the a used input string can be searched against the list of keys.
   * @param bundle {@code ResourceBundle} object to use to create the map
   * @return A {@code HashMap} containing {@code String} and {@code Pattern} pairs.
   */
  private HashMap<String, Pattern> createRegexMap(ResourceBundle bundle){
    HashMap<String, Pattern> ret = new HashMap<>();
    Enumeration<String> keys = bundle.getKeys();
    while (keys.hasMoreElements()) {
      String commandName = keys.nextElement();
      String commandRegex = bundle.getString(commandName);
      Pattern p = Pattern.compile(commandRegex);
      ret.put(commandName, p);
    }
    return ret;
  }

  public SLogoToken getNextToken(){
    return null;
  }

  public boolean hasNextToken(){
    return false;
  }




}

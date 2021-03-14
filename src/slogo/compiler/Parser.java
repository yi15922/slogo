package slogo.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import slogo.SLogoException;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.token.SLogoRunnable;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.observers.InputObserver;

/**
 * The {@code Parser} class takes user inputs and converts them into {@link SLogoToken} objects
 * of the correct type. This class simply splits {@code String} inputs by blank spaces and
 * performs no syntax related error checking.
 *
 * While parsing, if the beginning of a {@link SLogoList} is encountered, the corresponding
 * token will be created and all tokens until the list closing token is found.
 *
 * While parsing, any {@code String} matching {@code [a-zA-Z_]+(\?)?} will be used to search
 * for a valid {@link SLogoCommand}. If no runnable object with that name is found, a generic
 * {@code SLogoToken} will be created and the compiler will be responsible for checking whether
 * a {@link SLogoRunnable} with the same name exists in the {@link Workspace}.
 *
 * The parser is initialized with the language it has to interpret in. This language is read from a
 * {@code .properties} file in the {@code src.resources.languages} folder.
 *
 * @author Yi Chen
 */
public class Parser implements InputObserver {

  private final ResourceBundle languageBundle;
  private final ResourceBundle syntaxBundle;
  // use Java's dot notation, like with import, for properties files
  private final String DEFAULT_RESOURCE_PACKAGE = "resources.languages.";
  private final String SYNTAX_BUNDLE = "Syntax";
  private final String COMMAND_TOKENTYPE = "Command";
  // use file system notation, standard Unix slashes, for other kinds of files
  private final String DEFAULT_RESOURCE_FOLDER = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private final Map<String, Pattern> builtinCommands;
  private final Map<String, Pattern> tokenTypes;

  private Workspace workspace;

  /**
   * Constructs a parser with the specified language. This determines which
   * {@code ResourceBundle} is loaded and which language the user can code in.
   * This constructor also builds {@code Map}s of {@code String} and {@code Pattern}s
   * that connect user input values to string descriptions of classes to generate.
   * @param language the desired language of the compiler.
   */
  public Parser(String language, Workspace workspace){
    languageBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    syntaxBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + SYNTAX_BUNDLE);
    builtinCommands = createRegexMap(languageBundle);
    tokenTypes = createRegexMap(syntaxBundle);
    this.workspace = workspace;
  }

  /**
   * Parses all user input and creates the corresponding {@link SLogoToken}s,
   * placing them in a {@code Queue}. The input can contain whitespace and newline characters.
   * The method first breaks the strings into lines, then into individual space delimited
   * strings.
   *
   * If a "#" character is encountered, the entire line is ignored.
   *
   * @param inputString a user input SLogo code
   * @return a {@code Queue} of {@code SLogoToken} objects.
   */
  public Queue<SLogoToken> parseInput(String inputString){
    ArrayList<String> allLines = new ArrayList<>(Arrays.asList(inputString.split("\n")));
    Queue<SLogoToken> tokenQueue = new LinkedList<>();

    for (String line : allLines) {
      //System.out.println(line);
      ArrayList<String> strings = new ArrayList<>(Arrays.asList(line.split(" ")));
      if (strings.get(0).equals("#")) {
        continue;
      }
      for (String s : strings) {
        SLogoToken newToken = createTokenFromString(s);
        if (newToken != null)
          System.out.println(newToken.toString());
          tokenQueue.add(newToken);
      }
    }
    System.out.println(tokenQueue);
    return tokenQueue;
  }

  /**
   * Creates {@link SLogoToken} of the correct types from given {@code String}.
   * If the token type is of {@link SLogoCommand}, then the correct subclass of
   * {@code Command} will be created.
   *
   * If no corresponding {@code SLogoToken} or {@code SLogoCommand} is found,
   * this method will attempt to find a {@link WorkspaceEntry} in the
   * {@link Workspace} that has a name matching the user input. If none is found
   * in the workspace, an exception will be thrown.
   * @param userInput
   * @return the {@code SLogoToken} of the correct type.
   */
  public SLogoToken createTokenFromString(String userInput) throws SLogoException {
    String tokenType = determineTokenType(userInput);
    if (tokenType == null) return null;

    SLogoTokenMaker tokenMaker= new SLogoTokenMaker(workspace);
    if (tokenType.equals(COMMAND_TOKENTYPE)) {
      String commandType = determineCommandType(userInput);
      return tokenMaker.make(tokenType, commandType);
    } else {
      return tokenMaker.make(tokenType, userInput);
    }

  }

  /**
   * Determines the proper name describing the {@link SLogoToken} from a user
   * input string. Returns {@code null} if the user input doesn't match any existing
   * pattern.
   * @param userInput a string that does not contain any blank space.
   * @return a {@code String} of the proper name of a {@code SLogoToken} or null
   */
  private String determineTokenType(String userInput) throws SLogoException{
    if (userInput.equals("")) { return null; }

    String ret = getKeyFromRegex(tokenTypes, userInput);
    if (ret == null) {
      throw new SLogoException("Not a valid token: %s", userInput);
    }
    return ret;
  }

  /**
   * Attempts to find the {@code String} command name matching the user input.
   * If not found, the input might be a user defined function, therefore return
   * the user input as is.
   * @param userInput {@code String} user typed text
   * @return either the proper {@link SLogoCommand} name or the user input as is.
   */
  private String determineCommandType(String userInput){
    String ret = getKeyFromRegex(builtinCommands, userInput);
    if (ret == null) {
      return userInput;
    }
    return ret;
  }


  /**
   * Searches the given {@code Map} for any value {@code Pattern}s that has a
   * regex match with {@code userInput} string. If there is a match, returns the
   * {@code String} key of the corresponding pattern. If not, returns null.
   *
   * The input map's keySet is searched in order of descending string length
   * because regex finds the first matching {@code String}, even if it is a
   * substring. Therefore, ordering the set in reverse eliminates the possibility
   * of getting the key for "or" when the user input is "for", for example.
   *
   * @param regexMap a map containing {@code String} and {@code Pattern} pairs
   * @param userInput the {@code String} to search for in the map
   * @return returns {@code String} match from the map, or null if none is found.
   */
  private String getKeyFromRegex(Map<String, Pattern> regexMap, String userInput){
    ArrayList<String> reversedKeys = new ArrayList<>(regexMap.keySet());
    reversedKeys.sort(Comparator.comparing(String::length)
        .reversed());
    for (String commandName : reversedKeys) {
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
  private Map<String, Pattern> createRegexMap(ResourceBundle bundle){
    TreeMap<String, Pattern> ret = new TreeMap<>();
    Enumeration<String> keys = bundle.getKeys();
    while (keys.hasMoreElements()) {
      String commandName = keys.nextElement();
      String commandRegex = bundle.getString(commandName);
      Pattern p = Pattern.compile(commandRegex);
      ret.put(commandName, p);
    }
    return ret;
  }


  @Override
  public void receiveUserInput(String input) {
    parseInput(input);
    System.out.println(input);
  }
}

package slogo.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import slogo.Main;
import slogo.SLogoException;
import slogo.WindowAlert;
import slogo.compiler.command.IDCommand;
import slogo.compiler.command.SLogoUserDefinedCommand;
import slogo.compiler.token.SLogoGroupEnd;
import slogo.compiler.token.SLogoGroupStart;
import slogo.compiler.token.SLogoVariable;
import slogo.model.Turtle;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoListEnd;
import slogo.compiler.token.SLogoListStart;
import slogo.compiler.token.SLogoToken;
import slogo.observers.InputObserver;

/**
 * Responsible for grouping tokens into a form in which they could be run. Detects beginning and end
 * of lists, and creates {@link SLogoList} tokens. All tokens in the current {@link Parser} session
 * will be used to create a {@link slogo.compiler.token.SLogoFunction}, which the compiler will run
 * once the parser queue is depleted.
 *
 * Upon encountering a {@link slogo.compiler.token.SLogoComment} token, the compiler
 * will ignore all subsequent tokens until the next new line character.
 *
 * @author Yi Chen
 */
public class Compiler implements InputObserver {

  private Parser parser;
  private Queue<SLogoToken> tokenQueue;
  private Turtle turtle;
  private boolean containsID;

  /**
   * Creates an instance of a compiler. The instance takes an instance of {@link Parser}.
   * @param parser the {@code Parser} instance for this parsing session.
   */
  public Compiler(Parser parser, Turtle turtle) {
    this.parser = parser;
    this.turtle = turtle;
  }


  /**
   * Uses the parser to create a {@code Queue} of {@link SLogoToken} instances from
   * the user input string.
   * @param input user input to the console
   */
  private void makeTokenQueue(String input){
    try {
      tokenQueue = parser.parseInput(input);
    } catch (Exception e) {
      WindowAlert.throwErrorAlert(e.getMessage());
    }
  }

  /**
   * Creates a {@link slogo.compiler.token.SLogoFunction} object containing all compiled
   * {@link SLogoToken} objects. This will add lists as {@link SLogoList} objects rather
   * than as individual tokens, therefore these lists should be treated link single tokens.
   *
   * Upon successful creation of the {@code SLogoFunction} object, this method will call
   * the function's {@code run()} method.
   */
  public SLogoToken compileAndRun(String input){
    containsID = false;
    makeTokenQueue(input);
    if (!hasNextToken()) return null;
    Deque<SLogoToken> functionTokens = new LinkedList<>();
    while (hasNextToken()) {
      SLogoToken tokenToAdd = getNextToken();
      if (tokenToAdd.getClass().equals(SLogoListStart.class)) {
        tokenToAdd = makeList();
      }
      else if (tokenToAdd.getClass().equals(SLogoGroupStart.class)) {
        tokenToAdd = makeGroupFunction();
      }
      else if (tokenToAdd.getClass().equals(IDCommand.class)) {
        tokenToAdd = new SLogoVariable("ID");
        containsID = true;
      }
      functionTokens.add(tokenToAdd);
    }
    try {
      if (containsID) {
        SLogoUserDefinedCommand wrapperCommand = new SLogoUserDefinedCommand("wrapper");
        SLogoList variableList = new SLogoList(new ArrayList<>(Arrays.asList(new SLogoVariable("ID"))));
        SLogoList commandList = new SLogoList(new ArrayList<>(functionTokens));
        wrapperCommand.giveParameters(variableList, commandList);
        return turtle.runIDFunction(wrapperCommand);
      }
      else {
        return new SLogoFunction(functionTokens, turtle).run();
      }
    } catch (Exception e) {
        WindowAlert.throwErrorAlert(e.getMessage());
      }
    return null;
  }


  /**
   * Returns the next {@link SLogoToken} from the queue of parsed tokens.
   * Returns null if no more tokens are available.
   * @return a {@code SLogoToken} object or {@code null}.
   */
  private SLogoToken getNextToken(){
    try {
      return tokenQueue.remove();
    } catch (NoSuchElementException | NullPointerException exception) {
      return null;
    }
  }

  /**
   * Determines whether there are more tokens left in this compile session.
   * @return {@code boolean} whether there are more parsed {@code SLogoToken}s.
   */
  private boolean hasNextToken(){
    return (tokenQueue != null && tokenQueue.size() != 0);
  }

  /**
   * This method should only be called when a {@link slogo.compiler.token.SLogoListStart}
   * token is encountered.
   *
   * This method creates a {@link SLogoList} token with all subsequent tokens in the queue until it
   * encounters a {@link slogo.compiler.token.SLogoListEnd}.
   * @return a {@code SLogoList} object.
   */
  private SLogoList makeList() throws SLogoException {
    boolean listEnded = false;
    ArrayList<SLogoToken> tokenList = new ArrayList<>();
    while (hasNextToken()) {
      SLogoToken token = getNextToken();
      if (token.getClass().equals(SLogoListEnd.class)) {
        listEnded = true;
        break;
      }
      else if (token.getClass().equals(SLogoListStart.class)) {
        token = makeList();
      }
      else if (token.getClass().equals(SLogoGroupStart.class)) {
        token = makeGroupFunction();
      }
      else if (token.getClass().equals(IDCommand.class)) {
        token = new SLogoVariable("ID");
        containsID = true;
      }
      tokenList.add(token);
    }
    if (!listEnded) {
      throw new SLogoException("Unexpected end of file: missing closing ']'?");
    }
    SLogoList ret = new SLogoList(tokenList);
    if (Main.DEBUG) System.out.println(ret);
    return ret;
  }

  private SLogoFunction makeGroupFunction() throws SLogoException {
    boolean listEnded = false;
    ArrayList<SLogoToken> tokenList = new ArrayList<>();
    while (hasNextToken()) {
      SLogoToken token = getNextToken();
      if (token.getClass().equals(SLogoGroupEnd.class)) {
        listEnded = true;
        break;
      }
      else if (token.getClass().equals(SLogoListStart.class)) {
        token = makeList();
      }
      else if (token.getClass().equals(SLogoGroupStart.class)) {
        token = makeGroupFunction();
      }
      else if (token.getClass().equals(IDCommand.class)) {
        token = new SLogoVariable("ID");
        containsID = true;
      }
      tokenList.add(token);
    }
    if (!listEnded) {
      throw new SLogoException("Unexpected end of file: missing closing ')'?");
    }
    SLogoList groupList = new SLogoList(tokenList);
    GroupHelper groupHelper = new GroupHelper(groupList, turtle);
    return groupHelper.createGroupFunction();
  }

  @Override
  public void receiveUserInput(String input) {
    compileAndRun(input);
  }
}
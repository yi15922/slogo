package slogo.compiler;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import slogo.SLogoException;
import slogo.Turtle;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoListEnd;
import slogo.compiler.token.SLogoListStart;
import slogo.compiler.token.SLogoToken;

/**
 * Responsible for grouping tokens into a form in which they could be run. Detects beginning and end
 * of lists, and creates {@link SLogoList} tokens. All tokens in the current {@link Parser} session
 * will be used to create a {@link slogo.compiler.token.SLogoFunction}, which the compiler will run
 * once the parser queue is depleted.
 *
 * Upon encountering a {@link slogo.compiler.token.SLogoComment} token, the compiler
 * will ignore all subsequent tokens until the next new line character.
 */
public class Compiler {

  private Parser parser;
  private Queue<SLogoToken> tokenQueue;
  private Turtle turtle;

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
  public void makeTokenQueue(String input){
    tokenQueue = parser.parseInput(input);
  }

  /**
   * Creates a {@link slogo.compiler.token.SLogoFunction} object containing all compiled
   * {@link SLogoToken} objects. This will add lists as {@link SLogoList} objects rather
   * than as individual tokens, therefore these lists should be treated link single tokens.
   *
   * Upon successful creation of the {@code SLogoFunction} object, this method will call
   * the function's {@code run()} method.
   */
  public void compileAndRun(){
    if (!hasNextToken()) return;
    SLogoCommand initialCommand = (SLogoCommand) getNextToken();
    Deque<SLogoToken> parameterTokens = new LinkedList<>();
    while (hasNextToken()) {
      SLogoToken tokenToAdd = getNextToken();
      if (tokenToAdd.getClass().equals(SLogoListStart.class)) {
        tokenToAdd = makeList();
      }
      parameterTokens.add(tokenToAdd);
    }

    new SLogoFunction(initialCommand, parameterTokens, turtle).run();

  }


  /**
   * Returns the next {@link SLogoToken} from the queue of parsed tokens.
   * Returns null if no more tokens are available.
   * @return a {@code SLogoToken} object or {@code null}.
   */
  public SLogoToken getNextToken(){
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
  public boolean hasNextToken(){
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
  public SLogoList makeList() throws SLogoException {
    boolean listEnded = false;
    ArrayList<SLogoToken> tokenList = new ArrayList<>();
    while (hasNextToken()) {
      SLogoToken token = getNextToken();
      if (token.getClass().equals(SLogoListEnd.class)) {
        listEnded = true;
        break;
      }
      tokenList.add(token);
    }
    if (!listEnded) {
      throw new SLogoException("Unexpected end of file: missing closing ']'?");
    }
    SLogoList ret = new SLogoList(tokenList);
    System.out.println(ret);
    return ret;
  }

}
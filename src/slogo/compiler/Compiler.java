package slogo.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import javafx.scene.web.HTMLEditorSkin.Command;
import slogo.SLogoException;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoListEnd;
import slogo.compiler.token.SLogoToken;

/**
 * Responsible for grouping tokens into a form in which they could be run. Detects beginning and end
 * of lists, and creates {@link SLogoList} tokens. All tokens in the current {@link Parser} session
 * will be used to create a {@link slogo.compiler.token.SLogoFunction}, which the compiler will run
 * once the parser queue is depleted.
 */
public class Compiler {

  private Parser parser;
  private Queue<SLogoToken> tokenQueue;

  public Compiler(Parser parser) {
    this.parser = parser;
  }


  public void getAllTokens(String input){
    tokenQueue = parser.parseInput(input);
  }


  /**
   * Returns the next {@link SLogoToken} from the queue of parsed tokens.
   * Returns null if no more tokens are available.
   * @return a {@code SLogoToken} object or {@code null}.
   */
  public SLogoToken getNextToken(){
    try {
      return tokenQueue.remove();
    } catch (NoSuchElementException exception) {
      return null;
    }
  }

  /**
   * Determines whether there are more tokens left in this compile session.
   * @return {@code boolean} whether there are more parsed {@code SLogoToken}s.
   */
  public boolean hasNextToken(){
    return (tokenQueue.size() != 0);
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
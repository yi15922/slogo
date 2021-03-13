package slogo.compiler;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import javafx.scene.web.HTMLEditorSkin.Command;
import slogo.compiler.token.SLogoList;
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





}
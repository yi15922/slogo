package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoVariable;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of repeating the given commands a specified
 * number of times.
 */
public class RepeatCommand extends SLogoCommand {
  private Deque<SLogoToken> commandQueue;
  private SLogoVariable repcountVariable;

  /**
   * Initializes the command with name and two expected parameters
   */
  public RepeatCommand() {
    super("Repeat");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoList("commands"));
    repcountVariable = new SLogoVariable("repcount");
  }

  /**
   * Runs command with help from a LoopHelper
   * @return - result of final command run, or 0 if no commands are run
   * @throws SLogoException - if there is a syntax error in the command list
   */
  @Override
  public SLogoToken run() throws SLogoException {
    int numTimesToRepeat = (int) expectedParameters.get(0).getValue();
    SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
    commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    for (SLogoToken token : commandQueue) {
      if (token.isEqualTokenType(new SLogoVariable("")) && token.toString().equals(":repcount")) {
        System.out.println("Found repcount variable");
        repcountVariable = (SLogoVariable) token;
        break;
      }
    }
    return new LoopHelper(1, numTimesToRepeat, 1, new SLogoFunction(commandQueue, modelTurtle),
        repcountVariable).run();
  }
}

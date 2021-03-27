package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.Deque;
import slogo.SLogoException;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoVariable;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of performing one set of commands if a condition
 * is met, and another set of commands otherwise.
 */
public class IfElseCommand extends SLogoCommand {

  /**
   * Initializes the command with name and three expected parameters
   */
  public IfElseCommand() {
    super("IfElse");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoList("true commands"));
    expectedParameters.add(new SLogoList("false commands"));
  }

  /**
   * Runs command
   * @return - result of final command run, or 0 if no commands are run
   * @throws SLogoException - if there is a syntax error in the command list that is run
   */
  @Override
  public SLogoToken run() throws SLogoException {
    Deque<SLogoToken> commandQueue;
    SLogoList commandTokens;
    if (expectedParameters.get(0).getValue() != 0.0) {
      commandTokens = (SLogoList) expectedParameters.get(1);
    }
    else {
      commandTokens = (SLogoList) expectedParameters.get(2);
    }
    commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    return new SLogoFunction(commandQueue, modelTurtle).run();
  }
}

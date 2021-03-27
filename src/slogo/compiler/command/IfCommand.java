package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.Deque;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoVariable;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of performing the given commands if a condition
 * is met.
 */
public class IfCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public IfCommand() {
    super("If");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoList("commands"));
  }

  /**
   * Runs command
   * @return - result of final command run, or 0 if no commands are run
   * @throws SLogoException - if there is a syntax error in the command list
   */
  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() != 0.0) {
      SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
      Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
      return new SLogoFunction(commandQueue, modelTurtle).run();
    }
    else {
      return new SLogoConstant(0);
    }
  }
}

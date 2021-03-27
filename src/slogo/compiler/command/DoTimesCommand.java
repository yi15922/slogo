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
 * This command provides the functionality of performing the given commands a specified
 * number of times.
 */
public class DoTimesCommand extends SLogoCommand {
  private SLogoVariable counterVariable;
  private int limit;

  /**
   * Initializes the command with name and two expected parameters
   */
  public DoTimesCommand() {
    super("DoTimes");
    expectedParameters.add(new SLogoList("parameters"));
    expectedParameters.add(new SLogoList("commands"));
  }

  /**
   * Runs given commands multiple times by running a LoopHelper
   * @return - result of final command run
   * @throws SLogoException - if syntax of command list is incorrect
   */
  @Override
  public SLogoToken run() throws SLogoException {
    parseParameterList((SLogoList) expectedParameters.get(0));
    SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    return new LoopHelper(1, limit, 1, new SLogoFunction(commandQueue, modelTurtle), counterVariable).run();
  }

  private void parseParameterList(SLogoList parameterList) {
    Deque<SLogoToken> tokenQueue = new ArrayDeque<>(parameterList.getTokenList());
    try {
      counterVariable = (SLogoVariable) tokenQueue.poll();
      counterVariable.setValue(1);
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid parameter list syntax");
    }
    tokenQueue.addFirst(new EvaluateNumberCommand());
    limit = (int) new SLogoFunction(tokenQueue, modelTurtle).runSingleCommand().getValue();
  }
}

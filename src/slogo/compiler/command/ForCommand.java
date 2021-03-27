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
 * This command provides the functionality of running the given commands a specified
 * number of times according to a start, end, and increment.
 */
public class ForCommand extends SLogoCommand {
  private SLogoVariable counterVariable;
  private int start;
  private int end;
  private int increment;

  /**
   * Initializes the command with name and two expected parameters
   */
  public ForCommand() {
    super("For");
    expectedParameters.add(new SLogoList("parameters"));
    expectedParameters.add(new SLogoList("commands"));
  }

  /**
   * Runs command by running a LoopHelper with the start, end, and increment
   * @return - result of final command
   * @throws SLogoException - if loop is an infinite loop
   */
  @Override
  public SLogoToken run() throws SLogoException {
    parseParameterQueue((SLogoList) expectedParameters.get(0));
    SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    if (increment <= 0) {
      throw new SLogoException("Invalid loop declaration");
    }
    return new LoopHelper(start, end, increment, new SLogoFunction(commandQueue, modelTurtle), counterVariable).run();
  }

  // uses EvaluateNumberCommand to evaluate possible expressions in the parameter list
  private void parseParameterQueue(SLogoList parameterList) {
    Deque<SLogoToken> tokenQueue = new ArrayDeque<>(parameterList.getTokenList());
    try {
      counterVariable = (SLogoVariable) tokenQueue.poll();
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid parameter list syntax");
    }
    tokenQueue.addFirst(new EvaluateNumberCommand());
    start = (int) new SLogoFunction(tokenQueue, modelTurtle).runSingleCommand().getValue();
    tokenQueue.addFirst(new EvaluateNumberCommand());
    end = (int) new SLogoFunction(tokenQueue, modelTurtle).runSingleCommand().getValue();
    tokenQueue.addFirst(new EvaluateNumberCommand());
    increment = (int) new SLogoFunction(tokenQueue, modelTurtle).runSingleCommand().getValue();
  }
}

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

public class ForCommand extends SLogoCommand {
  private SLogoVariable counterVariable;
  private int start;
  private int end;
  private int increment;

  public ForCommand() {
    super("For");
    expectedParameters.add(new SLogoList("parameters"));
    expectedParameters.add(new SLogoList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    parseParameterQueue((SLogoList) expectedParameters.get(0));
    SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    return new LoopHelper(start, end, increment, new SLogoFunction(commandQueue, modelTurtle), counterVariable).run();
  }

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

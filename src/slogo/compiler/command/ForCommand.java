package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoFunction;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoTokenList;
import slogo.compiler.SLogoVariable;

public class ForCommand extends SLogoCommand {
  private SLogoVariable counterVariable;
  private int start;
  private int end;
  private int increment;

  public ForCommand() {
    super("For");
    expectedParameters.add(new SLogoTokenList("parameters"));
    expectedParameters.add(new SLogoTokenList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    SLogoTokenList parameterList = (SLogoTokenList) expectedParameters.get(0);
    Deque<SLogoToken> parameterQueue = new ArrayDeque<>(parameterList.getTokenList());
    parseParameterQueue(parameterQueue);
    // todo: add counterVariable to the workspace
    SLogoTokenList commandTokens = (SLogoTokenList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    // todo: check that first token is a command
    SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
    SLogoToken returnToken = new SLogoConstant(0);
    for (int i = start; i < end; i += increment) {
      System.out.println("running for loop for counter value " + i);
      returnToken = innerFunction.run();
      // todo: update counterVariable in the workspace
      // todo: figure out how Function accesses workspace
    }
    return returnToken;
  }

  private void parseParameterQueue(Deque<SLogoToken> tokenQueue) {
    // todo: check that first token is generic token/valid variable name
    counterVariable = new SLogoVariable(tokenQueue.poll().toString(), 1.0); // todo: set counter value to start
    SLogoFunction helperFunction = new SLogoFunction(new EvaluateNumberCommand(), tokenQueue);
    start = (int) helperFunction.run().getValue();
    helperFunction = new SLogoFunction(new EvaluateNumberCommand(), tokenQueue);
    end = (int) helperFunction.run().getValue();
    helperFunction = new SLogoFunction(new EvaluateNumberCommand(), tokenQueue);
    increment = (int) helperFunction.run().getValue();
  }
}

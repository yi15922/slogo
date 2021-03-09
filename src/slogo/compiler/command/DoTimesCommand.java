package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.Queue;
import slogo.SLogoException;
import slogo.compiler.SLogoFunction;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoTokenList;
import slogo.compiler.SLogoVariable;

public class DoTimesCommand extends SLogoCommand {
  private SLogoVariable counterVariable;
  private int limit;

  public DoTimesCommand() {
    super("DoTimes");
    expectedParameters.add(new SLogoTokenList("variable and limit"));
    expectedParameters.add(new SLogoTokenList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    SLogoTokenList parameterList = (SLogoTokenList) expectedParameters.get(0);
    Queue<SLogoToken> parameterQueue = new ArrayDeque<>(parameterList.getTokenList());
    parseParameterQueue(parameterQueue);
    // todo: add counterVariable to the workspace
    SLogoTokenList commandTokens = (SLogoTokenList) expectedParameters.get(1);
    Queue<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    // todo: check that first token is a command
    SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
    for (int i = 1; i < limit; i++) { // runs numTimes-1 times, last time needs to return a value
      innerFunction.run();
      // todo: update counterVariable in the workspace
      // todo: figure out how Function accesses workspace
    }
    return innerFunction.run();
  }

  private void parseParameterQueue(Queue<SLogoToken> tokenQueue) {
    // todo: check that first token is generic token/valid variable name
    counterVariable = new SLogoVariable(tokenQueue.poll().toString(), 1.0);
    SLogoFunction helperFunction = new SLogoFunction(new EvaluateNumberCommand(), tokenQueue);
    limit = (int) helperFunction.run().getValue();
  }
}

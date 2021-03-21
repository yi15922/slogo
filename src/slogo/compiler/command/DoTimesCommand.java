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

public class DoTimesCommand extends SLogoCommand {
  private SLogoVariable counterVariable;
  private int limit;

  public DoTimesCommand() {
    super("DoTimes");
    expectedParameters.add(new SLogoList("parameters"));
    expectedParameters.add(new SLogoList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    SLogoList parameterList = (SLogoList) expectedParameters.get(0);
    Deque<SLogoToken> parameterQueue = new ArrayDeque<>(parameterList.getTokenList());
    parseParameterQueue(parameterQueue);
    SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    List<SLogoFunction> functionList = new ArrayList<>();
    while (! commandQueue.isEmpty()) {
      try {
        SLogoFunction innerFunction = new SLogoFunction(commandQueue,
            modelTurtle);
        functionList.add(innerFunction);
      }
      catch (ClassCastException e) {
        throw new SLogoException("Invalid command list syntax");
      }
    }
    SLogoToken returnToken = new SLogoConstant(0);
    for (int i = 1; i <= limit; i++) {
      for (SLogoFunction function : functionList) {
        returnToken = function.run();
      }
      counterVariable.setValue(counterVariable.getValue() + 1);
    }
    return returnToken;
  }

  private void parseParameterQueue(Deque<SLogoToken> tokenQueue) {
    try {
      counterVariable = (SLogoVariable) tokenQueue.poll();
      counterVariable.setValue(1);
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid parameter list syntax");
    }
    SLogoFunction helperFunction = new SLogoFunction(tokenQueue,
        modelTurtle);
    limit = (int) helperFunction.run().getValue();
  }
}

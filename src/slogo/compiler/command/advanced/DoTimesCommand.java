package slogo.compiler.command.advanced;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoTokenList;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.EvaluateNumberCommand;
import slogo.compiler.command.SLogoCommand;

public class DoTimesCommand extends SLogoCommand {
  private SLogoVariable counterVariable;
  private int limit;

  public DoTimesCommand() {
    super("DoTimes");
    expectedParameters.add(new SLogoTokenList("parameters"));
    expectedParameters.add(new SLogoTokenList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    SLogoTokenList parameterList = (SLogoTokenList) expectedParameters.get(0);
    Deque<SLogoToken> parameterQueue = new ArrayDeque<>(parameterList.getTokenList());
    parseParameterQueue(parameterQueue);
    SLogoTokenList commandTokens = (SLogoTokenList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    List<SLogoFunction> functionList = new ArrayList<>();
    while (! commandQueue.isEmpty()) {
      try {
        SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
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
    SLogoFunction helperFunction = new SLogoFunction(new EvaluateNumberCommand(), tokenQueue);
    limit = (int) helperFunction.run().getValue();
  }
}

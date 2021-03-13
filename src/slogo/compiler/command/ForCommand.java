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
    SLogoList parameterList = (SLogoList) expectedParameters.get(0);
    Deque<SLogoToken> parameterQueue = new ArrayDeque<>(parameterList.getTokenList());
    parseParameterQueue(parameterQueue);
    SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    List<SLogoFunction> functionList = new ArrayList<>();
    while (! commandQueue.isEmpty()) {
      try {
        SLogoCommand innerCommand = (SLogoCommand) commandQueue.poll();
        SLogoFunction innerFunction = new SLogoFunction(innerCommand, commandQueue,
            modelTurtle);
        functionList.add(innerFunction);
      }
      catch (ClassCastException e) {
        throw new SLogoException("Invalid command list syntax");
      }
    }
    SLogoToken returnToken = new SLogoConstant(0);
    for (int i = start; i <= end; i += increment) {
      counterVariable.setValue(i);
      for (SLogoFunction function : functionList) {
        returnToken = function.run();
      }
    }
    return returnToken;
  }

  private void parseParameterQueue(Deque<SLogoToken> tokenQueue) {
    try {
      counterVariable = (SLogoVariable) tokenQueue.poll();
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid parameter list syntax");
    }
    SLogoFunction helperFunction = new SLogoFunction(new EvaluateNumberCommand(), tokenQueue,
        modelTurtle);
    start = (int) helperFunction.run().getValue();
    helperFunction = new SLogoFunction(new EvaluateNumberCommand(), tokenQueue, modelTurtle);
    end = (int) helperFunction.run().getValue();
    helperFunction = new SLogoFunction(new EvaluateNumberCommand(), tokenQueue, modelTurtle);
    increment = (int) helperFunction.run().getValue();
  }
}

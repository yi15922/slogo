package slogo.compiler.command.advanced;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoFunction;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoTokenList;
import slogo.compiler.SLogoVariable;
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
    // todo: add counterVariable to the workspace
    SLogoTokenList commandTokens = (SLogoTokenList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    // todo: check that first token is a command
    List<SLogoFunction> functionList = new ArrayList<>();
    while (! commandQueue.isEmpty()) { // todo: error checking
      SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
      functionList.add(innerFunction);
    }
    SLogoToken returnToken = new SLogoConstant(0);
    // todo: generalize turning list of commands into function, will need to save commands for repeated runs
    for (int i = 1; i <= limit; i++) {
      for (SLogoFunction function : functionList) {
        returnToken = function.run();
      }
      // todo: update variable in the workspace
      // todo: figure out how Function accesses workspace
    }
    return returnToken;
  }

  private void parseParameterQueue(Deque<SLogoToken> tokenQueue) {
    // todo: check that first token is generic token/valid variable name
    counterVariable = new SLogoVariable(tokenQueue.poll().toString(), 1.0);
    SLogoFunction helperFunction = new SLogoFunction(new EvaluateNumberCommand(), tokenQueue);
    limit = (int) helperFunction.run().getValue();
  }
}

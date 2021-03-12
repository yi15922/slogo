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
    List<SLogoFunction> functionList = new ArrayList<>();
    while (! commandQueue.isEmpty()) { // todo: error checking
      SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
      functionList.add(innerFunction);
    }
    // todo: generalize turning list of commands into function, will need to save commands for repeated runs
    SLogoToken returnToken = new SLogoConstant(0);
    for (int i = start; i < end; i += increment) {
      for (SLogoFunction function : functionList) {
        returnToken = function.run();
      }
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

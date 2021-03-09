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

public class RepeatCommand extends SLogoCommand {

  public RepeatCommand() {
    super("Repeat");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoTokenList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    int numTimesToRepeat = (int) expectedParameters.get(0).getValue();
    SLogoVariable repcount = new SLogoVariable("repcount", 1.0);
    // todo: add repcount to the workspace
    SLogoTokenList commandTokens = (SLogoTokenList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    // todo: check that first token is a command
    SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
    SLogoToken returnToken = new SLogoConstant(0);
    // todo: generalize turning list of commands into function, will need to save commands for repeated runs
    for (int i = 1; i < numTimesToRepeat; i++) { // runs numTimes-1 times, last time needs to return a value
      returnToken = innerFunction.run();
      // todo: update repcount in the workspace
      // todo: figure out how Function accesses workspace
    }
    return returnToken;
  }
}

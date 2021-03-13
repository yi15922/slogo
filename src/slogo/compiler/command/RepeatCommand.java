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

public class RepeatCommand extends SLogoCommand {

  public RepeatCommand() {
    super("Repeat");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    int numTimesToRepeat = (int) expectedParameters.get(0).getValue();
    SLogoVariable repcount = new SLogoVariable("repcount", 1.0);
    // todo: add repcount to the workspace
    SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
    Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    // todo: check that first token is a command
    List<SLogoFunction> functionList = new ArrayList<>();
    while (! commandQueue.isEmpty()) { // todo: error checking
      SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
      functionList.add(innerFunction);
    }
    SLogoToken returnToken = new SLogoConstant(0);
    // todo: generalize turning list of commands into function, will need to save commands for repeated runs
    for (int i = 1; i <= numTimesToRepeat; i++) {
      for (SLogoFunction function : functionList) {
        returnToken = function.run();
      }
      // todo: update repcount in the workspace
      // todo: figure out how Function accesses workspace
    }
    return returnToken;
  }
}

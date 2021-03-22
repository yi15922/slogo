package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.Deque;
import slogo.SLogoException;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoVariable;

public class IfElseCommand extends SLogoCommand {

  public IfElseCommand() {
    super("IfElse");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoList("true commands"));
    expectedParameters.add(new SLogoList("false commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    Deque<SLogoToken> commandQueue;
    SLogoList commandTokens;
    if (expectedParameters.get(0).getValue() != 0.0) {
      commandTokens = (SLogoList) expectedParameters.get(1);
    }
    else {
      commandTokens = (SLogoList) expectedParameters.get(2);
    }
    commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    return new SLogoFunction(commandQueue, modelTurtle).run();
  }
}

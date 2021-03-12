package slogo.compiler.command.advanced;

import java.util.ArrayDeque;
import java.util.Deque;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoTokenList;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class IfElseCommand extends SLogoCommand {

  public IfElseCommand() {
    super("IfElse");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoTokenList("true commands"));
    expectedParameters.add(new SLogoTokenList("false commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    Deque<SLogoToken> commandQueue;
    SLogoTokenList commandTokens;
    if (expectedParameters.get(0).getValue() != 0.0) {
      // todo: refactor code to extract method that creates inner function from token list
      commandTokens = (SLogoTokenList) expectedParameters.get(1);
      commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    }
    else {
      commandTokens = (SLogoTokenList) expectedParameters.get(2);
      commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    }
    SLogoToken returnToken = new SLogoConstant(0);
    while (! commandQueue.isEmpty()) {
      try {
        SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
        returnToken = innerFunction.run();
      }
      catch (ClassCastException e) {
        throw new SLogoException("Invalid command list syntax");
      }
    }
    return returnToken;
  }
}

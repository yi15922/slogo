package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.Deque;
import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoFunction;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoTokenList;
import slogo.compiler.SLogoVariable;

public class IfCommand extends SLogoCommand {

  public IfCommand() {
    super("If");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoTokenList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() != 0.0) {
      // todo: refactor code to extract method that creates inner function from token list
      SLogoTokenList commandTokens = (SLogoTokenList) expectedParameters.get(1);
      Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
      // todo: check that first token is a command
      SLogoToken returnToken = new SLogoConstant(0);
      while (! commandQueue.isEmpty()) {
        SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
        returnToken = innerFunction.run();
      }
      return returnToken;
    }
    else {
      return new SLogoConstant(0);
    }
  }
}

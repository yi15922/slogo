package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.Deque;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoVariable;

public class IfCommand extends SLogoCommand {

  public IfCommand() {
    super("If");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() != 0.0) {
      // todo: refactor code to extract method that creates inner function from token list
      SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
      Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
      SLogoToken returnToken = new SLogoConstant(0);
      while (! commandQueue.isEmpty()) {
        try {
          SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue,
              modelTurtle);
          returnToken = innerFunction.run();
        }
        catch (ClassCastException e) {
          throw new SLogoException("Invalid command list syntax");
        }
      }
      return returnToken;
    }
    else {
      return new SLogoConstant(0);
    }
  }
}
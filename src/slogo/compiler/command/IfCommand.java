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
      SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
      Deque<SLogoToken> commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
      return new SLogoFunction(commandQueue, modelTurtle).runFunction();
    }
    else {
      return new SLogoConstant(0);
    }
  }
}

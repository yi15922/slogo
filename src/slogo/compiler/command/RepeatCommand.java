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
  private Deque<SLogoToken> commandQueue;
  private SLogoVariable repcountVariable;

  public RepeatCommand() {
    super("Repeat");
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoList("commands"));
    repcountVariable = new SLogoVariable("repcount");
  }

  @Override
  public SLogoToken run() throws SLogoException {
    int numTimesToRepeat = (int) expectedParameters.get(0).getValue();
    try {
      SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
      commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid command syntax");
    }
    for (SLogoToken token : commandQueue) {
      if (token.isEqualTokenType(new SLogoVariable("")) && token.toString().equals("repcount")) {
        repcountVariable = (SLogoVariable) token;
      }
    }
    List<SLogoFunction> functionList = new ArrayList<>();
    while (! commandQueue.isEmpty()) { // todo: error checking
      try {
        SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) commandQueue.poll(), commandQueue);
        functionList.add(innerFunction);
      }
      catch (ClassCastException e) {
        throw new SLogoException("Invalid command list syntax");
      }
    }
    SLogoToken returnToken = new SLogoConstant(0);
    for (int i = 1; i <= numTimesToRepeat; i++) {
      repcountVariable.setValue(i);
      for (SLogoFunction function : functionList) {
        returnToken = function.run();
      }
    }
    return returnToken;
  }
}

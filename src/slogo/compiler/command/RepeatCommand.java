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
    SLogoList commandTokens = (SLogoList) expectedParameters.get(1);
    commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    for (SLogoToken token : commandQueue) {
      if (token.isEqualTokenType(new SLogoVariable("")) && token.toString().equals(":repcount")) {
        System.out.println("Found repcount variable");
        repcountVariable = (SLogoVariable) token;
        break;
      }
    }
    return new LoopHelper(1, numTimesToRepeat, 1, new SLogoFunction(commandQueue, modelTurtle),
        repcountVariable).run();
  }
}

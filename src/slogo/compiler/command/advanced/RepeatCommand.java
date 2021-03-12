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
import slogo.compiler.command.SLogoCommand;

public class RepeatCommand extends SLogoCommand {
  private Deque<SLogoToken> commandQueue;
  private SLogoVariable repcountVariable;

  public RepeatCommand() {
    super("Repeat");
    // todo: require Parser to add a "repcount" token so RepeatCommand doesn't need access to the workspace
    expectedParameters.add(new SLogoVariable("repcount"));
    expectedParameters.add(new SLogoVariable("expr"));
    expectedParameters.add(new SLogoTokenList("commands"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    int numTimesToRepeat = (int) expectedParameters.get(1).getValue();
    try {
      repcountVariable = (SLogoVariable) expectedParameters.get(0);
      SLogoTokenList commandTokens = (SLogoTokenList) expectedParameters.get(2);
      commandQueue = new ArrayDeque<>(commandTokens.getTokenList());
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid command syntax");
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

package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoVariable;

public class SLogoUserDefinedCommand extends SLogoCommand {
  private Deque<SLogoToken> tokenQueue;
  private Map<String, Integer> variableMap;

  public SLogoUserDefinedCommand(String name) throws SLogoException {
    super(name);
    variableMap = new HashMap<>();
    tokenQueue = new ArrayDeque<>();
  }

  public void giveParameters(SLogoList variables, SLogoList commands) throws SLogoException {
    for (SLogoToken token : variables.getTokenList()) {
      expectedParameters.add(new SLogoVariable(token.toString()));
      // map variable name to location in expectedParameters
      variableMap.put(token.toString(), expectedParameters.size() - 1);
    }
    tokenQueue = new ArrayDeque<>(commands.getTokenList());
    if (! verifyCommandDefinition()) {
      throw new SLogoException("Invalid command definition");
    }
  }

  @Override
  public SLogoToken run() throws SLogoException {
    Deque<SLogoToken> replacedCommandQueue = new ArrayDeque<>();
    for (SLogoToken token : tokenQueue) {
      if (token.isEqualTokenType(new SLogoVariable("dummy"))) { // needs variable reference
        if (variableMap.containsKey(token.toString())) {
          token = expectedParameters.get(variableMap.get(token.toString()));
        }
        else {
          throw new SLogoException("Variable name not recognized");
        }
      }
      replacedCommandQueue.add(token);
    }
    return new SLogoFunction(replacedCommandQueue, modelTurtle).runFunction();
  }

  private boolean verifyCommandDefinition() throws SLogoException {
    Deque<SLogoToken> dummyCommandQueue = new ArrayDeque<>();
    for (SLogoToken token : tokenQueue) {
      if (token.isEqualTokenType(new SLogoVariable("dummy"))) { // needs variable reference
        if (variableMap.containsKey(token.toString())) {
          token = new SLogoConstant(1);
        }
        else {
          return false;
        }
      }
      dummyCommandQueue.add(token);
    }
    try {
      new SLogoFunction(dummyCommandQueue, modelTurtle).runFunction();
    }
    catch (SLogoException e) {
      return false;
    }
    return true;
  }
}

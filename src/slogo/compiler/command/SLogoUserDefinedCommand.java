package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoFunction;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoTokenList;
import slogo.compiler.SLogoVariable;

public class SLogoUserDefinedCommand extends SLogoCommand {
  private Deque<SLogoToken> commandQueue;
  private List<SLogoFunction> functionList;
  private Map<String, Integer> variableMap;

  public SLogoUserDefinedCommand(String name, SLogoTokenList variables, SLogoTokenList commands) {
    super(name);
    for (SLogoToken token : variables.getTokenList()) {
      expectedParameters.add(new SLogoVariable(token.toString()));
      // map variable name to location in expectedParameters
      variableMap.put(token.toString(), expectedParameters.size() - 1);
    }
    commandQueue = new ArrayDeque<>(commands.getTokenList());
  }

  @Override
  public SLogoToken run() throws SLogoException {
    Deque<SLogoToken> replacedCommandQueue = new ArrayDeque<>();
    for (SLogoToken token : commandQueue) {
      if (token.isEqualTokenType(new SLogoVariable("dummy"))) { // needs variable reference
        if (variableMap.containsKey(token.toString())) {
          token = expectedParameters.get(variableMap.get(token.toString()));
        }
        else {
          throw new SLogoException("Variable name not recognized"); // todo: check workspace
        }
      }
      replacedCommandQueue.add(token);
    }
    while (! replacedCommandQueue.isEmpty()) {
      SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) replacedCommandQueue.poll(), replacedCommandQueue);
      functionList.add(innerFunction);
    }
    SLogoToken returnToken = new SLogoConstant(0);
    for (SLogoFunction function : functionList) {
      returnToken = function.run();
    }
    return returnToken;
  }

  private boolean verifyCommandDefinition() throws SLogoException {
    Deque<SLogoToken> dummyCommandQueue = new ArrayDeque<>();
    List<SLogoFunction> dummyFunctionList = new ArrayList<>();
    for (SLogoToken token : commandQueue) {
      if (token.isEqualTokenType(new SLogoVariable("dummy"))) { // needs variable reference
        if (variableMap.containsKey(token.toString())) {
          token = new SLogoConstant(1);
        }
        else {
          throw new SLogoException("Variable name not recognized"); // todo: check workspace
        }
      }
      dummyCommandQueue.add(token);
    }
    while (! dummyCommandQueue.isEmpty()) {
      SLogoFunction innerFunction = new SLogoFunction((SLogoCommand) dummyCommandQueue.poll(), dummyCommandQueue);
      dummyFunctionList.add(innerFunction);
    }
    return true;
  }
}

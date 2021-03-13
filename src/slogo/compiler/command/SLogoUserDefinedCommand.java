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
  private List<SLogoFunction> functionList;
  private Map<String, Integer> variableMap;

  /**
   * Default constructor, used for instantiation by reflection
   * @param name Name of a defined command.
   */
  public SLogoUserDefinedCommand(String name){
    super(name);
  }

  public SLogoUserDefinedCommand(String name, SLogoList variables, SLogoList inputTokens) throws SLogoException {
    super(name);
    functionList = new ArrayList<>();
    variableMap = new HashMap<>();
    for (SLogoToken token : variables.getTokenList()) {
      expectedParameters.add(new SLogoVariable(token.toString()));
      // map variable name to location in expectedParameters
      variableMap.put(token.toString(), expectedParameters.size() - 1);
    }
    tokenQueue = new ArrayDeque<>(inputTokens.getTokenList());
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
    while (! replacedCommandQueue.isEmpty()) {
      SLogoCommand command = (SLogoCommand) replacedCommandQueue.poll();
      SLogoFunction innerFunction = new SLogoFunction(command, replacedCommandQueue);
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
    while (! dummyCommandQueue.isEmpty()) {
      SLogoCommand command = (SLogoCommand) dummyCommandQueue.poll(); // todo: check for command
      SLogoFunction innerFunction = new SLogoFunction(command, dummyCommandQueue);
      dummyFunctionList.add(innerFunction);
      command.resetCommand();
    }
    return true;
  }
}

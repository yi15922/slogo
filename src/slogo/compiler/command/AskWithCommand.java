package slogo.compiler.command;

import java.util.ArrayDeque;
import slogo.SLogoException;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoToken;

public class AskWithCommand extends SLogoCommand {

  public AskWithCommand() {
    super("Ask With");
    expectedParameters.add(new SLogoList("condition"));
    expectedParameters.add(new SLogoList("commands"));
  }

  /**
   * Passes two {@code SLogoFunction} objects to the Turtle. The first function is a "condition"
   * check that should be run on each individual turtle to determine whether the second function
   * should be run on that turtle. Error checking of function syntax should be performed by the
   * Turtle class.
   * @return - result of last command run
   * @throws SLogoException
   */
  @Override
  public SLogoToken run() throws SLogoException {
    SLogoList conditionList = (SLogoList) expectedParameters.get(0);
    SLogoList commandList = (SLogoList) expectedParameters.get(1);
    SLogoFunction conditionFunction = new SLogoFunction(new ArrayDeque<>(conditionList.getTokenList()), modelTurtle);
    SLogoFunction commandFunction = new SLogoFunction(new ArrayDeque<>(commandList.getTokenList()), modelTurtle);
    // todo: call Turtle method with conditionFunction and commandFunction
    return null;
  }
}

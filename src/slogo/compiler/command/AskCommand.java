package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

public class AskCommand extends SLogoCommand {

  public AskCommand() {
    super("Ask");
    expectedParameters.add(new SLogoList("turtles"));
    expectedParameters.add(new SLogoList("commands"));
  }

  /**
   * Passes a list of integers and a {@code SLogoFunction} object to the {@code Turtle}. The
   * list of integers contains all IDs of turtles that should be active. The function should
   * be run on only the turtles with active IDs.
   * @return - the result of the last command run by the last turtle in the list
   * @throws SLogoException
   */
  @Override
  public SLogoToken run() throws SLogoException {
    SLogoList tokenList = (SLogoList) expectedParameters.get(0);
    SLogoList commandList = (SLogoList) expectedParameters.get(1);
    List<Integer> turtleIDs = new TurtleListHelper(tokenList, modelTurtle).getTurtleIDList();
    return new SLogoConstant(modelTurtle.ask(turtleIDs, new SLogoFunction(new ArrayDeque<>(commandList.getTokenList()), modelTurtle)));
  }

}

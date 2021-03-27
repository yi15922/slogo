package slogo.compiler.command;

import java.util.ArrayDeque;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoToken;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of specifying a condition that a turtle
 * has to meet in order to run the given commands.
 */
public class AskWithCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public AskWithCommand() {
    super("AskWith");
    expectedParameters.add(new SLogoList("condition"));
    expectedParameters.add(new SLogoList("commands"));
  }

  /**
   * Passes two {@code SLogoFunction} objects to the Turtle. The first function is a "condition"
   * check that should be run on each individual turtle to determine whether the second function
   * should be run on that turtle. Error checking of function syntax should be performed by the
   * Turtle class.
   * @return - result of last command run
   */
  @Override
  public SLogoToken run() throws SLogoException {
    SLogoList conditionList = (SLogoList) expectedParameters.get(0);
    SLogoList commandList = (SLogoList) expectedParameters.get(1);
    SLogoFunction conditionFunction = new SLogoFunction(new ArrayDeque<>(conditionList.getTokenList()), modelTurtle);
    SLogoFunction commandFunction = new SLogoFunction(new ArrayDeque<>(commandList.getTokenList()), modelTurtle);
    return new SLogoConstant(modelTurtle.askWith(conditionFunction, commandFunction));
  }
}

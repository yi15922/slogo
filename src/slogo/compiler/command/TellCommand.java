package slogo.compiler.command;

import java.util.ArrayList;
import java.util.List;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoToken;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of creating new turtles.
 */
public class TellCommand extends SLogoCommand {
  private List<Integer> turtleIDs;

  /**
   * Initializes the command with name and one expected parameter
   */
  public TellCommand() {
    super("Tell");
    expectedParameters.add(new SLogoList("turtles"));
    turtleIDs = new ArrayList<>();
  }

  /**
   * Calls Turtle method with a list of integers representing turtle IDs to be created. These IDs
   * are not checked, meaning negative IDs or IDs that are already in existence may be passed to
   * the Turtle.
   * @return - the result of the Turtle tell method
   * @throws SLogoException - if the list of turtle IDs is incorrectly formatted
   */
  @Override
  public SLogoToken run() throws SLogoException {
    SLogoList tokenList = (SLogoList) expectedParameters.get(0);
    turtleIDs = new TurtleListHelper(tokenList, modelTurtle).getTurtleIDList();
    if (turtleIDs.size() == 1) {
      singleElementList();
    }
    return new SLogoConstant(modelTurtle.tell(turtleIDs));
  }

  // turns single element into list containing all integers up to that element
  private void singleElementList() {
    int lastID = turtleIDs.get(0);
    turtleIDs.clear();
    for (int i = 1; i <= lastID; i++) {
      turtleIDs.add(i);
    }
  }

}

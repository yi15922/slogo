package slogo.compiler.command;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import slogo.SLogoException;
import slogo.Turtle;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

public class TurtleListHelper {
  private SLogoList tokenList;
  private Turtle modelTurtle;

  public TurtleListHelper(SLogoList tokenList, Turtle modelTurtle) {
    this.tokenList = tokenList;
    this.modelTurtle = modelTurtle;
  }

  public List<Integer> getTurtleIDList() {
    Deque<SLogoToken> givenTokens = new ArrayDeque<>(tokenList.getTokenList());
    List<Integer> turtleIDs = new ArrayList<>();
    while (! givenTokens.isEmpty()) {
      SLogoToken nextToken = givenTokens.peek();
      if (nextToken.isEqualTokenType(new SLogoConstant(0)) || nextToken.isEqualTokenType(new SLogoVariable(""))) {
        turtleIDs.add((int) givenTokens.poll().getValue());
      }
      else {
        try {
          SLogoToken resultToken = new SLogoFunction(givenTokens, modelTurtle).runSingleCommand();
          givenTokens.addFirst(resultToken);
        }
        catch (SLogoException e) {
          throw new SLogoException("Invalid turtle ID list");
        }
      }
    }
    return turtleIDs;
  }
}

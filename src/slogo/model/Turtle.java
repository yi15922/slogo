package slogo.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.TurtleModel;
import slogo.compiler.command.SLogoUserDefinedCommand;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.observers.ModelObserver;

/**
 * @author Kenneth Moore III
 * Purpose: to store, update and return the details of multiple turtles
 * Assumptions: Assumes appropriate packages
 * Dependencies: depends on TurtleModel, which it extends, and TurtleInterface, which it implements
 * Example: Turtle t = new Turtle;
 */
public class Turtle extends TurtleModel implements TurtleInterface {

  Map<Integer, SingleTurtle> turtleMap;
  Map<Integer, Boolean> activeMap;
  private int myBackground;
  private ModelObserver myTurtleObserver;

  /**
   * Construcctor for turtle
   */
  public Turtle() {
    turtleMap = new HashMap<>();
    activeMap = new HashMap<>();
    putTurtleIfAbsent(1, new SingleTurtle(1));
    activeMap.put(1, true);
    myBackground = 0;
  }


  @Override
  /**
   * loops through forward command for each active turtle
   */
  public double forward(double pixels) {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).forward(pixels);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through back command for each active turtle
   */
  public double back(double pixels) {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).back(pixels);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through left command for each active turtle
   */
  public double left(double degrees) {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).left(degrees);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through right command for each active turtle
   */
  public double right(double degrees) {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).right(degrees);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through setHeading command for each active turtle
   */
  public double setHeading(double degrees) {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).setHeading(degrees);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through towards command for each active turtle
   */
  public double towards(double x, double y) {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).towards(x, y);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through setXY command for each active turtle
   */
  public double setXY(double x, double y) {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).setXY(x, y);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through penDown command for each active turtle
   */
  public double penDown() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).penDown();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through penUp command for each active turtle
   */
  public double penUp() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).penUp();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through showTurtle command for each active turtle
   */
  public double showTurtle() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).showTurtle();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through hideTurtle command for each active turtle
   */
  public double hideTurtle() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).hideTurtle();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through home command for each active turtle
   */
  public double home() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).home();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through clearScreen command for each active turtle
   */
  public double clearScreen() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).clearScreen();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through xCor command for each active turtle
   */
  public double xCor() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).xCor();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through yCor command for each active turtle
   */
  public double yCor() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).yCor();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through heading command for each active turtle.
   */
  public double heading() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).heading();
      }
    }
    return returned;
  }

  /**
   * loops through penDownP command for each active turtle.
   */
  @Override
  public int penDownP() {
    int returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).penDownP();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through showingP command for each active turtle
   */
  public int showingP() {
    int returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).showingP();
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through setPenColor command for each active turtle
   */
  public int setPenColor(int index) {
    int returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).setPenColor(index);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through setPenSize command for each active turtle
   */
  public double setPenSize(double pixels) {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).setPenSize(pixels);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through setShape command for each active turtle
   */
  public int setShape(int index) {
    int returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).setShape(index);
      }
    }
    return returned;
  }

  @Override
  /**
   * loops through penColor command for each active turtle
   */
  public int penColor() {
    int returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).penColor();
      }
    }
    return returned;
  }

  /**
   * loops through shape command for each active turtle
   */
  @Override
  public int shape() {
    int returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).shape();
      }
    }
    return returned;
  }

  /**
   * returns the number of turtles made.
   * @return the size of the key set for tracking turtles
   */
  public int turtles() {
    return turtleMap.keySet().size();
  }

  /**
   * activates the turtles in the list.  if no tuurtle exists it creates a new turtle.
   * @param turtleIds list of turtle Ids
   * @return the id of the last turtle activated
   */
  public int tell(List<Integer> turtleIds) {
    deactivateMap(activeMap);
    System.out.println(turtleIds);
    for (int id : turtleIds) {
      putTurtleIfAbsent(id, new SingleTurtle(id));
      activeMap.put(id, true);
    }
    //need to do command
    return turtleIds.get(turtleIds.size() - 1);
  }

  /**
   * runs a given method on a list of turrtles.
   * @param turtleIds the list of turtles
   * @param method the method to be run on all turtles
   * @return value of last return from method ran
   */
  public double ask(List<Integer> turtleIds, SLogoFunction method) {
    Map<Integer, Boolean> originalActiveMap = new HashMap<>(activeMap);
    deactivateMap(activeMap);
    for (Integer id : turtleIds) {
      activeMap.put(id, true);
    }
    double returned = method.run().getValue();
    activeMap = originalActiveMap;
    return returned;
  }

  /**
   * usues ask, but activates turtles based on a condition
   * @param condition that is tested to activate turtles
   * @param method method that is run on active turtles
   * @return return value of last turtle from method ran
   */
  public double askWith(SLogoFunction condition, SLogoFunction method) {
    Map<Integer, Boolean> originalActiveMap = new HashMap<>(activeMap);
    deactivateMap(activeMap);

    List<Integer> createdActiveList = new ArrayList<>();

    for (Integer id : turtleMap.keySet()) {
      activeMap.put(id, true);
      if (condition.run().getValue() == 1) {
        createdActiveList.add(id);
      }
      activeMap.put(id, false);
    }

    activeMap = originalActiveMap;
    return ask(createdActiveList, method);
  }

  /**
   * changes the background color through an index palette
   * @param index the background color index representation
   * @return the index
   */
  public int setBackground(int index) {
    myBackground = index;
    notifyObserverOfBackground(index);
    return index;
  }

  private void putTurtleIfAbsent(int id, SingleTurtle turtle) {
    if(!turtleMap.keySet().contains(id)) {
      System.out.println("Added turtle " + id + " in map");
      turtleMap.put(id, turtle);
      if (myTurtleObserver != null) {
//        notifyObserverOfNewTurtle(id);
        myTurtleObserver.addTurtle(id);
        turtle.addObserver(myTurtleObserver);
      }
    }
  }

  /**
   * adds observer of new turtle
   * @param o
   */
  public void assignObserverForTurtles(ModelObserver o) {
    myTurtleObserver = o;
//    notifyObserverOfNewTurtle(1);
    myTurtleObserver.addTurtle(1);
    turtleMap.get(1).addObserver(myTurtleObserver);
  }

  private void deactivateMap(Map<Integer, Boolean> map) {
    map.replaceAll((i, v) -> false);
  }

  /**
   * returns the ID of a turtle that is currently having a fucntion run on it
   * @param commandToRun
   * @return SLogoToken
   */
  public SLogoToken runIDFunction(SLogoUserDefinedCommand commandToRun) {
    SLogoToken returned = new SLogoConstant(0);
    Map<Integer, Boolean> copyActiveMap = new HashMap<>(activeMap);
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        deactivateMap(activeMap);
        activeMap.put(id, true);
        Deque<SLogoToken> commandQueue = new ArrayDeque<>();
        commandQueue.add(commandToRun);
        commandQueue.add(new SLogoConstant(id));
        returned = new SLogoFunction(commandQueue, this).run();
        activeMap = new HashMap<>(copyActiveMap);
      }
    }
    return returned;
  }
}
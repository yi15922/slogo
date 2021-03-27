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


public class Turtle extends TurtleModel implements TurtleInterface {

  Map<Integer, SingleTurtle> turtleMap;
  Map<Integer, Boolean> activeMap;
  private int myBackground;
  private ModelObserver myTurtleObserver;

  public Turtle() {
    turtleMap = new HashMap<>();
    activeMap = new HashMap<>();
    putTurtleIfAbsent(1, new SingleTurtle(1));
    activeMap.put(1, true);
    myBackground = 0;
  }


  @Override
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
  public double heading() {
    double returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).heading();
      }
    }
    return returned;
  }

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
  public int penColor() {
    int returned = -1;
    for (Integer id : turtleMap.keySet()) {
      if (activeMap.get(id)) {
        returned = turtleMap.get(id).penColor();
      }
    }
    return returned;
  }

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
  
  public int turtles() {
    return turtleMap.keySet().size();
  }

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

  public int setBackground(int index) {
    myBackground = index;
//    notifyObserverOfBackground(index);
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

  public void assignObserverForTurtles(ModelObserver o) {
    myTurtleObserver = o;
//    notifyObserverOfNewTurtle(1);
    myTurtleObserver.addTurtle(1);
    turtleMap.get(1).addObserver(myTurtleObserver);
  }

  private void deactivateMap(Map<Integer, Boolean> map) {
    map.replaceAll((i, v) -> false);
  }

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
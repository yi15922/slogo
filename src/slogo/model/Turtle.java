package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.compiler.token.SLogoFunction;


public class Turtle implements TurtleInterface {

  Map<Integer, SingleTurtle> turtleMap;
  Map<Integer, Boolean> activeMap;

  public Turtle() {
    turtleMap = new HashMap<>();
    activeMap = new HashMap<>();
    putIfAbsentActiveTurtle(1, new SingleTurtle(1));
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

  @Override
  public int turtles() {
    return turtleMap.get(1).turtles();
  }

  public int tell(List<Integer> turtles) {
    deactivateMap(activeMap);
    for (int i : turtles) {
      //TODO: notify of new turtle
      putIfAbsentActiveTurtle(i, new SingleTurtle(i));
      activeMap.put(i, true);
    }
    //need to do command
    return turtles.get(turtles.size() - 1);
  }

  public double ask(List<Integer> turtles, SLogoFunction method) {
    Map<Integer, Boolean> originalActiveMap = new HashMap<>(activeMap);
    deactivateMap(activeMap);
    for (Integer i : turtles) {
      activeMap.put(i, true);
    }
    double returned = method.run().getValue();
    activeMap = originalActiveMap;
    return returned;
  }

  public double askWith(SLogoFunction condition, SLogoFunction method) {
    Map<Integer, Boolean> originalActiveMap = new HashMap<>(activeMap);
    deactivateMap(activeMap);

    List<Integer> createdActiveList = new ArrayList<>();

    for (Integer i : turtleMap.keySet()) {
      activeMap.put(i, true);
      if (condition.run().getValue() == 1) {
        createdActiveList.add(i);
      }
      activeMap.put(i, false);
    }

    activeMap = originalActiveMap;
    return ask(createdActiveList, method);
  }

  private void putIfAbsentActiveTurtle(int id, SingleTurtle turtle) {
    turtleMap.putIfAbsent(id, turtle);
    activeMap.putIfAbsent(id, true);
  }

  private void deactivateMap(Map<Integer, Boolean> map) {
    map.replaceAll((i, v) -> false);
  }
}
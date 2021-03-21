package slogo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.SlogoModel;
import slogo.model.SingleTurtle;
import slogo.model.TurtleInterface;

public class Turtle extends SlogoModel implements TurtleInterface {

  Map<Integer, SingleTurtle> turtleMap;
  Map<Integer, Boolean> activeMap;

  public Turtle() {
    turtleMap =  new HashMap<>();
    activeMap = new HashMap<>();
    putIfAbsentActiveTurtle(1, new SingleTurtle());
  }

  @Override
  public double forward(double pixels) {
    return 0;
//    double returned;
//    for (Turtle t: turtleList) {
//      if(t.isActive()){
//        returned = t.forward(pixels);
//      }
//    }
//    return returned;
  }

  @Override
  public double back(double pixels) {
    return 0;
  }

  @Override
  public double left(double degrees) {
    return 0;
  }

  @Override
  public double right(double degrees) {
    return 0;
  }

  @Override
  public double setHeading(double degrees) {
    return 0;
  }

  @Override
  public double towards(double x, double y) {
    return 0;
  }

  @Override
  public double setXY(double x, double y) {
    return 0;
  }

  @Override
  public double penDown() {
    return 0;
  }

  @Override
  public double penUp() {
    return 0;
  }

  @Override
  public double showTurtle() {
    return 0;
  }

  @Override
  public double hideTurtle() {
    return 0;
  }

  @Override
  public double home() {
    return 0;
  }

  @Override
  public double clearScreen() {
    return 0;
  }

  @Override
  public double xCor() {
    return 0;
  }

  @Override
  public double yCor() {
    return 0;
  }

  @Override
  public double heading() {
    return 0;
  }

  @Override
  public int penDownP() {
    return 0;
  }

  @Override
  public int showingP() {
    return 0;
  }

  @Override
  public int setPenColor(int index) {
    return 0;
  }

  @Override
  public double setPenSize(double pixels) {
    return 0;
  }

  @Override
  public int setShape(int index) {
    return 0;
  }

  @Override
  public int penColor() {
    return 0;
  }

  @Override
  public int shape() {
    return 0;
  }

  @Override
  public int turtles() {
    return turtleMap.get(1).turtles();
  }

  public int tell(List<Integer> turtles) {
    deactivateTurtles();
    for (int i : turtles) {
      putIfAbsentActiveTurtle(i, new SingleTurtle());
      activeMap.put(i, true);
    }
    //need to do command
    return turtles.get(turtles.size() - 1);
  }

  private void putIfAbsentActiveTurtle(int id, SingleTurtle turtle){
    turtleMap.putIfAbsent(id, turtle);
    activeMap.putIfAbsent(id, true);
  }

  private void deactivateTurtles() {
    for (SingleTurtle turtle : turtleMap.values()) {
      turtle.deactivate();
    }
  }
}
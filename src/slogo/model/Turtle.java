package slogo.model;

import java.util.List;
import slogo.SlogoModel;

public class Turtle extends SlogoModel implements TurtleInterface {

  List<SingleTurtle> turtleList;

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
}
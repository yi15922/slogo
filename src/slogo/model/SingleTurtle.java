package slogo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import slogo.SlogoModel;

/**
 * @author Kenneth Moore III
 * Purpose: to store, update and return the details of an individual turtle
 * Assumptions: Assumes appropriate packages
 * Dependencies: depends on SlogoModel, which it extends, and TurtleInterface, which it implements
 * Example: SingleTurtle t = new Singleturtle;
 */
public class SingleTurtle extends SlogoModel implements TurtleInterface {

  private double myX;
  private double myY;
  private double myAngle;
  private Pen myPen;
  private boolean myShow;
  private int myID;
  private int myShape;
  private static final int ROUND_DECIMAL_PLACES = 3;

  /**
   * Purpose: Constructor for Single Turtle with id as an input
   * Exceptions: None
   * Return: None
   * @param id value to be assigned myID
   */
  public SingleTurtle(int id) {
    setXY(0, 0);
    setHeading(0);
    myPen = new Pen();
    myShow = true;
    myID = id;
    myShape=0;
  }

  /**
   * Purpose: Constructor for single turtle with default id value of 0
   * Exceptions: None
   * Return: None
   */
  public SingleTurtle() {
    this(0);
  }

  /**
   * Purpose: moves the turtle pixels amount forward in the direction it is facing using triangles
   * Assumptions: assume the Math functions work as well as setXY
   * @param pixels- the amount to move forward
   * @return the amount moved forward
   */
  public double forward(double pixels) {
    double newX = myX + Math.sin(Math.toRadians(myAngle)) * pixels;
    double newY = myY + Math.cos(Math.toRadians(myAngle)) * pixels;
    setXY(newX, newY);
    return round(pixels);
  }

  /**
   * Purpose: moves the turtle backward by flipping, moving forward, and flipping again
   * Assumptions: assumes setHeading, round, and forward work
   * @param pixels- the amount of pixels moving backward
   * @return- the amount of pixels moved backward
   */
  public double back(double pixels) {
    setHeading(myAngle + 180);
    forward(pixels);
    setHeading(myAngle - 180);
    return round(pixels);
  }

  /**
   * Purpose: to turn the turtle left
   * Assumptions: assumes setHeading works
   * @param degrees- the amount of degrees to turn to the left
   * @return the amount of degrees to the left
   */
  public double left(double degrees) {
    setHeading(myAngle - degrees);
    return round(degrees);
  }

  /**
   * Purpose: to turn the turtle right
   * Assumptions: assumes setHeading works
   * @param degrees- the amount of degrees to turn to the right
   * @return the amount of degrees turned
   */
  public double right(double degrees) {
    setHeading(myAngle + degrees);
    return round(degrees);
  }

  /**
   * Purpose: to set the heading to a set number of degrees, notifies observers
   * Assumptions- assumes standardizeAngle and notify methods work
   * @param degrees- the value to set myAngle to
   * @return the difference between angle moved and old angle
   */
  public double setHeading(double degrees) {
    double oldMyAngle = myAngle;
    myAngle = standardizeAngle(degrees);
    notifyObserversOfHeading(myID, myAngle);
    return standardizeAngle(degrees - oldMyAngle);
  }

  /**
   * Purpose: towards the turtle towards a specific point
   * Assumptions: assumes 2pointdistance works, as well as Math functions
   * @param x, y the coordinates the turtle should face
   * @return the return value of setHeading
   */
  public double towards(double x, double y) {
    double hypotenuse = calculate2PointDistance(myX, myY, x, y);
    double dX = x - myX;
    double dY = y - myY;
    double angle = Math.toDegrees(Math.acos((Math.abs(dX / hypotenuse))));
    if (dX > 0) {
      if (dY > 0) {
        angle = 90 - angle;
      } else {
        angle = 90 + angle;
      }
    } else {
      if (dY > 0) {
        angle = 270 + angle;
      } else {
        angle = 270 - angle;
      }
    }
    return setHeading(angle);
  }

  /**
   * Purpose: returns my Angle
   * @return myAngle
   */
  public double heading() {
    return myAngle;
  }

  /**
   * Purpose: set the position of the turtle using x, y coordinates
   * Assumptions: calculate two point distance works
   * @param x, y- the coordinates
   * @return the distannce moved
   */
  public double setXY(double x, double y) {
    double distance = calculate2PointDistance(myX, myY, x, y);
    myX = round(x);
    myY = round(y);
    notifyObserversOfPosition(myID, myX, myY);
    return round(distance);
  }

  public double xCor() {
    return myX;
  }

  public double yCor() {
    return myY;
  }

  /**
   * Purpose: set show variable to true
   * Assumptions: toggleShow works
   * @return 1, the int representation of show being true
   */
  public double showTurtle() {
    if (!myShow) {
      toggleShow();
    }
    return 1;
  }

  /**
   * Purpose: set show variable to false
   * Assumptions: toggleShow works
   * @return 0, the int representation of show being false
   */
  public double hideTurtle() {
    if (myShow) {
      toggleShow();
    }
    return 0;
  }

  private void toggleShow() {
    myShow = !myShow;
    notifyObserversOfShow(myID, myShow);
  }

  /**
   * Purpose: returns the status of the turtle show
   * @return the int representation of show
   */
  public int showingP() {
    if (myShow) {
      return 1;
    }
    return 0;
  }

  /**
   * Purpose: returns the turle to the center of screen
   * Assumes setXY works
   * @return the value returned by setXY(0,0)
   */
  public double home() {
    return setXY(0, 0);
  }

  /**
   * Should clear screen and return turtle home.  returns home but was never given method to clear
   * @return home()
   */
  public double clearScreen() {
    //need to clear screen
    return home();
  }

  /**
   * Purpose: puts the pen down and notifies observers of change.  could have some unneeded updates
   * @return myPen.penDown
   */
  public double penDown() {
    notifyObserversOfPenStatus(myID, true);
    return myPen.penDown();
  }

  /**
   * Purpose: puts the pen up and notifies observers of change.  could have some unneeded updates
   * @return myPen.penUp
   */
  public double penUp() {
    notifyObserversOfPenStatus(myID, false);
    return myPen.penUp();
  }

  /**
   * returns if pen is down
   * @return it value for myPen.penDownP()
   */
  public int penDownP() {
    return myPen.penDownP();
  }

  @Override
  /**
   * Purpose: changes pen color using pen object and notifies observers
   * Assumptions: assumes Pen works
   * @param index - the index to set color to
   * @return the int index that param is set to
   */
  public int setPenColor(int index) {
    notifyObserversOfPenColor(myID, index);
    return myPen.setPenColor(index);
  }

  @Override
  /**
   * Purpose: to set pen size using pen object
   * Assumptions: that pen works
   * @param pixels - the width of the pen
   * @return the width the pen was set to
   */
  public double setPenSize(double pixels) {
    return myPen.setPenSize(pixels);
  }

  /**
   * Purpose; to set pen color using pen object
   * Assumes pen works
   * @return the int index it is associated with
   */
  @Override
  public int penColor() {
    return myPen.getPenColor();
  }

  /**
   * set the shape of the turtle.
   * assumes notify works and that index is mapped to shape somewhere else
   * @param index - the index to set shape to
   * @return tbe int the shape was set to
   */
  public int setShape(int index) {
    myShape = index;
    notifyObserversOfShape(myID, myShape);
    return index;
  }

  @Override
  public int shape() {
    return myShape;
  }

  private double standardizeAngle(double angle) {
    double returned = angle;
    while (returned < 0) {
      returned += 360;
    }
    if (returned > 360) {
      returned %= 360;
    }
    return round(returned);
  }

  //https://www.baeldung.com/java-round-decimal-number
  //#4
  private static double round(double value) {
    BigDecimal bd = new BigDecimal(Double.toString(value));
    bd = bd.setScale(ROUND_DECIMAL_PLACES, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  private double calculate2PointDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }
}


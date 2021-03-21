package slogo.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import slogo.SlogoModel;

public class SingleTurtle extends SlogoModel implements TurtleInterface {

  private double myX;
  private double myY;
  private double myAngle;
  private Pen myPen;
  private boolean myShow;
  private int myID;
  private boolean myActive;
  private static int turtleCount = 0;
  private int myShape;
  private static final int ROUND_DECIMAL_PLACES = 3;

  public SingleTurtle(int ID) {
    setXY(0, 0);
    setHeading(0);
    myPen = new Pen();
    myShow = true;
    myID = ID;
    myActive = true;
    turtleCount++;
    myShape=0;
  }

  public SingleTurtle() {
    this(0);
  }

  public double forward(double pixels) {
    double newX = myX + Math.sin(Math.toRadians(myAngle)) * pixels;
    double newY = myY + Math.cos(Math.toRadians(myAngle)) * pixels;
    setXY(newX, newY);
    return round(pixels);
  }

  public double back(double pixels) {
    setHeading(myAngle + 180);
    forward(pixels);
    setHeading(myAngle - 180);
    return round(pixels);
  }

  public double left(double degrees) {
    setHeading(myAngle - degrees);
    return round(degrees);
  }

  public double right(double degrees) {
    setHeading(myAngle + degrees);
    return round(degrees);
  }

  public double setHeading(double degrees) {
    double oldMyAngle = myAngle;
    myAngle = standardizeAngle(degrees);
    notifyObserversOfHeading(myAngle);
    return standardizeAngle(degrees - oldMyAngle);
  }

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

  public double heading() {
    return myAngle;
  }

  public double setXY(double x, double y) {
    double distance = calculate2PointDistance(myX, myY, x, y);
    myX = round(x);
    myY = round(y);
    notifyObserversOfPosition(myX, myY);
    return round(distance);
  }

  public double xCor() {
    return myX;
  }

  public double yCor() {
    return myY;
  }

  //no tests
  public double showTurtle() {
    if (!myShow) {
      toggleShow();
    }
    return 1;
  }

  //no tests
  public double hideTurtle() {
    if (myShow) {
      toggleShow();
    }
    return 0;
  }

  private void toggleShow() {
    myShow = !myShow;
    notifyObserversOfShow(myShow);
  }

  public int showingP() {
    if (myShow) {
      return 1;
    }
    return 0;
  }

  public double home() {
    return setXY(0, 0);
  }

  //TODO: complete
  //no tests
  public double clearScreen() {
    //need to clear screen
    return home();
  }

  //no tests
  //could have some unnecessary updates
  public double penDown() {
    notifyObserversOfPenStatus(true);
    return myPen.penDown();
  }

  //no tests
  //could have some unnecessary updates
  public double penUp() {
    notifyObserversOfPenStatus(false);
    return myPen.penUp();
  }

  public int penDownP() {
    return myPen.penDownP();
  }

  @Override
  public int setPenColor(int index) {
    notifyObserversOfPenColor(index);
    return myPen.setPenColor(index);
  }

  @Override
  public double setPenSize(double pixels) {
    return myPen.setPenSize(pixels);
  }

  @Override
  public int penColor() {
    return myPen.getPenColor();
  }

  public int setShape(int index) {
    myShape = index;
    notifyObserversOfShape(myShape);
    return index;
  }

  @Override
  public int shape() {
    return myShape;
  }

  @Override
  public int turtles() {
    return turtleCount;
  }

  public void activate() {
    myActive = true;
  }

  public void deactivate() {
    myActive = false;
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


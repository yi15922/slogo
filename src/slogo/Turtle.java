package slogo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Turtle extends Observable {

  private double myX;
  private double myY;
  private double myAngle;
  private boolean myPen;
  private boolean myShow;
  private static final int ROUND_DECIMAL_PLACES = 3;

  public Turtle() {
    setX(0);
    setY(0);
    setHeading(0);
    myPen = true;
    myShow = true;
  }

  public double forward(double pixels) {
    setX(myX + Math.sin(Math.toRadians(myAngle)) * pixels);
    setY(myY + Math.cos(Math.toRadians(myAngle)) * pixels);
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
    notifyListeners("HEADING", oldMyAngle, myAngle);
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

  public double setXY(double x, double y) {
    double distance = calculate2PointDistance(myX, myY, x, y);
    setX(x);
    setY(y);
    return round(distance);
  }

  //no tests
  public double penDown() {
    if (!myPen) {
      togglePen();
    }
    return 1;
  }

  //no tests
  public double penUp() {
    if (myPen) {
      togglePen();
    }
    return 0;
  }

  //assumes that pen state has changed
  private void togglePen() {
    myPen = !myPen;
    notifyListeners("PEN", !myPen, myPen);
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
    notifyListeners("PEN", !myShow, myShow);
  }

  public double home() {
    double distance = calculate2PointDistance(myX, myY, 0, 0);
    setX(0);
    setY(0);
    return round(distance);
  }

  //TODO: complete
  //no tests
  public double clearScreen() {
    //need to clear screen
    return home();
  }

  public double xCor() {
    return myX;
  }

  public double yCor() {
    return myY;
  }

  public double heading() {
    return myAngle;
  }

  public int penDownP() {
    if (myPen) {
      return 1;
    }
    return 0;
  }

  public int showingP() {
    if (myShow) {
      return 1;
    }
    return 0;
  }

  private void setX(double x) {
    notifyListeners("X", myX, x);
    myX = round(x);
  }

  private void setY(double y) {
    notifyListeners("Y", myY, y);
    myY = round(y);
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
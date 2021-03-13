package slogo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Turtle {

  private double myX;
  private double myY;
  private double myAngle;
  private boolean myPen;
  private boolean myShow;
  private static final double THRESHOLD = 0.0001;
  private static final int ROUND_DECIMAL_PLACES = 3;

  public Turtle() {
    myX = 0;
    myY = 0;
    myAngle = 0;
    myPen = true;
    myShow = true;
  }

  //TODO: test
  public double forward(double pixels) {
    myX = round(myX + Math.sin(Math.toRadians(myAngle)) * pixels);
    myY = round(myY += Math.cos(Math.toRadians(myAngle)) * pixels);
    return pixels;
  }

  //TODO: tests
  public double back(double pixels) {
    myAngle = standardizeAngle(myAngle+180);
    forward(pixels);
    myAngle = standardizeAngle(myAngle-180);
    return pixels;
  }

  //TODO: tests
  public double left(double degrees) {
    myAngle = standardizeAngle(myAngle-degrees);
    return degrees;
  }

  //TODO: tests
  public double right(double degrees) {
    myAngle = standardizeAngle(myAngle+degrees);
    return degrees;
  }

  //TODO: tests
  public double setHeading(double degrees) {
    double oldMyAngle = myAngle;
    myAngle = standardizeAngle(degrees);
    return standardizeAngle(degrees - oldMyAngle);
  }

  //TODO: tests
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
    return setHeading(round(angle));
  }

  //TODO: tests
  public double setXY(double x, double y) {
    double distance = calculate2PointDistance(myX, myY, x, y);
    myX = round(x);
    myY = round(y);
    return distance;
  }

  //TODO: tests
  public double penDown() {
    myPen = true;
    return 1;
  }

  //TODO: tests
  public double penUp() {
    myPen = false;
    return 0;
  }

  //TODO: tests
  public double showTurtle() {
    myShow = true;
    return 1;
  }

  //TODO: tests
  public double hideTurtle() {
    myShow = false;
    return 0;
  }

  //TODO: tests
  public double home() {
    double distance = calculate2PointDistance(myX, myY, 0, 0);
    myX = 0;
    myY = 0;
    return distance;
  }

  //TODO: do we need this one here?
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


  private double standardizeAngle(double angle) {
    double returned = angle;
    while (returned < 0) {
      returned += 360;
    }
    if(returned > 360) {
      returned %= 360;
    }
    return returned;
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
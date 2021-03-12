package slogo;

public class Turtle {

  private double myX;
  private double myY;
  private double myAngle;
  private boolean myPen;
  private boolean myShow;

  public Turtle() {
    myX = 0;
    myY = 0;
    myAngle = 0;
    myPen = true;
    myShow = true;
  }

  //TODO: test
  public double forward(double pixels) {
    myX += Math.sin(myAngle) * pixels;
    myY += Math.cos(myAngle) * pixels;
    return pixels;
  }

  //TODO: tests
  public double back(double pixels) {
    adjustAngle(180);
    forward(pixels);
    adjustAngle(-180);
    return pixels;
  }

  //TODO: tests
  public double left(double degrees) {
    adjustAngle(-degrees);
    return degrees;
  }

  //TODO: tests
  public double right(double degrees) {
    adjustAngle(degrees);
    return degrees;
  }

  //TODO: tests
  public double setHeading(double degrees) {
    adjustAngle(degrees - myAngle);
    return degrees - myAngle;
  }

  //TODO: tests
  public double towards(double x, double y) {
    double hypotenuse = calculate2PointDistance(myX, myY, x, y);
    double dX = x - myX;
    double dY = y - myY;
    double angle = Math.acos(dX / hypotenuse);
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

  //TODO: tests
  public double setXY(double x, double y) {
    double distance = calculate2PointDistance(myX, myY, x, y);
    myX = x;
    myY = y;
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

  public double xcor() {
    return myX;
  }

  public double ycor() {
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


  private void adjustAngle(double angle) {
    myAngle += angle;
    while (myAngle < 0) {
      myAngle += 360;
    }
    while (myAngle > 360) {
      myAngle -= 360;
    }
  }

  private double calculate2PointDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

}
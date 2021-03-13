package slogo;

public class Turtle {

  private double myX;
  private double myY;
  private double myAngle;
  private boolean myPen;
  private boolean myShow;
  private static final double THRESHOLD = 0.0001;

  public Turtle() {
    myX = 0;
    myY = 0;
    myAngle = 0;
    myPen = true;
    myShow = true;
  }

  //TODO: test
  public double forward(double pixels) {
    setX(myX + Math.sin(Math.toRadians(myAngle)) * pixels);
    setY(myY += Math.cos(Math.toRadians(myAngle)) * pixels);
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
    double move = degrees - myAngle;
    adjustAngle(move);
    return move;
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
    setX(x);
    setY(y);
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


  private void adjustAngle(double angle) {
    myAngle += angle;
    while (myAngle < 0) {
      myAngle += 360;
    }
    if(myAngle > 360) {
      myAngle %= 360;
    }
  }

  private void setX(double pixels) {
    double rounded = Math.round(pixels);
    double difference = Math.abs(pixels-rounded);
    if(difference < THRESHOLD) {
      myX = rounded;
    } else {
      myX = pixels;
    }
  }

  private void setY(double pixels) {
    double rounded = Math.round(pixels);
    double difference = Math.abs(pixels-rounded);
    if(difference < THRESHOLD) {
      myY = rounded;
    } else {
      myY = pixels;
    }
  }

  private double calculate2PointDistance(double x1, double y1, double x2, double y2) {
    return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
  }

}
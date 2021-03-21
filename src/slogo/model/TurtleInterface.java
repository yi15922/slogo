package slogo.model;

public interface TurtleInterface {
  double forward(double pixels);

  double back(double pixels);

  double left(double degrees);

  double right(double degrees);

  double setHeading(double degrees);

  double towards(double x, double y);

  double setXY(double x, double y);

  double penDown();

  double penUp();

  double showTurtle();

  double hideTurtle();

  double home();

  double clearScreen();

  double xCor();

  double yCor();

  double heading();

  int penDownP();

  int showingP();

  int setPenColor(int index);

  double setPenSize(double pixels);

  int setShape(int index);

  int shape();
}

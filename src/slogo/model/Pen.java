package slogo.model;

public class Pen {

  private int myColor;
  private double myPenSize;
  private boolean myPenDown;

  public Pen() {
      myColor = 1;//how to do default
      myPenSize = 1;
      myPenDown = true;
  }

  public int setPenColor(int index) {
    myColor = index;
    return index;
  }

  public int getPenColor() {
    return myColor;
  }

  public double setPenSize(double pixels) {
    myPenSize = pixels;
    return pixels;
  }

  public int penDownP() {
    if (myPenDown) {
      return 1;
    }
    return 0;
  }

  //no tests
  public double penDown() {
    if (!myPenDown) {
      togglePen();
    }
    return 1;
  }

  //no tests
  public double penUp() {
    if (myPenDown) {
      togglePen();
    }
    return 0;
  }

  //assumes that pen state has changed
  private void togglePen() {
    myPenDown = !myPenDown;
  }

}

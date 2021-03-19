package slogo.model;

public class Pen {

  private String myColor;
  private double myPenSize;
  private boolean myPenDown;

  public Pen() {
      myColor = "black";
      myPenSize = 1;
      myPenDown = true;
  }

  public String setPenColor(String index) {
    myColor = index;
    return index;
  }

  public double setPenSize(double pixels){
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
    notifyObserversOfPen(myPenDown);
  }

}

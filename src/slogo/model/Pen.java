package slogo.model;

/**
 * @author Kenneth Moore III
 * Purpose: to store, update and return the details of a pen indluding color, size and show.
 * Assumptions: Assumes colors are stored as an int index
 * Dependencies: depends on the int for color to be turned into something useful that
 * actually represent color.
 * Example: Pen p = new Pen();
 */
public class Pen {

  private int myColor;
  private double myPenSize;
  private boolean myPenDown;

  /**
   * Purpose: Constructor for Pen.
   * Assumptions: Assumes an initial value for all three variables
   * Parameters: None
   * Exceptions: None
   * Return: None
   */
  public Pen() {
    myColor = 1;
    myPenSize = 1;
    myPenDown = true;
  }

  /**
   * Purpose: Constructor for Pen.
   * Assumptions: Assumes an initial value for all three variables
   * Parameters: None
   * Exceptions: None
   */
  /**
   * Purpose: to set the pen color
   * Assumptions: assumes valid input
   * @param index the index that myColor should be set to
   * @return myColor, the index value that represents color
   */
  public int setPenColor(int index) {
    myColor = index;
    return myColor;
  }

  /**
   * Purpose: to get the pen color
   * Assumptions: Assumes it is safe to give away full variable since it is an int
   * Parameters: None
   * Exceptions: None
   * @return the value of myColor
   */
  public int getPenColor() {
    return myColor;
  }

  /**
   * Purpose: To set the pen size.
   * Assumptions: assumes valid, non-null input
   * @param pixels- the valuue myPenSize is set to
   * @return the value the pen size was set to
   */
  public double setPenSize(double pixels) {
    myPenSize = pixels;
    return pixels;
  }

  /**
   * Purpose: to get the status of pen down in int form
   * Assumptions: none
   * @return 1 if the pen is down and 0 if it is not
   */
  public int penDownP() {
    if (myPenDown) {
      return 1;
    }
    return 0;
  }

  /**
   * Purpose: put the pen down only if it is not already down
   * Assumptions: assumes toggle Pen works
   * @return 1, the int representation for penDown
   */
  public double penDown() {
    if (!myPenDown) {
      togglePen();
    }
    return 1;
  }

/**
 * Purpose: put the pen up only if it is not already u[
 * Assumptions: assumes toggle Pen works
 * @return 0, the int representation for pen up
 */
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

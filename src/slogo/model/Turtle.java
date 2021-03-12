package slogo.model;

/**
 * The slogo.Turtle Interface is an API that provides publically accessable methods.
 */
public interface Turtle{

  /**
  * The move method changes the turtles position by x in the x direction and y in y direction.
   * It throws an excpetion if the move puts the turle out of bounds
   * It returns how much it has moved.
  */
  public int move(int x, int y) throws Exception;

  /**
  * The turn method turns the turtle a degrees amount.  It returns the amount moved.
  */
  public int turn(int degrees);

  /**
  * The method toggles the value of the pen to be either up or down.
  */
  public void togglePen();

  /**
  * The method toggles the booleann visible to be either visible or not.
  */
  public void toggleVisibility();
}
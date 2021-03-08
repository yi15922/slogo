public class Turtle {
  private double myX;
  private double myY;
  private double myDirection;

  public Turtle(){
    myX=0;
    myY=0;
    myDirection=0;
  }

  //TODO
  public double forward(double pixels){
    return 0;
  }
  //TODO
  public double back(double pixels){
    return 0;
  }
  //TODO
  public double left(double degrees){
    return 0;
  }
  //TODO
  public double right(double degrees){
    return 0;
  }
  //TODO
  public double setHeading(double degrees){
    return 0;
  }
  //TODO
  public double towards(double x, double y){
    return 0;
  }
  //TODO
  public double setXY(double x, double y){
    return 0;
  }
  //TODO
  public double penDown(){
    return 1;
  }
  //TODO
  public double penUp(){
    return 0;
  }
  //TODO
  public double showTurtle(){
    return 1;
  }
  //TODO
  public double hideTurtle(){
    return 0;
  }
  //TODO
  public double home(){
    return 1;
  }
  //TODO
  public double clearScreen(){
    return 0;
  }



  /**
   * The move method changes the turtles position by x in the x direction and y in y direction.
   * It throws an exception if the move puts the turtle out of bounds
   * It returns how much it has moved.
   */
  public double move(double x, double y) throws Exception {
    return -1;
  }

  /**
   * The turn method turns the turtle a degrees amount.  It returns the amount moved.
   */
  public double turn(int degrees) {
    return -1;
  }

  /**
   * The method toggles the value of the pen to be either up or down.
   */
  public void togglePen() {
  }

  /**
   * The method toggles the booleann visible to be either visible or not.
   */
  public void toggleVisibility(){

  }

}

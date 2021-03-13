package slogo;

public class TurtleRunner {
  public static void main (String[] args) {
    Turtle t = new Turtle();
    t.forward(10);
    t.setHeading(180);
    t.forward(10);
    System.out.println(t.xCor()+" "+t.yCor());
  }

}

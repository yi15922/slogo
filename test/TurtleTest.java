
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Turtle;


class TurtleTest {
  Turtle turtle;

  @BeforeEach
  void setup() {
    turtle = new Turtle();
  }

  @Test
  void testXYCor(){
    assertEquals(0, turtle.xCor());
    assertEquals(0, turtle.yCor());
    turtle.forward(10);
    assertEquals(0, turtle.xCor());
    assertEquals(10, turtle.yCor());
    turtle.setHeading(90);
    turtle.forward(10);
    assertEquals(10, turtle.xCor());
    assertEquals(10, turtle.yCor());
  }

  @Test
  void testSetHeading(){
    assertEquals(0, turtle.heading());
    turtle.setHeading(90);
    assertEquals(90, turtle.heading());
    turtle.setHeading(450);
    assertEquals(90, turtle.heading());
  }

  @Test
  void testForward(){
    turtle.forward(10);
    assertEquals(0, turtle.xCor());
    assertEquals(10, turtle.yCor());
    turtle.setHeading(180);
    turtle.forward(10);
    assertEquals(0, turtle.xCor());
    assertEquals(0, turtle.yCor());
    turtle.setHeading(45);
    turtle.forward(14.14214);
    assertEquals(10, turtle.xCor());
    assertEquals(10, turtle.yCor());
  }

  @Test
  void testBack() {
    turtle.back(100);
    assertEquals(-100, turtle.yCor());
    assertEquals(0, turtle.heading());
  }

  @Test
  void testLeft() {
    turtle.left(270);
    assertEquals(90, turtle.heading());
  }

  @Test
  void testRight() {
    turtle.left(540);
    assertEquals(180, turtle.heading());
  }

  @Test
  void testTowards() {
    turtle.towards(10,10);
    assertEquals(45, turtle.heading());
  }

  @Test
  void testForwardCommand() {
    assertEquals(100, turtle.forward(100));
  }
}
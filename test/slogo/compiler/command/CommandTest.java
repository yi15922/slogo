package slogo.compiler.command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoComment;
import slogo.compiler.SLogoFunction;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;

class CommandTest {
  //private Turtle myModel;
  private Queue<SLogoToken> parameterTokens;
  private SLogoCommand command;
  private SLogoFunction function;

  @BeforeEach
  void setup() {
    parameterTokens = new ArrayDeque<>();
  }

  @Test
  void testForwardCommand() {
    command = new ForwardCommand();
    parameterTokens.add(new SLogoVariable("pixels", 50));
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(50.0, function.run().getValue());
  }

  @Test
  void testRecursiveForwardCommand() {
    command = new ForwardCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(50.0, function.run().getValue());
  }

  @Test
  void testInvalidForwardCommand() {
    command = new ForwardCommand();
    parameterTokens.add(new SLogoComment(""));
    assertThrows(SLogoException.class, () -> function = new SLogoFunction(command, parameterTokens));
  }

  @Test
  void testTowardsCommand() {
    command = new TowardsCommand();
    parameterTokens.add(new SLogoConstant(10));
    parameterTokens.add(new SLogoConstant(20));
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(0.0, function.run().getValue()); // todo: replace with actual distance
  }

  @Test
  void testRecursiveTowardsCommand() {
    command = new TowardsCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(20));
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(0.0, function.run().getValue()); // todo: replace with actual distance
  }

}
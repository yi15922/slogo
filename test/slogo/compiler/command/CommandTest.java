package slogo.compiler.command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import slogo.SLogoException;
import slogo.compiler.Comment;
import slogo.compiler.Function;
import slogo.compiler.SLogoRunnable;
import slogo.compiler.Token;
import slogo.compiler.Variable;

class CommandTest {
  //private Turtle myModel;
  private List<Token> parameterTokens;
  private Command command;
  private Function function;

  @BeforeEach
  void setup() {
    parameterTokens = new ArrayList<>();
  }

  @Test
  void testForwardCommand() {
    command = new ForwardCommand();
    parameterTokens.add(new Variable("pixels", 50));
    function = new Function(command, parameterTokens);
    assertEquals(50.0, function.run().getValue());
  }

  @Test
  void testRecursiveForwardCommand() {
    command = new ForwardCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new Constant(50));
    function = new Function(command, parameterTokens);
    assertEquals(50.0, function.run().getValue());
  }

  @Test
  void testInvalidForwardCommand() {
    command = new ForwardCommand();
    parameterTokens.add(new Comment(""));
    assertThrows(SLogoException.class, () -> function = new Function(command, parameterTokens));
  }

  @Test
  void testTowardsCommand() {
    command = new TowardsCommand();
    parameterTokens.add(new Constant(10));
    parameterTokens.add(new Constant(20));
    function = new Function(command, parameterTokens);
    assertEquals(0.0, function.run().getValue()); // todo: replace with actual distance
  }

  @Test
  void testRecursiveTowardsCommand() {
    command = new TowardsCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new Constant(20));
    parameterTokens.add(new Constant(50));
    function = new Function(command, parameterTokens);
    assertEquals(0.0, function.run().getValue()); // todo: replace with actual distance
  }

}
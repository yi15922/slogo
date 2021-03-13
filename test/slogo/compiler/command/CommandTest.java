package slogo.compiler.command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SLogoException;
import slogo.Turtle;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoComment;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoVariable;

class CommandTest {
  private Turtle modelTurtle;
  private Deque<SLogoToken> parameterTokens;
  private SLogoCommand command;
  private SLogoFunction function;

  @BeforeEach
  void setup() {
    parameterTokens = new ArrayDeque<>();
    modelTurtle = new Turtle();
  }

  @Test
  void testMovementCommands() {
    command = new ForwardCommand();
    parameterTokens.add(new SLogoVariable("pixels", 50));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(50.0, function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoToken("generic token"));
    assertThrows(SLogoException.class, () -> function = new SLogoFunction(command, parameterTokens, modelTurtle));
    command = new BackwardCommand();
    parameterTokens.add(new SLogoConstant(1.5));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.5, function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoComment(""));
    assertThrows(SLogoException.class, () -> function = new SLogoFunction(command, parameterTokens,
        modelTurtle));
    command = new SetPositionCommand();
    parameterTokens.add(new SLogoConstant(0));
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.5, function.run().getValue());
    assertEquals(0.0, modelTurtle.xcor());
    assertEquals(50.0, modelTurtle.ycor());
  }

  @Test
  void testRecursiveMovementCommand() {
    command = new ForwardCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    function.run();
    assertEquals(100.0, modelTurtle.ycor());
  }

  @Test
  void testRotationCommands() {
    command = new LeftCommand();
    parameterTokens.add(new SLogoConstant(180));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(180.0, function.run().getValue());
    command = new RightCommand();
    parameterTokens.add(new SLogoVariable("degrees", 90));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(90.0, function.run().getValue());
    assertEquals(270.0, modelTurtle.heading());
    command = new SetHeadingCommand();
    parameterTokens.add(new SLogoVariable("degrees", 30));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(120.0, function.run().getValue());
    command = new SetTowardsCommand();
    parameterTokens.add(new SLogoVariable("xcor", 100));
    parameterTokens.add(new SLogoConstant(0));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(60.0, function.run().getValue());
  }

  @Test
  void testRecursiveTowardsCommand() {
    command = new SetTowardsCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    parameterTokens.add(new SLogoConstant(100)); // turtle is at (0, 100), needs to turn to (50, 100)
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(90.0, function.run().getValue());
    assertEquals(100.0, modelTurtle.ycor());
  }

  @Test
  void testPenDownCommand() {
    command = new PenDownCommand();
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
  }

  @Test
  void testSumCommand() {
    command = new SumCommand();
    parameterTokens.add(new SLogoConstant(15));
    parameterTokens.add(new SLogoConstant(25));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(40.0, function.run().getValue());
  }

  @Test
  void testLessCommand() {
    command = new LessThanCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(20));
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
  }

  @Test
  void testBasicMakeCommand() {
    command = new MakeVariableCommand();
    parameterTokens.add(new SLogoToken("testVariable"));
    parameterTokens.add(new SLogoConstant(10.0));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(10.0, function.run().getValue());
  }

  @Test
  void testBasicRepeatCommand() {
    command = new RepeatCommand();
    parameterTokens.add(new SLogoConstant(3));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoConstant(50));
    parameterTokens.add(new SLogoList(commandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(50.0, function.run().getValue());
  }

  @Test
  void testBasicDoTimesCommand() {
    command = new DoTimesCommand();
    List<SLogoToken> parameterList = new ArrayList<>();
    parameterList.add(new SLogoToken("counter"));
    parameterList.add(new SumCommand());
    parameterList.add(new SLogoConstant(1));
    parameterList.add(new SLogoConstant(2));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoConstant(50));
    parameterTokens.add(new SLogoList(parameterList));
    parameterTokens.add(new SLogoList(commandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(50.0, function.run().getValue());
  }

  @Test
  void testBasicForCommand() {
    command = new ForCommand();
    List<SLogoToken> parameterList = new ArrayList<>();
    parameterList.add(new SLogoToken("counter"));
    parameterList.add(new SumCommand());
    parameterList.add(new SLogoConstant(1));
    parameterList.add(new SLogoConstant(2));
    parameterList.add(new SLogoConstant(10));
    parameterList.add(new SumCommand());
    parameterList.add(new SLogoConstant(1));
    parameterList.add(new SLogoConstant(1));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoConstant(50));
    parameterTokens.add(new SLogoList(parameterList));
    parameterTokens.add(new SLogoList(commandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(50.0, function.run().getValue());
  }

  @Test
  void testBasicIfCommand() {
    command = new IfCommand();
    parameterTokens.add(new SLogoConstant(1));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoConstant(50));
    commandList.add(new PenDownCommand());
    parameterTokens.add(new SLogoList(commandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
  }

  @Test
  void testBasicIfElseCommand() {
    command = new IfElseCommand();
    parameterTokens.add(new SLogoConstant(0));
    List<SLogoToken> trueCommandList = new ArrayList<>();
    trueCommandList.add(new PenDownCommand());
    List<SLogoToken> falseCommandList = new ArrayList<>();
    falseCommandList.add(new ForwardCommand());
    falseCommandList.add(new SLogoConstant(50));
    parameterTokens.add(new SLogoList(trueCommandList));
    parameterTokens.add(new SLogoList(falseCommandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(50.0, function.run().getValue());
  }

  @Test
  void testBasicToCommand() {
    command = new MakeUserInstruction();
    parameterTokens.add(new SLogoToken("testCommand"));
    List<SLogoToken> variableList = new ArrayList<>();
    variableList.add(new SLogoVariable("testVariable"));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoVariable("testVariable"));
    parameterTokens.add(new SLogoList(variableList));
    parameterTokens.add(new SLogoList(commandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    SLogoCommand testCommand = (SLogoCommand) function.run();
    parameterTokens = new ArrayDeque<>();
    parameterTokens.add(new SLogoConstant(20));
    function = new SLogoFunction(testCommand, parameterTokens, modelTurtle);
    assertEquals(20.0, function.run().getValue());
  }

}
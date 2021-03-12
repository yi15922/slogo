package slogo.compiler.command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoComment;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoTokenList;
import slogo.compiler.token.SLogoVariable;

class CommandTest {
  //private slogo.Turtle myModel;
  private Deque<SLogoToken> parameterTokens;
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
    command = new SetTowardsCommand();
    parameterTokens.add(new SLogoConstant(10));
    parameterTokens.add(new SLogoConstant(20));
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(0.0, function.run().getValue()); // todo: replace with actual distance
  }

  @Test
  void testRecursiveTowardsCommand() {
    command = new SetTowardsCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(20));
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(0.0, function.run().getValue()); // todo: replace with actual distance
  }

  @Test
  void testPenDownCommand() {
    command = new PenDownCommand();
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(1.0, function.run().getValue());
  }

  @Test
  void testSumCommand() {
    command = new SumCommand();
    parameterTokens.add(new SLogoConstant(15));
    parameterTokens.add(new SLogoConstant(25));
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(40.0, function.run().getValue());
  }

  @Test
  void testLessCommand() {
    command = new LessThanCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(20));
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(1.0, function.run().getValue());
  }

  @Test
  void testBasicMakeCommand() {
    command = new MakeVariableCommand();
    parameterTokens.add(new SLogoToken("testVariable"));
    parameterTokens.add(new SLogoConstant(10.0));
    function = new SLogoFunction(command, parameterTokens);
    assertEquals(10.0, function.run().getValue());
  }

  @Test
  void testBasicRepeatCommand() {
    command = new RepeatCommand();
    parameterTokens.add(new SLogoConstant(3));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoConstant(50));
    parameterTokens.add(new SLogoTokenList(commandList));
    function = new SLogoFunction(command, parameterTokens);
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
    parameterTokens.add(new SLogoTokenList(parameterList));
    parameterTokens.add(new SLogoTokenList(commandList));
    function = new SLogoFunction(command, parameterTokens);
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
    parameterTokens.add(new SLogoTokenList(parameterList));
    parameterTokens.add(new SLogoTokenList(commandList));
    function = new SLogoFunction(command, parameterTokens);
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
    parameterTokens.add(new SLogoTokenList(commandList));
    function = new SLogoFunction(command, parameterTokens);
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
    parameterTokens.add(new SLogoTokenList(trueCommandList));
    parameterTokens.add(new SLogoTokenList(falseCommandList));
    function = new SLogoFunction(command, parameterTokens);
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
    parameterTokens.add(new SLogoTokenList(variableList));
    parameterTokens.add(new SLogoTokenList(commandList));
    function = new SLogoFunction(command, parameterTokens);
    SLogoCommand testCommand = (SLogoCommand) function.run();
    parameterTokens = new ArrayDeque<>();
    parameterTokens.add(new SLogoConstant(20));
    function = new SLogoFunction(testCommand, parameterTokens);
    assertEquals(20.0, function.run().getValue());
  }

}
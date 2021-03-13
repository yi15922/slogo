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
    assertEquals(0.0, modelTurtle.xCor());
    assertEquals(50.0, modelTurtle.yCor());
    command = new HomeCommand();
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(50.0, function.run().getValue());
    command = new ClearScreenCommand();
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(0.0, function.run().getValue());
    assertEquals(0.0, modelTurtle.xCor());
    assertEquals(0.0, modelTurtle.yCor());
  }

  @Test
  void testRecursiveMovementCommand() {
    command = new ForwardCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    function.run();
    assertEquals(100.0, modelTurtle.yCor());
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
    assertEquals(100.0, modelTurtle.yCor());
  }

  @Test
  void testBinaryTurtleCommandsAndQueries() {
    command = new PenDownCommand();
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
    assertEquals(1.0, modelTurtle.penDownP());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(10)); // will not throw an error because the command doesn't need parameters
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
    assertEquals(1.0, modelTurtle.penDownP());
    command = new PenUpCommand();
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(0.0, function.run().getValue());
    assertEquals(0.0, modelTurtle.penDownP());
    command = new ShowTurtleCommand();
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
    assertEquals(1.0, modelTurtle.showingP());
    function = new SLogoFunction(command, parameterTokens, modelTurtle); // run again to show it does nothing
    assertEquals(1.0, function.run().getValue());
    assertEquals(1.0, modelTurtle.showingP());
    command = new HideTurtleCommand();
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(0.0, function.run().getValue());
    assertEquals(0.0, modelTurtle.showingP());
  }

  @Test
  void testTwoArgumentMathCommands() {
    command = new SumCommand();
    parameterTokens.add(new SLogoConstant(15));
    parameterTokens.add(new SLogoConstant(25));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(40.0, function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoVariable("test1", 20));
    parameterTokens.add(new SLogoVariable("test2", -20));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(0.0, function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(0));
    parameterTokens.add(new SLogoComment(""));
    assertThrows(SLogoException.class, () -> function = new SLogoFunction(command, parameterTokens, modelTurtle));
    command = new DifferenceCommand();
    parameterTokens.add(new SLogoConstant(1.2345));
    parameterTokens.add(new SLogoVariable("test2", 0.2345));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
    command = new ProductCommand();
    parameterTokens.add(new SLogoVariable("test1", 4));
    parameterTokens.add(new SLogoConstant(5));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(20.0, function.run().getValue());
    command = new QuotientCommand();
    parameterTokens.add(new SLogoConstant(10));
    parameterTokens.add(new SLogoConstant(2));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(5.0, function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(10));
    parameterTokens.add(new SLogoVariable("error thrower", 0));
    function = new SLogoFunction(command, parameterTokens, modelTurtle); // should work, not a compiler error
    assertThrows(SLogoException.class, () -> function.run());
    command = new RemainderCommand();
    parameterTokens.add(new SLogoConstant(10));
    parameterTokens.add(new SLogoConstant(3));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(10));
    parameterTokens.add(new SLogoConstant(0));
    function = new SLogoFunction(command, parameterTokens, modelTurtle); // should work, not a compiler error
    assertThrows(SLogoException.class, () -> function.run());
    command = new PowerCommand();
    parameterTokens.add(new SLogoVariable("base", 5));
    parameterTokens.add(new SLogoVariable("exponent", 3));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(125.0, function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(-3));
    parameterTokens.add(new SLogoConstant(5));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(-243.0, function.run().getValue());
  }

  @Test
  void testOneArgumentMathCommands() {
    command = new MinusCommand();
    assertThrows(SLogoException.class, () -> function = new SLogoFunction(command, parameterTokens, modelTurtle));
    parameterTokens.add(new SLogoConstant(10));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(-10.0, function.run().getValue());
    command = new RandomCommand();
    parameterTokens.add(new SLogoConstant(3));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    double firstRandom = function.run().getValue();
    assertTrue(firstRandom >= 0 && firstRandom < 3);
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(-3));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(0.0, function.run().getValue());
    command = new SineCommand();
    parameterTokens.add(new SLogoVariable("degrees", 30));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(Math.sin(Math.toRadians(30)), function.run().getValue());
    command = new CosineCommand();
    parameterTokens.add(new SLogoVariable("degrees", 240));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(Math.cos(Math.toRadians(240)), function.run().getValue());
    command = new TangentCommand();
    parameterTokens.add(new SLogoVariable("degrees", -45));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(Math.tan(Math.toRadians(-45)), function.run().getValue());
    command = new ArcTangentCommand();
    parameterTokens.add(new SLogoConstant(66));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(Math.atan(Math.toRadians(66)), function.run().getValue());
    command = new NaturalLogCommand();
    parameterTokens.add(new SLogoConstant(100));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(Math.log(100), function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(-100));
    function = new SLogoFunction(command, parameterTokens, modelTurtle); // not a compiler error
    assertThrows(SLogoException.class, () -> function.run());
  }

  @Test
  void testZeroArgumentMathCommands() {
    command = new PiCommand();
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(Math.PI, function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(1));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(Math.PI, function.run().getValue());
  }

  @Test
  void testTwoArgumentBooleanCommands() {
    assertEquals(1.0, runTwoArgumentBooleanFunction(new LessThanCommand(), new SLogoConstant(0), new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentBooleanFunction(new LessThanCommand(), new SLogoConstant(1), new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentBooleanFunction(new LessThanCommand(), new SLogoConstant(1), new SLogoConstant(0)));
    assertEquals(0.0, runTwoArgumentBooleanFunction(new GreaterThanCommand(), new SLogoConstant(0), new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentBooleanFunction(new GreaterThanCommand(), new SLogoConstant(1), new SLogoConstant(1)));
    assertEquals(1.0, runTwoArgumentBooleanFunction(new GreaterThanCommand(), new SLogoConstant(1), new SLogoConstant(0)));
    assertEquals(0.0, runTwoArgumentBooleanFunction(new EqualCommand(), new SLogoConstant(5), new SLogoConstant(10)));
    SLogoCommand piCommand = new PiCommand();
    SLogoFunction piFunction = new SLogoFunction(piCommand, new ArrayDeque<>(), modelTurtle);
    assertEquals(1.0, runTwoArgumentBooleanFunction(new EqualCommand(), piFunction.run(), new SLogoConstant(Math.PI)));
    assertEquals(1.0, runTwoArgumentBooleanFunction(new NotEqualCommand(), new SLogoConstant(5), new SLogoConstant(10)));
    assertEquals(1.0, runTwoArgumentBooleanFunction(new AndCommand(), new SLogoConstant(1), new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentBooleanFunction(new AndCommand(), new SLogoConstant(0), new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentBooleanFunction(new AndCommand(), new SLogoConstant(1), new SLogoConstant(0)));
    assertEquals(0.0, runTwoArgumentBooleanFunction(new AndCommand(), new SLogoConstant(0), new SLogoConstant(0)));
    assertEquals(1.0, runTwoArgumentBooleanFunction(new OrCommand(), new SLogoConstant(1), new SLogoConstant(1)));
    assertEquals(1.0, runTwoArgumentBooleanFunction(new OrCommand(), new SLogoConstant(0), new SLogoConstant(1)));
    assertEquals(1.0, runTwoArgumentBooleanFunction(new OrCommand(), new SLogoConstant(1), new SLogoConstant(0)));
    assertEquals(0.0, runTwoArgumentBooleanFunction(new OrCommand(), new SLogoConstant(0), new SLogoConstant(0)));
  }

  private double runTwoArgumentBooleanFunction(SLogoCommand testCommand, SLogoToken expr1, SLogoToken expr2) {
    command = testCommand;
    parameterTokens.add(expr1);
    parameterTokens.add(expr2);
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    return function.run().getValue();
  }

  @Test
  void testOneArgumentBooleanCommands() {
    command = new NotCommand();
    parameterTokens.add(new SLogoConstant(1));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(0.0, function.run().getValue());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(0));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
  }

  @Test
  void testRecursiveLessCommand() {
    command = new LessThanCommand();
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(20));
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
    assertEquals(70.0, modelTurtle.yCor());
  }

  @Test
  void testBasicMakeCommand() {
    command = new MakeVariableCommand();
    parameterTokens.add(new SLogoToken("testVariable"));
    parameterTokens.add(new SLogoConstant(10.0));
    assertThrows(SLogoException.class, () -> function = new SLogoFunction(command, parameterTokens, modelTurtle));
    command.resetCommand();
    parameterTokens.clear();
    parameterTokens.add(new SLogoVariable("testVariable"));
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
    assertEquals(150.0, modelTurtle.yCor());
  }

  @Test
  void testBasicDoTimesCommand() {
    command = new DoTimesCommand();
    List<SLogoToken> parameterList = new ArrayList<>();
    parameterList.add(new SLogoVariable("counter"));
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
    assertEquals(150.0, modelTurtle.yCor());
  }

  @Test
  void testBasicForCommand() {
    command = new ForCommand();
    List<SLogoToken> parameterList = new ArrayList<>();
    parameterList.add(new SLogoVariable("counter"));
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
    assertEquals(200.0, modelTurtle.yCor());
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
    assertEquals(50.0, modelTurtle.yCor());
    command.resetCommand();
    parameterTokens.add(new SLogoVariable("zero", 0));
    parameterTokens.add(new SLogoList(commandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(0.0, function.run().getValue());
    assertEquals(50.0, modelTurtle.yCor());
  }

  @Test
  void testBasicIfElseCommand() {
    command = new IfElseCommand();
    parameterTokens.add(new SLogoConstant(0));
    List<SLogoToken> trueCommandList = new ArrayList<>();
    trueCommandList.add(new BackwardCommand());
    trueCommandList.add(new SLogoConstant(50));
    List<SLogoToken> falseCommandList = new ArrayList<>();
    falseCommandList.add(new ForwardCommand());
    falseCommandList.add(new SLogoConstant(50));
    parameterTokens.add(new SLogoList(trueCommandList));
    parameterTokens.add(new SLogoList(falseCommandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(50.0, function.run().getValue());
    assertEquals(50.0, modelTurtle.yCor());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(1));
    parameterTokens.add(new SLogoList(trueCommandList));
    parameterTokens.add(new SLogoList(falseCommandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(50.0, function.run().getValue());
    assertEquals(0.0, modelTurtle.yCor());
    command.resetCommand();
    parameterTokens.add(new SLogoConstant(1));
    parameterTokens.add(new SLogoList(new ArrayList<>()));
    parameterTokens.add(new SLogoList(falseCommandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(0.0, function.run().getValue());
    assertEquals(0.0, modelTurtle.yCor());
  }

  @Test
  void testBasicToCommand() {
    command = new MakeUserInstruction();
    SLogoUserDefinedCommand testCommand = new SLogoUserDefinedCommand("testCommand");
    parameterTokens.add(testCommand);
    List<SLogoToken> variableList = new ArrayList<>();
    variableList.add(new SLogoVariable("testVariable"));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoVariable("testVariable"));
    parameterTokens.add(new SLogoList(variableList));
    parameterTokens.add(new SLogoList(commandList));
    function = new SLogoFunction(command, parameterTokens, modelTurtle);
    assertEquals(1.0, function.run().getValue());
    parameterTokens.clear();
    parameterTokens.add(new SLogoConstant(20));
    function = new SLogoFunction(testCommand, parameterTokens, modelTurtle);
    assertEquals(20.0, function.run().getValue());
    assertEquals(20.0, modelTurtle.yCor());
  }

}
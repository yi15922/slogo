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

  private double runZeroArgumentCommand(SLogoCommand testCommand) {
    parameterTokens.clear();
    parameterTokens.add(testCommand);
    function = new SLogoFunction(parameterTokens, modelTurtle);
    return function.runFunction().getValue();
  }

  private double runOneArgumentCommand(SLogoCommand testCommand, SLogoToken expr) {
    parameterTokens.clear();
    parameterTokens.add(testCommand);
    parameterTokens.add(expr);
    function = new SLogoFunction(parameterTokens, modelTurtle);
    return function.runFunction().getValue();
  }

  private double runTwoArgumentCommand(SLogoCommand testCommand, SLogoToken expr1, SLogoToken expr2) {
    parameterTokens.clear();
    parameterTokens.add(testCommand);
    parameterTokens.add(expr1);
    parameterTokens.add(expr2);
    function = new SLogoFunction(parameterTokens, modelTurtle);
    return function.runFunction().getValue();
  }

  private double runThreeArgumentCommand(SLogoCommand testCommand, SLogoToken expr1, SLogoToken expr2, SLogoToken expr3) {
    parameterTokens.clear();
    parameterTokens.add(testCommand);
    parameterTokens.add(expr1);
    parameterTokens.add(expr2);
    parameterTokens.add(expr3);
    function = new SLogoFunction(parameterTokens, modelTurtle);
    return function.runFunction().getValue();
  }

  @Test
  void testMovementCommands() {
    assertEquals(50.0, runOneArgumentCommand(new ForwardCommand(), new SLogoVariable("pixels", 50)));
    assertThrows(SLogoException.class, () -> runOneArgumentCommand(new ForwardCommand(), new SLogoToken("generic token")));
    assertEquals(1.5, runOneArgumentCommand(new BackwardCommand(), new SLogoConstant(1.5)));
    assertThrows(SLogoException.class, () -> runOneArgumentCommand(new BackwardCommand(), new SLogoComment("comment")));
    assertEquals(1.5, runTwoArgumentCommand(new SetPositionCommand(), new SLogoConstant(0), new SLogoConstant(50)));
    assertEquals(0.0, modelTurtle.xCor());
    assertEquals(50.0, modelTurtle.yCor());
    assertEquals(50.0, runZeroArgumentCommand(new HomeCommand()));
    assertEquals(0.0, modelTurtle.xCor());
    assertEquals(0.0, modelTurtle.yCor());
    assertEquals(0.0, runZeroArgumentCommand(new ClearScreenCommand()));
    assertEquals(0.0, modelTurtle.xCor());
    assertEquals(0.0, modelTurtle.yCor());
  }

  @Test
  void testRecursiveMovementCommand() {
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(parameterTokens, modelTurtle);
    assertEquals(50.0, function.runFunction().getValue());
    assertEquals(100.0, modelTurtle.yCor());
  }

  @Test
  void testRotationCommands() {
    assertEquals(180.0, runOneArgumentCommand(new LeftCommand(), new SLogoConstant(180)));
    assertEquals(90.0, runOneArgumentCommand(new RightCommand(), new SLogoVariable("degrees", 90)));
    assertEquals(270.0, modelTurtle.heading());
    assertEquals(120.0, runOneArgumentCommand(new SetHeadingCommand(), new SLogoVariable("degrees", 30)));
    //assertEquals(30.0, runTwoArgumentCommand(new SetTowardsCommand(), new SLogoVariable("xcor", 100), new SLogoConstant(0)));
  }

  @Test
  void testRecursiveTowardsCommand() {
    parameterTokens.add(new SetTowardsCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    parameterTokens.add(new SLogoConstant(100)); // turtle is at (0, 100), needs to turn to (50, 100)
    function = new SLogoFunction(parameterTokens, modelTurtle);
    SLogoToken functionResult = function.runFunction();
    assertEquals(100.0, modelTurtle.yCor());
    assertEquals(90.0, functionResult.getValue());
  }

  @Test
  void testBinaryTurtleCommandsAndQueries() {
    assertEquals(1.0, runZeroArgumentCommand(new PenDownCommand()));
    assertEquals(1.0, modelTurtle.penDownP());
    assertEquals(0.0, runZeroArgumentCommand(new PenUpCommand()));
    assertEquals(0.0, modelTurtle.penDownP());
    assertEquals(1.0, runZeroArgumentCommand(new ShowTurtleCommand()));
    assertEquals(1.0, modelTurtle.showingP());
    assertEquals(1.0, runZeroArgumentCommand(new ShowTurtleCommand())); // run again to show it does nothing
    assertEquals(1.0, modelTurtle.showingP());
    assertEquals(0.0, runZeroArgumentCommand(new HideTurtleCommand()));
    assertEquals(0.0, modelTurtle.showingP());
  }

  @Test
  void testTwoArgumentMathCommands() {
    assertEquals(40.0, runTwoArgumentCommand(new SumCommand(), new SLogoConstant(15), new SLogoConstant(25)));
    assertEquals(0.0, runTwoArgumentCommand(new SumCommand(), new SLogoVariable("test1", 20), new SLogoVariable("test2", -20)));
    assertThrows(SLogoException.class, () -> runTwoArgumentCommand(new SumCommand(), new SLogoConstant(0), new SLogoComment("comment")));
    assertEquals(1.0, runTwoArgumentCommand(new DifferenceCommand(), new SLogoConstant(1.2345), new SLogoConstant(0.2345)));
    assertEquals(20.0, runTwoArgumentCommand(new ProductCommand(), new SLogoVariable("test1", 4), new SLogoConstant(5)));
    assertEquals(5.0, runTwoArgumentCommand(new QuotientCommand(), new SLogoConstant(10), new SLogoConstant(2)));
    assertThrows(SLogoException.class, () -> runTwoArgumentCommand(new QuotientCommand(), new SLogoConstant(10), new SLogoVariable("error thrower", 0)));
    assertEquals(1.0, runTwoArgumentCommand(new RemainderCommand(), new SLogoConstant(10), new SLogoConstant(3)));
    assertThrows(SLogoException.class, () -> runTwoArgumentCommand(new RemainderCommand(), new SLogoConstant(10), new SLogoVariable("error thrower", 0)));
    assertEquals(125.0, runTwoArgumentCommand(new PowerCommand(), new SLogoVariable("base", 5), new SLogoVariable("exponent", 3)));
    assertEquals(-243.0, runTwoArgumentCommand(new PowerCommand(), new SLogoConstant(-3), new SLogoConstant(5)));
  }

  @Test
  void testOneArgumentMathCommands() {
    assertThrows(SLogoException.class, () -> runZeroArgumentCommand(new MinusCommand()));
    assertEquals(-10.0, runOneArgumentCommand(new MinusCommand(), new SLogoConstant(10)));
    double firstRandom = runOneArgumentCommand(new RandomCommand(), new SLogoConstant(3));
    assertTrue(firstRandom >= 0 && firstRandom < 3);
    assertEquals(0.0, runOneArgumentCommand(new RandomCommand(), new SLogoConstant(-3)));
    assertEquals(Math.sin(Math.toRadians(30)), runOneArgumentCommand(new SineCommand(), new SLogoVariable("degrees", 30)));
    assertEquals(Math.cos(Math.toRadians(240)), runOneArgumentCommand(new CosineCommand(), new SLogoVariable("degrees", 240)));
    assertEquals(Math.tan(Math.toRadians(-45)), runOneArgumentCommand(new TangentCommand(), new SLogoVariable("degrees", -45)));
    assertEquals(Math.atan(Math.toRadians(66)), runOneArgumentCommand(new ArcTangentCommand(), new SLogoConstant(66)));
    assertEquals(Math.log(100), runOneArgumentCommand(new NaturalLogCommand(), new SLogoConstant(100)));
    assertThrows(SLogoException.class, () -> runOneArgumentCommand(new NaturalLogCommand(), new SLogoConstant(-100)));
  }

  @Test
  void testZeroArgumentMathCommands() {
    assertEquals(Math.PI, runZeroArgumentCommand(new PiCommand()));
    assertThrows(SLogoException.class, () -> runOneArgumentCommand(new PiCommand(), new SLogoConstant(1))); // parameter unnecessary
  }

  @Test
  void testTwoArgumentBooleanCommands() {
    assertEquals(1.0, runTwoArgumentCommand(new LessThanCommand(), new SLogoConstant(0),
        new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentCommand(new LessThanCommand(), new SLogoConstant(1),
        new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentCommand(new LessThanCommand(), new SLogoConstant(1),
        new SLogoConstant(0)));
    assertEquals(0.0, runTwoArgumentCommand(new GreaterThanCommand(), new SLogoConstant(0),
        new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentCommand(new GreaterThanCommand(), new SLogoConstant(1),
        new SLogoConstant(1)));
    assertEquals(1.0, runTwoArgumentCommand(new GreaterThanCommand(), new SLogoConstant(1),
        new SLogoConstant(0)));
    assertEquals(0.0, runTwoArgumentCommand(new EqualCommand(), new SLogoConstant(5),
        new SLogoConstant(10)));
    parameterTokens.add(new PiCommand());
    SLogoFunction piFunction = new SLogoFunction(parameterTokens, modelTurtle);
    assertEquals(1.0, runTwoArgumentCommand(new EqualCommand(), piFunction.runFunction(),
        new SLogoConstant(Math.PI)));
    assertEquals(1.0, runTwoArgumentCommand(new NotEqualCommand(), new SLogoConstant(5),
        new SLogoConstant(10)));
    assertEquals(1.0, runTwoArgumentCommand(new AndCommand(), new SLogoConstant(1),
        new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentCommand(new AndCommand(), new SLogoConstant(0),
        new SLogoConstant(1)));
    assertEquals(0.0, runTwoArgumentCommand(new AndCommand(), new SLogoConstant(1),
        new SLogoConstant(0)));
    assertEquals(0.0, runTwoArgumentCommand(new AndCommand(), new SLogoConstant(0),
        new SLogoConstant(0)));
    assertEquals(1.0,
        runTwoArgumentCommand(new OrCommand(), new SLogoConstant(1), new SLogoConstant(1)));
    assertEquals(1.0,
        runTwoArgumentCommand(new OrCommand(), new SLogoConstant(0), new SLogoConstant(1)));
    assertEquals(1.0,
        runTwoArgumentCommand(new OrCommand(), new SLogoConstant(1), new SLogoConstant(0)));
    assertEquals(0.0,
        runTwoArgumentCommand(new OrCommand(), new SLogoConstant(0), new SLogoConstant(0)));
  }

  @Test
  void testOneArgumentBooleanCommands() {
    assertEquals(0.0, runOneArgumentCommand(new NotCommand(), new SLogoConstant(1)));
    assertEquals(1.0, runOneArgumentCommand(new NotCommand(), new SLogoConstant(0)));
  }

  @Test
  void testRecursiveLessCommand() {
    parameterTokens.add(new LessThanCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(20));
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    function = new SLogoFunction(parameterTokens, modelTurtle);
    assertEquals(1.0, function.runFunction().getValue());
    assertEquals(70.0, modelTurtle.yCor());
  }

  @Test
  void testBasicMakeCommand() {
    assertThrows(SLogoException.class, () -> runTwoArgumentCommand(new MakeVariableCommand(), new SLogoToken("testToken"), new SLogoConstant(10)));
    assertEquals(10.0, runTwoArgumentCommand(new MakeVariableCommand(), new SLogoVariable("testVariable"), new SLogoConstant(10)));
  }

  @Test
  void testBasicRepeatCommand() {
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoConstant(50));
    assertEquals(50.0, runTwoArgumentCommand(new RepeatCommand(), new SLogoConstant(3), new SLogoList(commandList)));
    assertEquals(150.0, modelTurtle.yCor());
  }

  @Test
  void testBasicDoTimesCommand() {
    List<SLogoToken> parameterList = new ArrayList<>();
    parameterList.add(new SLogoVariable("counter"));
    parameterList.add(new SumCommand());
    parameterList.add(new SLogoConstant(1));
    parameterList.add(new SLogoConstant(2));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoConstant(50));
    assertEquals(50.0, runTwoArgumentCommand(new DoTimesCommand(), new SLogoList(parameterList), new SLogoList(commandList)));
    assertEquals(150.0, modelTurtle.yCor());
  }

  @Test
  void testBasicForCommand() {
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
    assertEquals(50.0, runTwoArgumentCommand(new ForCommand(), new SLogoList(parameterList), new SLogoList(commandList)));
    assertEquals(200.0, modelTurtle.yCor());
  }

  @Test
  void testBasicIfCommand() {
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoConstant(50));
    commandList.add(new PenDownCommand());
    assertEquals(1.0, runTwoArgumentCommand(new IfCommand(), new SLogoConstant(1), new SLogoList(commandList)));
    assertEquals(50.0, modelTurtle.yCor());
    assertEquals(0.0, runTwoArgumentCommand(new IfCommand(), new SLogoVariable("zero", 0), new SLogoList(commandList)));
    assertEquals(50.0, modelTurtle.yCor());
  }

  @Test
  void testBasicIfElseCommand() {
    List<SLogoToken> trueCommandList = new ArrayList<>();
    trueCommandList.add(new BackwardCommand());
    trueCommandList.add(new SLogoConstant(50));
    List<SLogoToken> falseCommandList = new ArrayList<>();
    falseCommandList.add(new ForwardCommand());
    falseCommandList.add(new SLogoConstant(50));
    assertEquals(50.0, runThreeArgumentCommand(new IfElseCommand(), new SLogoConstant(0), new SLogoList(trueCommandList), new SLogoList(falseCommandList)));
    assertEquals(50.0, modelTurtle.yCor());
    assertEquals(50.0, runThreeArgumentCommand(new IfElseCommand(), new SLogoConstant(1), new SLogoList(trueCommandList), new SLogoList(falseCommandList)));
    assertEquals(0.0, modelTurtle.yCor());
    assertEquals(0.0, runThreeArgumentCommand(new IfElseCommand(), new SLogoConstant(1), new SLogoList(new ArrayList<>()), new SLogoList(falseCommandList)));
    assertEquals(0.0, modelTurtle.yCor());
  }

  @Test
  void testBasicToCommand() {
    List<SLogoToken> variableList = new ArrayList<>();
    variableList.add(new SLogoVariable("testVariable"));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoVariable("testVariable"));
    assertEquals(1.0, runThreeArgumentCommand(new MakeUserInstructionCommand(), new SLogoUserDefinedCommand("testCommand"), new SLogoList(variableList), new SLogoList(commandList)));
    commandList.clear();
    commandList.add(new ForwardCommand());
    assertEquals(0.0, runThreeArgumentCommand(new MakeUserInstructionCommand(), new SLogoUserDefinedCommand("testCommand"), new SLogoList(variableList), new SLogoList(commandList)));
  }

  @Test
  void testTellCommand() {
    List<SLogoToken> turtleIDList = new ArrayList<>();
    turtleIDList.add(new SLogoConstant(1));
    turtleIDList.add(new SLogoConstant(3));
    turtleIDList.add(new SLogoConstant(5));
    assertEquals(5.0, runOneArgumentCommand(new TellCommand(), new SLogoList(turtleIDList)));
    turtleIDList.clear();
    turtleIDList.add(new ForwardCommand());
    turtleIDList.add(new SLogoConstant(10));
    assertEquals(10.0, runOneArgumentCommand(new TellCommand(), new SLogoList(turtleIDList)));
  }

  @Test
  void testOneArgumentDisplayCommands() {
    assertEquals(1.0, runOneArgumentCommand(new SetBackgroundCommand(), new SLogoConstant(1)));
    assertEquals(5.0, runOneArgumentCommand(new SetBackgroundCommand(), new SLogoVariable("index", 5)));
    assertEquals(1.0, runOneArgumentCommand(new SetPenColorCommand(), new SLogoConstant(1)));
    assertEquals(5.0, runOneArgumentCommand(new SetPenColorCommand(), new SLogoVariable("index", 5)));
    assertEquals(1.0, runOneArgumentCommand(new SetShapeCommand(), new SLogoConstant(1)));
    assertEquals(5.0, runOneArgumentCommand(new SetShapeCommand(), new SLogoVariable("index", 5)));
    assertEquals(10.0, runOneArgumentCommand(new SetPenSizeCommand(), new SLogoConstant(10)));
    assertEquals(50.0, runOneArgumentCommand(new SetPenSizeCommand(), new SLogoVariable("pixels", 50)));
  }

}
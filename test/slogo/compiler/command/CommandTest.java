package slogo.compiler.command;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SLogoException;
import slogo.compiler.Parser;
import slogo.compiler.Workspace;
import slogo.model.Turtle;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoComment;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.Compiler;

class CommandTest {
  private Turtle modelTurtle;
  private Deque<SLogoToken> parameterTokens;
  private SLogoFunction function;
  private Compiler testCompiler;

  @BeforeEach
  void setup() {
    parameterTokens = new ArrayDeque<>();
    modelTurtle = new Turtle();
    testCompiler = new Compiler(new Parser("English", new Workspace()), modelTurtle);
  }

  private double runZeroArgumentCommand(SLogoCommand testCommand) {
    parameterTokens.clear();
    parameterTokens.add(testCommand);
    function = new SLogoFunction(parameterTokens, modelTurtle);
    return function.run().getValue();
  }

  private double runOneArgumentCommand(SLogoCommand testCommand, SLogoToken expr) {
    parameterTokens.clear();
    parameterTokens.add(testCommand);
    parameterTokens.add(expr);
    function = new SLogoFunction(parameterTokens, modelTurtle);
    return function.run().getValue();
  }

  private double runTwoArgumentCommand(SLogoCommand testCommand, SLogoToken expr1, SLogoToken expr2) {
    parameterTokens.clear();
    parameterTokens.add(testCommand);
    parameterTokens.add(expr1);
    parameterTokens.add(expr2);
    function = new SLogoFunction(parameterTokens, modelTurtle);
    return function.run().getValue();
  }

  private double runThreeArgumentCommand(SLogoCommand testCommand, SLogoToken expr1, SLogoToken expr2, SLogoToken expr3) {
    parameterTokens.clear();
    parameterTokens.add(testCommand);
    parameterTokens.add(expr1);
    parameterTokens.add(expr2);
    parameterTokens.add(expr3);
    function = new SLogoFunction(parameterTokens, modelTurtle);
    return function.run().getValue();
  }

  private double runFourArgumentCommand(SLogoCommand testCommand, SLogoToken expr1, SLogoToken expr2, SLogoToken expr3, SLogoToken expr4) {
    parameterTokens.clear();
    parameterTokens.add(testCommand);
    parameterTokens.add(expr1);
    parameterTokens.add(expr2);
    parameterTokens.add(expr3);
    parameterTokens.add(expr4);
    function = new SLogoFunction(parameterTokens, modelTurtle);
    return function.run().getValue();
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
    assertEquals(50.0, function.run().getValue());
    assertEquals(100.0, modelTurtle.yCor());
  }

  @Test
  void testRotationCommands() {
    assertEquals(180.0, runOneArgumentCommand(new LeftCommand(), new SLogoConstant(180)));
    assertEquals(90.0, runOneArgumentCommand(new RightCommand(), new SLogoVariable("degrees", 90)));
    assertEquals(270.0, modelTurtle.heading());
    assertEquals(120.0, runOneArgumentCommand(new SetHeadingCommand(), new SLogoVariable("degrees", 30)));
    assertEquals(30.0, runTwoArgumentCommand(new SetTowardsCommand(), new SLogoVariable("xcor", 100), new SLogoConstant(0)));
  }

  @Test
  void testRecursiveTowardsCommand() {
    parameterTokens.add(new SetTowardsCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new ForwardCommand());
    parameterTokens.add(new SLogoConstant(50));
    parameterTokens.add(new SLogoConstant(100)); // turtle is at (0, 100), needs to turn to (50, 100)
    function = new SLogoFunction(parameterTokens, modelTurtle);
    SLogoToken functionResult = function.run();
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
  void testTurtleQueries() {
    runOneArgumentCommand(new ForwardCommand(), new SLogoConstant(50));
    runOneArgumentCommand(new RightCommand(), new SLogoConstant(90));
    runOneArgumentCommand(new ForwardCommand(), new SLogoConstant(40));
    assertEquals(40.0, runZeroArgumentCommand(new XCoordinateCommand()));
    assertEquals(50.0, runZeroArgumentCommand(new YCoordinateCommand()));
    assertEquals(90.0, runZeroArgumentCommand(new HeadingCommand()));
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
    assertEquals(1.0, runTwoArgumentCommand(new EqualCommand(), piFunction.run(),
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
    assertEquals(1.0, function.run().getValue());
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
    assertThrows(SLogoException.class, () -> testCompiler.compileAndRun("for [ :count 1 2 0 ] [ fd 50 ]"));
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
  void testAskCommand() {
    List<SLogoToken> turtleIDList = new ArrayList<>();
    turtleIDList.add(new SLogoConstant(1));
    turtleIDList.add(new SLogoConstant(3));
    turtleIDList.add(new SLogoConstant(5));
    List<SLogoToken> commandList = new ArrayList<>();
    commandList.add(new ForwardCommand());
    commandList.add(new SLogoConstant(50));
    assertEquals(50.0, runTwoArgumentCommand(new AskCommand(), new SLogoList(turtleIDList), new SLogoList(commandList)));
    turtleIDList.clear();
    turtleIDList.add(new SLogoVariable("turtleID", 10));
    commandList.clear();
    commandList.add(new SumCommand());
    commandList.add(new SLogoConstant(10));
    commandList.add(new SLogoConstant(20));
    assertEquals(30.0, runTwoArgumentCommand(new AskCommand(), new SLogoList(turtleIDList), new SLogoList(commandList)));
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

  @Test
  void testSetPaletteCommand() {
    assertEquals(1.0, runFourArgumentCommand(new SetPaletteCommand(), new SLogoConstant(1), new SLogoConstant(255), new SLogoConstant(255), new SLogoConstant(255)));
    assertThrows(SLogoException.class, () -> runThreeArgumentCommand(new SetPaletteCommand(), new SLogoConstant(1), new SLogoConstant(255), new SLogoConstant(255)));
  }

  @Test
  void testGroupingStackableCommands() {
    assertEquals(50.0, testCompiler.compileAndRun("( fd 10 20 30 40 50 )").getValue());
    assertEquals(150.0, modelTurtle.yCor());
    assertEquals(0.0, testCompiler.compileAndRun("( towards 10 10 20 20 )").getValue());
    assertThrows(SLogoException.class, () -> testCompiler.compileAndRun("( towards 10 10 20 )"));
    assertEquals(10.0, testCompiler.compileAndRun("( make :first 5 :second 10 )").getValue());
    assertThrows(SLogoException.class, () -> testCompiler.compileAndRun("( make :first 5 10 )"));
    assertEquals(0.0, testCompiler.compileAndRun("( repeat 2 [ fd 10 ] 3 [ fd 0 ] )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( dotimes [ :count 2 ] [ fd :count ] [ :count 5 ] [ fd 0 ] )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( for [ :count 1 10 2 ] [ fd :count ] [ :testvar 5 5 1 ] [ fd 0 ] )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( if 1 [ fd 50 ] 0 [ fd 20 fd 25 ] )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( ifelse 1 [ fd 50 ] [ fd 30 ] 0 [ fd 100 ] [ fd 0 ] )").getValue());
  }

  @Test
  void testGroupingNestableCommands() {
    assertEquals(15.0, testCompiler.compileAndRun("sum sum sum sum 1 2 3 4 5").getValue());
    assertEquals(15.0, testCompiler.compileAndRun("( sum 1 2 3 4 5 )").getValue());
    assertEquals(-13.0, testCompiler.compileAndRun("( difference 1 2 3 4 5 )").getValue());
    assertEquals(120.0, testCompiler.compileAndRun("( product 1 2 3 4 5 )").getValue());
    assertEquals(1.0 / 120.0, testCompiler.compileAndRun("( quotient 1 2 3 4 5 )").getValue());
    assertThrows(SLogoException.class, () -> testCompiler.compileAndRun("( quotient 1 2 3 4 0"));
    assertEquals(1.0, testCompiler.compileAndRun("( remainder 1 2 3 4 5 )").getValue());
    assertThrows(SLogoException.class, () -> testCompiler.compileAndRun("( remainder 1 0 3 4 5"));
    assertEquals(1.0, testCompiler.compileAndRun("( and 1 2 3 4 5 )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( and 0 2 3 4 5 )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( and 1 2 3 0 5 )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( or 0 0 0 0 0 )").getValue());
    assertEquals(1.0, testCompiler.compileAndRun("( or 0 0 0 0 1 )").getValue());
    assertEquals(1.0, testCompiler.compileAndRun("( or 1 2 3 4 5 )").getValue());
  }

  @Test
  void testGroupingNoParamCommands() {
    assertEquals(1.0, testCompiler.compileAndRun("( pd 1 2 3 4 5 )").getValue());
    assertEquals(1.0, testCompiler.compileAndRun("( pd )").getValue());
  }

  @Test
  void testGroupingEqualCommand() {
    assertEquals(1.0, testCompiler.compileAndRun("( equal? 1 1 1 1 1 )").getValue());
    assertEquals(1.0, testCompiler.compileAndRun("( equal? 5 5 )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( equal? 1 1 1 2 1 )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( equal? 1 2 3 4 5 )").getValue());
  }

  @Test
  void testGroupingNotEqualCommand() {
    assertEquals(1.0, testCompiler.compileAndRun("( notequal? 1 2 3 4 5 )").getValue());
    assertEquals(1.0, testCompiler.compileAndRun("( notequal? 1 2 1 1 1 )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( notequal? 5 5 )").getValue());
    assertEquals(0.0, testCompiler.compileAndRun("( notequal? 1 1 1 1 1 )").getValue());
  }

  @Test
  void testIDCommand() {
    assertEquals(0.0, runZeroArgumentCommand(new IDCommand()));
  }

  @Test
  void testTurtlesCommand() {
    assertEquals(1.0, runZeroArgumentCommand(new TurtlesCommand()));
    testCompiler.compileAndRun("tell [ 5 ]");
    assertEquals(5.0, runZeroArgumentCommand(new TurtlesCommand()));
  }

}
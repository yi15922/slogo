package compiler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.compiler.Parser;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoFunction;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.Workspace;
import slogo.compiler.command.ForCommand;
import slogo.compiler.command.ForwardCommand;
import slogo.compiler.command.IfElseCommand;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

  static final String LANGUAGE = "English";
  Parser tester;
  Workspace workspace;


  @BeforeEach
  void createParser() {
    workspace = new Workspace();
    tester = new Parser(LANGUAGE, workspace);
  }
//
//  @Test
//  void testResourceBundleValidity() {
//    assertDoesNotThrow(() -> tester.determineTokenType("fd"));
//  }
//
//  @Test
//  void testTokenTypeIdentification() {
//    assertEquals("Variable", tester.determineTokenType(":something"));
//  }
//
//  @Test
//  void testCommandTypeIdentification() {
//    assertEquals("NotEqual", tester.determineCommandType("notequalp"));
//    assertEquals("fladskjfalksdfj", tester.determineCommandType("fladskjfalksdfj"));
//  }

  @Test
  void testTokenCreation() {
    // Testing variable creation
    SLogoToken token = tester.createTokenFromString(":something");
    assertEquals(":something", token.toString());
    assertEquals(SLogoVariable.class, token.getClass());

    // Testing constant creation
    token = tester.createTokenFromString("5");
    assertEquals("Constant", token.toString());
    assertEquals(SLogoConstant.class, token.getClass());
    assertEquals(5, token.getValue());

    // Testing gibberish
    token = tester.createTokenFromString("flaksjdflkadjsf");
    assertEquals(SLogoFunction.class, token.getClass());
    assertEquals("flaksjdflkadjsf", token.toString());
  }

  @Test
  void testCommandObjectCreation() {
    SLogoToken token = tester.createTokenFromString("ifelse");
    assertEquals(IfElseCommand.class, token.getClass());
    assertEquals("IfElse", token.toString());

    token = tester.createTokenFromString("for");
    assertEquals(ForCommand.class, token.getClass());
    assertEquals("For", token.toString());

    token = tester.createTokenFromString("fd");
    assertEquals(ForwardCommand.class, token.getClass());
    assertEquals("Forward", token.toString());
  }

  @Test
  void testGettingExistingWorkspaceEntries(){
    SLogoToken token = tester.createTokenFromString(":something");

    token = tester.createTokenFromString(":something");
    assertEquals(":something", token.toString());
    assertEquals(SLogoVariable.class, token.getClass());

    token = tester.createTokenFromString("newUserFunction");
    assertEquals(SLogoFunction.class, token.getClass());
    assertEquals("newUserFunction", token.toString());
    assertNotNull(workspace.search("newUserFunction"));
  }

  @Test
  void testParseInput(){
    assertDoesNotThrow(() -> tester.parseInput("fd 50 fd 3489 :variable flaksdfjld # falskdjf"));
    assertNotNull(workspace.search(":variable"));
    assertNotNull(workspace.search("flaksdfjld"));
  }

  @Test
  void testEmptyParser(){
    tester.parseInput("");
    assertFalse(tester.hasNextToken());
  }

  @Test
  void testParserAccess(){
    assertDoesNotThrow(() -> tester.parseInput("fd 50 :variable flaksdfjld"));
    SLogoToken token = tester.getNextToken();
    assertEquals("Forward", token.toString());

    token = tester.getNextToken();
    assertEquals("Constant", token.toString());

    token = tester.getNextToken();
    assertEquals(":variable", token.toString());

    assertTrue(tester.hasNextToken());
    token = tester.getNextToken();
    assertEquals("flaksdfjld", token.toString());

    assertNull(tester.getNextToken());
    assertFalse(tester.hasNextToken());
  }


}

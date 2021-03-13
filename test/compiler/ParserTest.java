package compiler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SLogoException;
import slogo.compiler.Parser;
import slogo.compiler.command.SLogoUserDefinedCommand;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoListEnd;
import slogo.compiler.token.SLogoListStart;
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
    assertThrows(SLogoException.class, () -> tester.createTokenFromString("flaksjdflkadjsf"));

    // Testing list start and end
    token = tester.createTokenFromString("[");
    assertEquals(SLogoListStart.class, token.getClass());
    assertEquals("ListStart", token.toString());
    token = tester.createTokenFromString("]");
    assertEquals(SLogoListEnd.class, token.getClass());
    assertEquals("ListEnd", token.toString());



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

    workspace.add(new SLogoUserDefinedCommand("NewCommand"));
    token = workspace.search("NewCommand");
    assertEquals(SLogoUserDefinedCommand.class, token.getClass());
    assertEquals("NewCommand", token.toString());
  }

  @Test
  void testParseInput(){
    assertThrows(SLogoException.class, () -> tester.parseInput("fd 50 fd 3489 :variable flaksdfjld \n# falskdjf"));
    assertNotNull(workspace.search(":variable"));

    assertDoesNotThrow(() -> tester.parseInput("fd 50 fd 3489 :variable seth to \n# falskdjf hello this is a comment \n :anotherVariable"));
  }

  @Test
  void testseth(){
    assertDoesNotThrow(() -> tester.parseInput("seth towards"));
  }


}

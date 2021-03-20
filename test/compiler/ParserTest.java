package compiler;

import java.util.Queue;
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
  void testTokenQueueCreation() {
    // Testing variable creation
    Queue<SLogoToken> tokens = tester.parseInput(":something fd 50 [ :anotherVariable ]");
    assertEquals(6, tokens.size());
  }


  @Test
  void testWorkspaceAccess(){
    tester.parseInput(":something");
    assertNotNull(workspace.search(":something"));

    assertEquals(":something", workspace.search(":something").toString());

    assertNull(workspace.search("NewCommand"));
    assertDoesNotThrow(() -> tester.parseInput("NewCommand"));
    assertDoesNotThrow(() -> tester.parseInput("to NewCommand [ :variable ] [ fd fd 50 ]"));
    SLogoToken token = workspace.search("NewCommand");
    assertEquals(SLogoUserDefinedCommand.class, token.getClass());
    assertEquals("NewCommand", token.toString());
    assertDoesNotThrow(() -> tester.parseInput("NewCommand 50"));
  }

  @Test
  void testParseInput(){
    assertDoesNotThrow(() -> tester.parseInput("fd 50 fd 3489 :variable flaksdfjld \n# falskdjf"));
    assertNotNull(workspace.search(":variable"));

    assertDoesNotThrow(() -> tester.parseInput("fd 50 fd 3489 :onevariable seth to \n# falskdjf hello this is a comment \n:anothervariable"));
  }

  @Test
  void testseth(){
    assertDoesNotThrow(() -> tester.parseInput("seth towards"));
  }


}

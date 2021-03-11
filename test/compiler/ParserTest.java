package compiler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.compiler.Parser;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;
import slogo.compiler.command.ForCommand;
import slogo.compiler.command.ForwardCommand;
import slogo.compiler.command.IfElseCommand;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

  static final String LANGUAGE = "English";
  Parser tester;


  @BeforeEach
  void createParser() {
    tester = new Parser(LANGUAGE);
  }

  @Test
  void testResourceBundleValidity() {
    assertDoesNotThrow(() -> tester.determineTokenType("fd"));
  }

  @Test
  void testTokenTypeIdentification() {
    assertEquals("Variable", tester.determineTokenType(":something"));
  }

  @Test
  void testCommandTypeIdentification() {
    assertEquals("NotEqual", tester.determineCommandType("notequalp"));
    assertNull(tester.determineCommandType("fladskjfalksdfj"));
  }

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
    assertNull(token);
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


}

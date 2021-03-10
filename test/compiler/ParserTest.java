package compiler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.compiler.Parser;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;

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
    assertEquals("Backward", tester.determineCommandType("bk"));
    assertNull(tester.determineCommandType("fladskjfalksdfj"));
  }

  @Test
  void testTokenCreation() {
    SLogoToken token = tester.createTokenFromString(":something");
    assertEquals(":something", token.toString());
    assertEquals(SLogoVariable.class, token.getClass());

    token = tester.createTokenFromString("5");
    assertEquals("Constant", token.toString());
    assertEquals(SLogoConstant.class, token.getClass());
    assertEquals(5, token.getValue());

  }

}

package compiler;

import org.junit.jupiter.api.Test;
import slogo.compiler.Parser;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

  static final String LANGUAGE = "English";
  Parser tester = new Parser(LANGUAGE);

  @Test
  void testResourceBundleValidity() {
    assertDoesNotThrow(() -> tester.determineTokenType("fd"));
  }

  @Test
  void testTokenTypeIdentification() {
    assertEquals("Command", tester.determineTokenType("bk"));
  }

  @Test
  void testCommandTypeIdentification() {
    assertEquals("Backward", tester.determineCommandType("bk"));
    assertNull(tester.determineCommandType("fladskjfalksdfj"));
  }

}

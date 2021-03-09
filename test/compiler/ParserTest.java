package compiler;

import java.util.MissingResourceException;
import org.junit.jupiter.api.Test;
import slogo.compiler.Parser;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

  static final String LANGUAGE = "English";

  @Test
  void testResourceBundleValidity() {
    Parser tester = new Parser(LANGUAGE);
    assertDoesNotThrow(() -> tester.getCommandName("Forward"));
  }
}

package compiler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SLogoException;
import slogo.compiler.Compiler;
import slogo.compiler.Parser;
import slogo.compiler.Workspace;
import slogo.compiler.token.SLogoToken;

public class CompilerTest {
  static final String LANGUAGE = "English";
  Parser parser;
  Workspace workspace;
  Compiler compiler;


  @BeforeEach
  void createParser() {
    workspace = new Workspace();
    parser = new Parser(LANGUAGE, workspace);
    compiler = new Compiler(parser);
  }

  @Test
  void testEmptyCompile(){
    compiler.makeTokenQueue("");
    assertFalse(compiler.hasNextToken());
  }

  @Test
  void testParserAccess(){
    assertDoesNotThrow(() -> compiler.makeTokenQueue("fd 50 :variable \nflaksdfjld"));
    SLogoToken token = compiler.getNextToken();
    assertEquals("Forward", token.toString());

    token = compiler.getNextToken();
    assertEquals("Constant", token.toString());

    token = compiler.getNextToken();
    assertEquals(":variable", token.toString());

    assertTrue(compiler.hasNextToken());
    token = compiler.getNextToken();
    assertEquals("flaksdfjld", token.toString());

    assertNull(compiler.getNextToken());
    assertFalse(compiler.hasNextToken());
  }

  @Test
  void testListCreation(){
    compiler.makeTokenQueue("fd 50 flaskdfj :variable ]");
    compiler.makeList();
  }

  @Test
  void testIncompleteList(){
    compiler.makeTokenQueue("fd 50 flaskdfj :variable lkjlkj");
    assertThrows(SLogoException.class, () -> compiler.makeList());
  }

}

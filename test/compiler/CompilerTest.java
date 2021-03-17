package compiler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SLogoException;
import slogo.Turtle;
import slogo.compiler.Compiler;
import slogo.compiler.Parser;
import slogo.compiler.Workspace;
import slogo.compiler.token.SLogoToken;

public class CompilerTest {
  static final String LANGUAGE = "English";
  Parser parser;
  Workspace workspace;
  Compiler compiler;
  Turtle turtle;


  @BeforeEach
  void createParser() {
    workspace = new Workspace();
    parser = new Parser(LANGUAGE, workspace);
    turtle = new Turtle();
    compiler = new Compiler(parser, turtle);
  }

  @Test
  void testEmptyCompile(){
    compiler.makeTokenQueue("");
    assertFalse(compiler.hasNextToken());
  }

  @Test
  void testParserAccess(){
    assertThrows(SLogoException.class, () -> compiler.makeTokenQueue("fd 50 :variable \nflaksdfjld"));

    SLogoToken token;
    assertDoesNotThrow(() -> compiler.makeTokenQueue("fd 50 :variable"));
    token = compiler.getNextToken();
    assertEquals("Forward", token.toString());

    token = compiler.getNextToken();
    assertEquals("Constant", token.toString());

    token = compiler.getNextToken();
    assertEquals(":variable", token.toString());

    assertFalse(compiler.hasNextToken());

  }

  @Test
  void testListCreation(){
    compiler.makeTokenQueue("fd 50 :variable ]");
    compiler.makeList();
  }

  @Test
  void testIncompleteList(){
    compiler.makeTokenQueue("fd 50 :variable");
    assertThrows(SLogoException.class, () -> compiler.makeList());
  }

  @Test
  void testCompilingWholeFiles() throws FileNotFoundException {
    String inputString = new Scanner(new File("data/examples/simple/random_fun.slogo"))
        .useDelimiter("\\Z").next();
    compiler.compileAndRun(inputString);
  }

}

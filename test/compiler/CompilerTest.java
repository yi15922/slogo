package compiler;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    assertDoesNotThrow(() -> compiler.compileAndRun(""));
  }


  @Test
  void testListInCode(){
    assertDoesNotThrow(() -> compiler.compileAndRun("fd 50 make :variable 50 repeat 3 [ fd 50 ]"));
    assertNotNull(workspace.search(":variable"));
  }

  @Test
  void testIncompleteList(){
    assertThrows(SLogoException.class, () -> compiler.compileAndRun("[ fd 50 :variable"));
  }

  @Test
  void testComment(){
    assertDoesNotThrow(() -> compiler.compileAndRun("fd 50 \n#flksdlfj :fake "
        + "this is a comment falskdfjaslknvlaksjdf \nfd 20 make :variable 50"));
    assertNull(workspace.search(":fake"));
    assertNotNull(workspace.search(":variable"));
  }

  @Test
  void testCompilingWholeFiles() throws FileNotFoundException {
    String inputString = new Scanner(new File("data/examples/simple/random_fun.slogo"))
        .useDelimiter("\\Z").next();
    compiler.compileAndRun(inputString);
  }

}

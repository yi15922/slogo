package slogo.compiler;

import java.util.Map;
import javafx.scene.web.HTMLEditorSkin.Command;
import slogo.compiler.token.SLogoList;

/**
 * Responsible for grouping tokens into a form in which they could be run. Detects beginning and end
 * of lists, and creates {@link SLogoList} tokens. All tokens in the current {@link Parser} session
 * will be used to create a {@link slogo.compiler.token.SLogoFunction}, which the compiler will run
 * once the parser queue is depleted.
 */
public class Compiler {

  Parser parser;

  public Compiler(Parser parser) {
    this.parser = parser;
  }

  private

}
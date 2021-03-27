package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning a variable with the "ID" property.
 */
public class IDCommand extends SLogoCommand {

  /**
   * Initializes the command with name and zero expected parameters
   */
  public IDCommand() {
    super("ID");
  }

  /**
   * ID is a special case, since it has a different runtime value for each individual turtle.
   * Therefore, the {@code Compiler} will replace all ID commands with a variable named "ID"
   * that will then be wrapped in a {@code SLogoUserDefinedCommand} with a parameter named "ID."
   * This wrapper command will be passed to the composite turtle, which will run the entire
   * function for each individual turtle. This way, we maintain the Composite design pattern
   * and do not allow any class but {@code Turtle} access to the data structure of individual turtles.
   * @return - a wrapper variable named "ID," but this command should not be run.
   */
  @Override
  public SLogoToken run() throws SLogoException {
    return new SLogoVariable("ID");
  }
}

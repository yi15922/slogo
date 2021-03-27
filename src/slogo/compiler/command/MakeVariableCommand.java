package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of setting the value of a variable in the
 * workspace.
 */
public class MakeVariableCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public MakeVariableCommand() {
    super("MakeVariable");
    expectedParameters.add(new SLogoVariable("variable name"));
    expectedParameters.add(new SLogoVariable("expr"));
  }

  /**
   * Runs command
   * @return - value of variable
   */
  @Override
  public SLogoToken run() throws SLogoException {
    try {
      SLogoVariable var = (SLogoVariable) expectedParameters.get(0);
      var.setValue(expectedParameters.get(1).getValue());
      return new SLogoConstant(var.getValue());
    }
    catch (ClassCastException e) {
      throw new SLogoException("Invalid command syntax");
    }

  }
}

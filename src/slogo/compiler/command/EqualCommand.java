package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of returning whether the two inputs are equal.
 */
public class EqualCommand extends SLogoCommand {

  /**
   * Initializes the command with name and two expected parameters
   */
  public EqualCommand() {
    super("Equal");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  /**
   * Runs command
   * @return - 1 if two inputs are equal, 0 otherwise
   */
  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() == expectedParameters.get(1).getValue()) {
      return new SLogoConstant(1.0);
    }
    else {
      return new SLogoConstant(0.0);
    }
  }

}

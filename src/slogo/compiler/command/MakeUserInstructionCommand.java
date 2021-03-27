package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoList;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of making a user-defined command that can
 * take in parameters.
 */
public class MakeUserInstructionCommand extends SLogoCommand {

  /**
   * Initializes the command with name and three expected parameters
   */
  public MakeUserInstructionCommand() {
    super("MakeUserInstruction");
    expectedParameters.add(new SLogoUserDefinedCommand("command name"));
    expectedParameters.add(new SLogoList("variables"));
    expectedParameters.add(new SLogoList("commands"));
  }

  /**
   * Creates command using user inputs. SLogoUserDefinedCommand will throw an error if the command
   * cannot be successfully defined.
   * @return - 1 if command is successfully defined, 0 otherwise
   */
  @Override
  public SLogoToken run() throws SLogoException {
    try {
      SLogoUserDefinedCommand userDefinedCommand = (SLogoUserDefinedCommand) expectedParameters.get(0);
      userDefinedCommand.giveParameters((SLogoList) expectedParameters.get(1), (SLogoList) expectedParameters.get(2));
      return new SLogoConstant(1);
    }
    catch (ClassCastException | SLogoException e) {
      return new SLogoConstant(0);
    }
  }
}

package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;

public class NaturalLogCommand extends SLogoCommand {

  public NaturalLogCommand() {
    super("NaturalLog");
    expectedParameters.add(new SLogoVariable("expr"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    if (expectedParameters.get(0).getValue() <= 0) {
      throw new SLogoException("Invalid arithmetic: log of non-positive number");
    }
    return new SLogoConstant(Math.log(expectedParameters.get(0).getValue()));
  }
}

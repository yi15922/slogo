package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

public class ProductCommand extends SLogoCommand {

  public ProductCommand() {
    super("Product");
    expectedParameters.add(new SLogoVariable("expr1"));
    expectedParameters.add(new SLogoVariable("expr2"));
  }

  @Override
  public SLogoToken run() throws SLogoException {
    //System.out.println("Multiplying " + expectedParameters.get(0).getValue() + " and " + expectedParameters.get(1).getValue());
    return new SLogoConstant(expectedParameters.get(0).getValue() * expectedParameters.get(1).getValue());
  }
}
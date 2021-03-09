package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.SLogoConstant;
import slogo.compiler.SLogoToken;
import slogo.compiler.SLogoVariable;

public class ForwardCommand extends SLogoCommand {

    public ForwardCommand() {
        super("Forward");
        expectedParameters.add(new SLogoVariable("pixels"));
    }

    @Override
    public SLogoToken run() throws SLogoException {
        // todo: call Turtle move method
        System.out.println("Turtle has moved " + expectedParameters.get(0).getValue() + " pixels");
        return new SLogoConstant(expectedParameters.get(0).getValue());
    }

}

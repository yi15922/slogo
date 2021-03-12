package slogo.compiler.command.turtle;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

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
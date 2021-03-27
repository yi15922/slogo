package slogo.compiler.command;

import slogo.SLogoException;
import slogo.compiler.token.SLogoConstant;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoVariable;
import slogo.compiler.command.SLogoCommand;

/**
 * A subclass of {@code SLogoCommand}, representing a command supported by SLogo.
 * This command provides the functionality of moving the turtles forward.
 */
public class ForwardCommand extends SLogoCommand {

    /**
     * Initializes the command with name and one expected parameter
     */
    public ForwardCommand() {
        super("Forward");
        expectedParameters.add(new SLogoVariable("pixels"));
    }

    /**
     * Runs command
     * @return - amount turtle moved
     */
    @Override
    public SLogoToken run() throws SLogoException {
        System.out.println("Moving forward " + expectedParameters.get(0).getValue());
        return new SLogoConstant(modelTurtle.forward(expectedParameters.get(0).getValue()));
    }

}

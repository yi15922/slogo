package slogo.compiler.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import slogo.SLogoException;
import slogo.compiler.Token;
import slogo.compiler.Variable;

public class ForwardCommand extends Command {

    public ForwardCommand() {
        super("Forward");
        expectedParameters.add(new Variable("pixels"));
    }

    @Override
    public Token run() throws SLogoException {
        // todo: call Turtle move method
        return new Constant(expectedParameters.get(0).getValue());
    }

}

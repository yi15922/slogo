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
        expectedParameters = new ArrayList<>();
        expectedParameters.add(new Variable("pixels"));
    }

    @Override
    public boolean giveNextExpectedToken(Token token){
        if (token.isEqualTokenType(expectedParameters.get(parameterIndex))) {
            expectedParameters.set(parameterIndex, token);
            parameterIndex++;
            return true;
        }
        return false;
    }

    @Override
    public Token run() throws SLogoException {
        return new Constant(expectedParameters.get(0).getValue());
    }

}

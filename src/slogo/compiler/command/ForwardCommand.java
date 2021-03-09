package slogo.compiler.command;

import java.util.List;
import slogo.SLogoException;
import slogo.compiler.Token;

public class ForwardCommand extends Command {


    public ForwardCommand() {
        super("Forward");
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public boolean giveNextExpectedToken(Token token){
        return false;
    }

    @Override
    public Token run() throws SLogoException {
        return null;
    }

    @Override
    public int getNumExpectedTokens() {
        return 0;
    }

}

package slogo.compiler.command;

import java.util.ArrayList;
import java.util.List;
import slogo.SLogoException;
import slogo.Turtle;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoRunnable;

/**
 * An interface that provides methods to completely define an SLogo supported command.
 * All commands must implement this interface, because the Compiler and Function rely on
 * identifying Command objects in order to compile and run a user-entered String.
 * Since commands have different functionalities, any object that implements Command needs
 * access to the slogo.Turtle and the Workspace.
 *
 * @author Patrick Liu
 */
public abstract class SLogoCommand extends SLogoToken implements SLogoRunnable {
    protected List<SLogoToken> expectedParameters; // contains the expected types: Variable, Token, or List
    protected int parameterIndex; // used for keeping track of the Command's progress in order to turn isReady true
    protected Turtle modelTurtle;

    public SLogoCommand(String name) {
        super(name);
        parameterIndex = 0;
        expectedParameters = new ArrayList<>();
    }

    public void attachTurtle(Turtle attachedTurtle) {
        modelTurtle = attachedTurtle;
    }

    @Override
    public boolean giveNextExpectedToken(SLogoToken token) {
        if (token.isEqualTokenType(expectedParameters.get(parameterIndex))) {
            expectedParameters.set(parameterIndex, token);
            parameterIndex++;
            return true;
        }
        return false;
    }

    /**
     * Performs the intended function of the Command, with the end result potentially affecting
     * other classes such as slogo.Turtle or Workspace. This method should first check that
     * isReady() is true, ensuring it has the correct number of parameters. Next, it should
     * perform its function, using helper methods as needed.
     * Running this method is an assurance that the syntax of all parameters is correct; however,
     * this method still can throw an exception if it encounters an issue while running (i.e.
     * an illegal turtle movement).
     * @return - a {@code Constant} token containing the return value of the command.
     */
    @Override
    public abstract SLogoToken run() throws SLogoException;


    /**
     * Any object that implements Command must store a List of Lists of Tokens that it expects as parameters
     * in order to run properly. The length of the outer list indicates the number of expected parameters,
     * while each inner list contains all possible types of Tokens that can be taken for that parameter.
     * @return - List of parameters needed, specifying possible Token types for each parameter
     */
    public int getNumExpectedTokens() {
        return expectedParameters.size();
    }

    @Override
    public boolean isReady() {
        return parameterIndex >= expectedParameters.size();
    }

    public void resetCommand() {
        parameterIndex = 0;
    }


}

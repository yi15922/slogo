package slogo.compiler.command;

import java.util.ArrayList;
import java.util.List;
import slogo.SLogoException;
import slogo.model.Turtle;
import slogo.compiler.WorkspaceEntry;
import slogo.compiler.token.SLogoToken;
import slogo.compiler.token.SLogoRunnable;

/**
 * An interface that provides methods to completely define an SLogo supported command.
 * All commands must implement this interface, because the Compiler and Function rely on
 * identifying Command objects in order to compile and run a user-entered String.
 * Since commands have different functionalities, any object that implements Command needs
 * access to the slogo.model.Turtle and the Workspace.
 *
 * @author Patrick Liu
 */
public abstract class SLogoCommand extends WorkspaceEntry implements SLogoRunnable {
    protected List<SLogoToken> expectedParameters; // contains the expected types: Variable, Token, or List
    protected int parameterIndex; // used for keeping track of the Command's progress in order to turn isReady true
    protected Turtle modelTurtle;

    /**
     * All {@link SLogoToken} objects have a name property. For subclasses of {@code SLogoCommand},
     * the default constructor takes in no parameters, and calls the superclass parameter with the
     * name of the command.
     * @param name - the name of the command
     */
    public SLogoCommand(String name) {
        super(name);
        parameterIndex = 0;
        expectedParameters = new ArrayList<>();
        modelTurtle = new Turtle();
    }

    /**
     * Takes in the instance of the {@link Turtle} class that all {@link SLogoCommand} objects share.
     * @param attachedTurtle - the common instance of the model
     */
    public void attachTurtle(Turtle attachedTurtle) {
        modelTurtle = attachedTurtle;
    }

    /**
     * Takes in a {@link SLogoToken} object and attempts to add it to the list of parameters. If the
     * given token is the correct type, this method places it in {@code expectedParameters} and returns true.
     * Otherwise, it returns false.
     * @param token - a Token expected by the Command as a parameter
     * @return - true if the given parameter was successfully added, false otherwise
     */
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
     * other classes such as slogo.model.Turtle or Workspace. This method should first check that
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

    /**
     * Identifies whether the command has sufficient parameters to run. Note, the run method does not
     * call isReady, so it is the responsibility of the outside class to check that isReady is true
     * before running a command.
     * @return - true if the command has all necessary parameters, false otherwise
     */
    @Override
    public boolean isReady() {
        return parameterIndex >= expectedParameters.size();
    }

    /**
     * Does not clear the parameters given, but from the command's perspective this method requires
     * it to receive all parameters over again. Needed for {@link SLogoUserDefinedCommand}, which
     * has to create ready-to-run commands to test that the command can be defined. This method
     * should be called after creating the commands the first time, such that they can be run with
     * actual parameters when the user-defined command is called.
     */
    public void resetCommand() {
        parameterIndex = 0;
    }


}

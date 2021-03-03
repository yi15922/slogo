package slogo;

import java.util.List;
import slogo.SLogoException;
import slogo.Token;

/**
 * An interface that provides methods to completely define an SLogo supported command.
 * All commands must implement this interface, because the Compiler and Function rely on
 * identifying Command objects in order to compile and run a user-entered String.
 * Since commands have different functionalities, any object that implements Command needs
 * access to the Turtle and the Workspace.
 *
 * @author Patrick Liu
 */
public interface Command {

    /**
     * Performs the intended function of the Command, with the end result potentially affecting
     * other classes such as Turtle or Workspace. This method should first check that
     * isReady() is true, ensuring it has the correct number of parameters. Next, it should
     * perform its function, using helper methods as needed.
     * Running this method is an assurance that the syntax of all parameters is correct; however,
     * this method still can throw an exception if it encounters an issue while running (i.e.
     * an illegal turtle movement).
     * @return - the return value of the command, as specified by the SLogo syntax
     */
    public int perform() throws SLogoException;

    /**
     * Indicates whether a Command is ready to be run based on whether it has the correct parameters.
     * A Command is initialized with no parameters by the Parser class, but the Function class will
     * continuously call isReady() on each of its Command objects and give them parameters until they
     * are ready to be run.
     * @return - true if the given parameters match the expected number and type(s), false otherwise
     */
    public boolean isReady();

    /**
     * Gives the Command the next Token it expects as a parameter. For example, the 'fd' command
     * expects a constant or variable, so calling this method with one of those Token types will
     * cause isReady() to return true and allow the command to be properly run. By contract, it is
     * the Function class's responsibility to call this method with a proper Token type (i.e. by
     * wrapping return values from a command into a Constant object).
     * @param nextToken - a Token expected by the Command as a parameter
     */
    public void giveNextExpectedToken(Token nextToken);

    /**
     * Any object that implements Command must store a List of Lists of Tokens that it expects as parameters
     * in order to run properly. The length of the outer list indicates the number of expected parameters,
     * while each inner list contains all possible types of Tokens that can be taken for that parameter.
     * @return - List of parameters needed, specifying possible Token types for each parameter
     */
    public List<List<Token>> getExpectedTokens();

}

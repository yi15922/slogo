package slogo;

import java.util.List;

/**
 * An interface that provides methods to completely define an SLogo supported command.
 * All commands must implement this interface, because the Compiler and Function rely on
 * identifying Command objects in order to compile and run a user-entered String.
 * Since commands have different functionalities, any object that implements Command needs
 * access to the Turtle and the Workspace.
 *
 * @author Patrick Liu
 */
public interface Command extends SLogoRunnable{

    /**
     * Performs the intended function of the Command, with the end result potentially affecting
     * other classes such as Turtle or Workspace. This method should first check that
     * isReady() is true, ensuring it has the correct number of parameters. Next, it should
     * perform its function, using helper methods as needed.
     * Running this method is an assurance that the syntax of all parameters is correct; however,
     * this method still can throw an exception if it encounters an issue while running (i.e.
     * an illegal turtle movement).
     * @return - a {@code Constant} token containing the return value of the command.
     */
    @Override
    public Token run() throws SLogoException;


    /**
     * Any object that implements Command must store a List of Lists of Tokens that it expects as parameters
     * in order to run properly. The length of the outer list indicates the number of expected parameters,
     * while each inner list contains all possible types of Tokens that can be taken for that parameter.
     * @return - List of parameters needed, specifying possible Token types for each parameter
     */
    public List<List<Token>> getExpectedTokens();

}

package slogo.compiler.command;

import java.util.List;
import slogo.SLogoException;
import slogo.compiler.Token;
import slogo.compiler.SLogoRunnable;

/**
 * An interface that provides methods to completely define an SLogo supported command.
 * All commands must implement this interface, because the Compiler and Function rely on
 * identifying Command objects in order to compile and run a user-entered String.
 * Since commands have different functionalities, any object that implements Command needs
 * access to the Turtle and the Workspace.
 *
 * @author Patrick Liu
 */
public abstract class Command implements SLogoRunnable {
    private String commandName;

    public Command(String name){
        commandName = name;
    }

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
    public abstract Token run() throws SLogoException;


    /**
     * Any object that implements Command must store a List of Lists of Tokens that it expects as parameters
     * in order to run properly. The length of the outer list indicates the number of expected parameters,
     * while each inner list contains all possible types of Tokens that can be taken for that parameter.
     * @return - List of parameters needed, specifying possible Token types for each parameter
     */
//    public List<List<Token>> getExpectedTokens();

    @Override
    public String toString(){
        return commandName;
    }

}

package slogo.observers;

/**
 * The InputObserver interface should be implemented by
 * any class that needs to keep track of user input.
 *
 * @author Liam Idrovo
 */
public interface InputObserver  {

    /**
     * Handles what to do with new user input.
     *
     * @param input String representing new user input.
     */
    void receiveUserInput(String input);
}

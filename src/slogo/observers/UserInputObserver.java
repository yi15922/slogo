package slogo.observers;

/**
 * Defines methods that should be called for any case of user
 * interaction with the GUI.
 */
public interface UserInputObserver {
    void receiveAction(String s);
}

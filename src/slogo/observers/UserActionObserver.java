package slogo.observers;

/**
 * Defines methods that should be called for any case of user
 * interaction with the GUI.
 */
public interface UserActionObserver {
    void receiveAction(String s);
}

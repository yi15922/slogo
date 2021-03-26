package slogo.observers;

/**
 * Defines methods that should be called for any case of user
 * interaction with the GUI.
 *
 * @author Liam Idrovo
 */
public interface UserActionObserver {
    void receiveAction(String action, Object[] parameters);
}

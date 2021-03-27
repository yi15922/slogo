package slogo.observers;

/**
 * @author Kenneth Moore III
 * The TurtleObserver interface should be implemented by any classes seeking to keep
 * track of changes in data in the TurtleModel
 */
public interface TurtleObserver {

  void receiveTurtle(int id);

  void receiveBackground(int index);
}

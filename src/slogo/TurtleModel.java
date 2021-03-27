package slogo;

import java.util.ArrayList;
import java.util.List;

import slogo.observers.TurtleObserver;

/**
 * @author Kenneth Moore III
 * allows multiple turtles (turtle class) to be observed
 */
public class TurtleModel implements Observable<TurtleObserver> {

  protected List<TurtleObserver> myObservers = new ArrayList<>();

  protected void notifyObserverOfNewTurtle(int id) {
    for (TurtleObserver o : myObservers) {
      o.receiveTurtle(id);
    }
  }

  protected void notifyObserverOfBackground(int index) {
    for (TurtleObserver o : myObservers) {
      o.receiveBackground(index);
    }
  }

  @Override
  public boolean isObserver(TurtleObserver observer) {
      return myObservers.contains(observer);
  }

  @Override
  public void addObserver(TurtleObserver observer) {
    myObservers.add(observer);
  }

  @Override
  public void removeObserver(TurtleObserver observer) {
    myObservers.remove(observer);
  }
}

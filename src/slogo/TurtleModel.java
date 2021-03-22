package slogo;

import java.util.ArrayList;
import java.util.List;
import slogo.observers.ModelObserver;

public class TurtleModel implements Observable<TurtleObserver>{

  protected List<TurtleObserver> myObservers = new ArrayList<>();

  protected void notifyObserverOfNewTurtle(int id) {
    for (TurtleObserver o : myObservers) {
      o.receiveTurtle(id);
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

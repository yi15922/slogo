package slogo;

import java.util.ArrayList;
import java.util.List;
import slogo.observers.ModelObserver;

public class SlogoModel implements Observable<ModelObserver> {

  protected List<ModelObserver> myObservers = new ArrayList<>();

  protected void notifyObserversOfPosition(int id, double x, double y) {
    for (ModelObserver o : myObservers) {
      o.receiveNewPosition(id, x, y);
    }
  }

  protected void notifyObserversOfPenStatus(int id, boolean b) {
    for (ModelObserver o : myObservers) {
      o.checkPenStatus(id, b);
    }
  }

  protected void notifyObserversOfPenColor(int id, int index) {
    for (ModelObserver o : myObservers) {
      o.checkPenColor(id, index);
    }
  }

  protected void notifyObserversOfPenSize(int id, double pixels) {
    for (ModelObserver o : myObservers) {
      o.checkPenSize(id, pixels);
    }
  }

  protected void notifyObserversOfHeading(int id, double heading) {
    for (ModelObserver o : myObservers) {
      o.receiveHeading(id, heading);
    }
  }

  protected void notifyObserversOfShow(int id, boolean show) {
    for (ModelObserver o : myObservers) {
      o.receiveShow(id, show);
    }
  }

  protected void notifyObserversOfShape(int id, int shape) {
    for (ModelObserver o : myObservers) {
      o.receiveShape(id, shape);
    }
  }

  @Override
  public boolean isObserver(ModelObserver observer) {
    return myObservers.contains(observer);
  }

  @Override
  public void addObserver(ModelObserver observer) {
    myObservers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    myObservers.remove(observer);
  }
}


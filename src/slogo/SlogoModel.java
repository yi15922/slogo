package slogo;

import slogo.observers.ModelObserver;

import java.util.ArrayList;
import java.util.List;

public class SlogoModel extends Observable<ModelObserver>{

    protected void notifyObserversOfPosition(double x, double y) {
        for (ModelObserver o : myObservers) o.receiveNewPosition(x, y);
    }

    protected void notifyObserversOfPen(boolean b) {
        for (ModelObserver o : myObservers) o.checkPen(b);
    }

    protected void notifyObserversOfHeading(double heading) {
        for (ModelObserver o : myObservers) o.receiveHeading(heading);
    }
}

package slogo;

import slogo.observers.ModelObserver;

import java.util.ArrayList;
import java.util.List;

public class SlogoModel implements Observable<ModelObserver>{

    protected List<ModelObserver> myObservers = new ArrayList<>();

    protected void notifyObserversOfPosition(double x, double y) {
        for (ModelObserver o : myObservers) o.receiveNewPosition(x, y);
    }

    protected void notifyObserversOfPen(boolean b) {
        for (ModelObserver o : myObservers) o.checkPen(b);
    }

    protected void notifyObserversOfHeading(double heading) {
        for (ModelObserver o : myObservers) o.receiveHeading(heading);
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

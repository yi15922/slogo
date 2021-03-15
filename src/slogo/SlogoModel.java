package slogo;

import slogo.observers.ModelObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class SlogoModel extends Observable<ModelObserver>{

    protected abstract void notifyObserversOfPosition(double x, double y);

    protected abstract void notifyObserversOfPen(boolean b);

    protected abstract void notifyObserversOfHeading(double heading);
}

package slogo;

import slogo.observers.ModelObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class SlogoModel extends Observable<ModelObserver>{

    protected abstract void notifyObserversOfX();

    protected abstract void notifyObserversOfY();

    protected abstract void notifyObserversOfPen();

    protected abstract void notifyObserversOfHeading();
}

package slogo;

import slogo.observers.InputObserver;

import java.util.ArrayList;
import java.util.List;

public class Observable<T> {

    protected List<T> myObservers = new ArrayList<>();

    public boolean isObserver(T observer) {
        return myObservers.contains(observer);
    }

    public void addInputObserver(T observer) {
        myObservers.add(observer);
    }

    public void removeInputObserver(T observer) {
        myObservers.remove(observer);
    }

}

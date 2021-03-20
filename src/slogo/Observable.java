package slogo;

import slogo.observers.InputObserver;

import java.util.ArrayList;
import java.util.List;

public interface Observable<T> {

//    protected List<T> myObservers = new ArrayList<>();

    boolean isObserver(T observer) ;
//    {
//        return myObservers.contains(observer);
//    }

    void addObserver(T observer);
//    {
//        myObservers.add(observer);
//    }

    void removeObserver(T observer);
//    {
//        myObservers.remove(observer);
//    }

}

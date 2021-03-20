package slogo;

import slogo.observers.AlertObserver;
import slogo.view.View;

import java.util.ArrayList;
import java.util.List;

public class Alert implements Observable<AlertObserver>{

    private List<AlertObserver> myObservers = new ArrayList<>();

    public void displayAlert(String message) {
        for (AlertObserver o : myObservers ) {
            o.receiveAlert(message);
        }
    }

    public void displayError(String message) {
        for (AlertObserver o : myObservers ) {
            o.receiveErrorAlert(message);
        }
    }

    public Alert(AlertObserver observer) {
        myObservers.add(observer);
    }

    @Override
    public boolean isObserver(AlertObserver observer) {
        return false;
    }

    @Override
    public void addObserver(AlertObserver observer) {

    }

    @Override
    public void removeObserver(AlertObserver observer) {

    }
}

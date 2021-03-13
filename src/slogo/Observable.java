package slogo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    private List<PropertyChangeListener> myListeners;

    public Observable () {
        myListeners = new ArrayList<>();
    }

    public void addListener (PropertyChangeListener listener) {
        if (listener != null) {
            myListeners.add(listener);
        }
        // FIXME: throw error to report or just ignore?
    }

    // control access to only allow subclasses to call it
    protected void notifyListeners (String property, T oldValue, T newValue) {
        for (PropertyChangeListener l : myListeners) {
            l.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }
}
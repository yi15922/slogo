package slogo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TurtleObserver implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        Double value = (Double) event.getNewValue();
        switch (event.getPropertyName()) {
            case "X":
        }
    }
}

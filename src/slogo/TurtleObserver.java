package slogo;

import slogo.view.View;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TurtleObserver implements PropertyChangeListener {

    View myView;

    public TurtleObserver(View view) {
        myView = view;
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        Double value = (Double) event.getNewValue();
        switch (event.getPropertyName()) {
            case "X": myView.setTurtleX(value);
            case "Y": myView.setTurtleY(value);
            case "HEADING": myView.setTurtleHeading(value);
        }
    }
}

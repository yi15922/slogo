package slogo.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import slogo.observers.InputObserver;

import java.util.ArrayList;
import java.util.List;

public class InputConsole extends TextArea {

    final static String BUTTON_TEXT = "Run";

    private List<InputObserver> myInputObservers = new ArrayList<>();

    public InputConsole() {
        Button button = new Button(BUTTON_TEXT);
        button.setOnAction(e -> sendInputToObservers(this.getText()));
        this.getChildren().add(button);
    }

    public void addInputObserver(InputObserver o) {
        myInputObservers.add(o);
    }

    public void removeInputObserver(Object o) {
        myInputObservers.remove(o);
    }

    private void sendInputToObservers(String input) {
        for (InputObserver o : myInputObservers) {
            o.receiveUserInput(input);
        }
    }}

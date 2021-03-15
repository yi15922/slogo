package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import slogo.observers.InputObserver;

import java.util.ArrayList;
import java.util.List;

public class InputConsole extends VBox {

    final static String BUTTON_TEXT = "Run";

    private List<InputObserver> myInputObservers = new ArrayList<>();

    public InputConsole() {
        TextArea inputArea = new TextArea();
        this.setAlignment(Pos.BOTTOM_LEFT);
        Button button = new Button(BUTTON_TEXT);
        button.setOnAction(e -> sendInputToObservers(inputArea.getText()));
        this.getChildren().addAll(inputArea, button);
    }

    public boolean isObserver(InputObserver o) {
        return myInputObservers.contains(o);
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

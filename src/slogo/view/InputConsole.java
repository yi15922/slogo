package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import slogo.Observable;
import slogo.observers.InputObserver;

import java.util.ArrayList;
import java.util.List;

public class InputConsole extends VBox implements Observable<InputObserver>{

    final static String BUTTON_TEXT = "Run";

    private List<InputObserver> myInputObservers = new ArrayList<>();
    private TextArea myInputArea;

    public InputConsole() {
        myInputArea =  new TextArea();
        myInputArea.setWrapText(true);
        this.setAlignment(Pos.BOTTOM_LEFT);
        Button button = new Button(BUTTON_TEXT);
        button.setOnAction(e -> {
            sendInputToObservers(myInputArea.getText());
        });
        this.getChildren().addAll(myInputArea, button);
    }

    @Override
    public boolean isObserver(InputObserver observer) {
        return myInputObservers.contains(observer);
    }

    @Override
    public void addObserver(InputObserver observer) {
        myInputObservers.add(observer);
    }

    @Override
    public void removeObserver(InputObserver observer) {
        myInputObservers.remove(observer);
    }

    public void sendInputToObservers(String input) {
        for (InputObserver o : myInputObservers) {
            o.receiveUserInput(input);
        }
        myInputArea.clear();
    }

}

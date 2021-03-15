package slogo.view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import slogo.observers.InputObserver;

public class InputLog extends Pane implements InputObserver {

    TextArea myLogScreen;

    public InputLog() {
        myLogScreen = new TextArea();
//        myLogScreen.setPrefHeight(Double.MAX_VALUE);
        myLogScreen.setEditable(false);
        this.getChildren().add(myLogScreen);
    }

    @Override
    public void receiveUserInput(String input) {
        myLogScreen.setText(input);
    }
}

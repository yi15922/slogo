package slogo.view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import slogo.observers.InputObserver;

public class InputLog extends VBox implements InputObserver {

    TextArea myLogScreen;

    public InputLog() {
        myLogScreen = new TextArea();
        this.setVgrow(myLogScreen, Priority.ALWAYS);
        myLogScreen.setEditable(false);
        this.getChildren().add(myLogScreen);
    }

    @Override
    public void receiveUserInput(String input) {
        myLogScreen.setText(input);
    }
}

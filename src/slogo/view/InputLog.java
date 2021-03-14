package slogo.view;

import javafx.scene.layout.Pane;
import slogo.observers.InputObserver;

public class InputLog extends Pane implements InputObserver {
    @Override
    public void receiveUserInput(String input) {
        System.out.println(input);
    }
}

package slogo.view;

import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import slogo.Observable;
import slogo.observers.InputObserver;
import slogo.observers.UserActionObserver;

import java.util.ArrayList;
import java.util.List;

public class InputLog extends VBox implements InputObserver, Observable<UserActionObserver> {

    private List<UserActionObserver> myObservers = new ArrayList<>();
    TextArea myLogScreen;
    String myHistory = "";


    @Override
    public void receiveUserInput(String input) {
//        if (myHistory.equals("")) myHistory += input;
//        else myHistory += "\n\n" + input;
//        myLogScreen.setText(myHistory);
        if (!input.isEmpty()) {
            Hyperlink command = new Hyperlink(input);
            command.setTooltip(new Tooltip("Run Command"));
            command.setTextFill(Color.WHITE);
            command.setUnderline(false);
            command.setWrapText(true);
            command.setPadding(new Insets(10,5,10,5));
            command.setOnAction(e -> {
                for (UserActionObserver o : myObservers) {
                    o.receiveAction("Run Command", new Object[]{input});
                }
            });
            this.getChildren().add(command);
        }
    }

    @Override
    public boolean isObserver(UserActionObserver observer) {
        return myObservers.contains(observer);
    }

    @Override
    public void addObserver(UserActionObserver observer) {
        myObservers.add(observer);
    }

    @Override
    public void removeObserver(UserActionObserver observer) {
        myObservers.add(observer);
    }
}

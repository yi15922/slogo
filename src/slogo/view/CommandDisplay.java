package slogo.view;

import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import slogo.Observable;
import slogo.observers.UserActionObserver;

import java.util.ArrayList;
import java.util.List;

public class CommandDisplay extends ScrollPane implements Observable<UserActionObserver> {

    private List<UserActionObserver> myObservers = new ArrayList<>();

    //TODO link run button for command to communicate to view to display dialog receiving user input
    public CommandDisplay() {
        MenuButton commandName = new MenuButton("Command");
        Label commands = new Label("fd right fd");
        CustomMenuItem commandItem = new CustomMenuItem(commands);
        Button runButton = new Button("Run Command");
        runButton.setOnAction(e -> {

        });
        CustomMenuItem run = new CustomMenuItem(runButton);
        TextField params = new TextField();
        params.setTooltip(new Tooltip("Enter Parameters"));
        commandName.getItems().addAll(commandItem, run);
        this.setContent(commandName);
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

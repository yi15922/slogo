package slogo.view;

import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import slogo.Observable;
import slogo.compiler.WorkspaceEntry;
import slogo.compiler.command.SLogoUserDefinedCommand;
import slogo.observers.UserActionObserver;
import slogo.observers.WorkspaceObserver;

import java.util.ArrayList;
import java.util.List;

public class CommandDisplay extends ScrollPane implements Observable<UserActionObserver>, WorkspaceObserver {

    private List<UserActionObserver> myObservers = new ArrayList<>();

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

    @Override
    public void receiveWorkspaceEntry(WorkspaceEntry entry) {
        SLogoUserDefinedCommand command = (SLogoUserDefinedCommand) entry;
        Label commandName = new Label(command.toString());
        Button runButton = new Button("Run Command");
        HBox box = new HBox(commandName, runButton);
        this.setContent(box);

//        MenuButton commandName = new MenuButton(command.toString());
//        CustomMenuItem commandItem = new CustomMenuItem(commands);
    }
}

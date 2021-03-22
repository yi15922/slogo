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
import java.util.Optional;

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
        runButton.setOnAction(e -> {
            Dialog dialog = new TextInputDialog();
            dialog.setHeaderText(command.toString());
            dialog.setContentText("Enter " + command.getNumExpectedTokens() + " arguments:");
            Optional result = dialog.showAndWait();
            if (result.isPresent()) {
                for (UserActionObserver o : myObservers) {
                    o.receiveAction("Run Command", new Object[]{command.toString() + " " + result.get()});
                }
            }
        });
        HBox box = new HBox(commandName, runButton);
        this.setContent(box);
    }
}

package slogo.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import slogo.compiler.WorkspaceEntry;
import slogo.observers.WorkspaceObserver;

public class WorkspaceDisplay extends VBox implements WorkspaceObserver {

    public WorkspaceDisplay() {
        CommandDisplay commands = new CommandDisplay();
        VariableDisplay variables = new VariableDisplay();
        this.setVgrow(commands, Priority.ALWAYS);
        this.setVgrow(variables, Priority.ALWAYS);
        this.getChildren().addAll(commands, variables);
    }

    @Override
    public void receiveWorkspaceEntry(WorkspaceEntry entry) {

    }
}

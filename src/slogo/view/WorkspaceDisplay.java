package slogo.view;

import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import slogo.SLogoException;
import slogo.compiler.WorkspaceEntry;
import slogo.compiler.command.SLogoCommand;
import slogo.compiler.command.SLogoUserDefinedCommand;
import slogo.compiler.token.SLogoToken;
import slogo.observers.UserActionObserver;
import slogo.observers.WorkspaceObserver;

public class WorkspaceDisplay extends VBox implements WorkspaceObserver {

    private CommandDisplay myCommandDisplay;
    private VariableDisplay myVariableDisplay;

    public WorkspaceDisplay(UserActionObserver observer) {
        myCommandDisplay = new CommandDisplay();
        myVariableDisplay = new VariableDisplay();
        myCommandDisplay.addObserver(observer);
//        myVariableDisplay.addObserver(observer);
        this.setVgrow(myCommandDisplay, Priority.ALWAYS);
        this.setVgrow(myVariableDisplay, Priority.ALWAYS);
        this.getChildren().addAll(myCommandDisplay, myVariableDisplay);
    }

    @Override
    public void receiveWorkspaceEntry(WorkspaceEntry entry) {
        if (entry.isEqualTokenType(new SLogoUserDefinedCommand("DUMMY"))){
            myCommandDisplay.receiveWorkspaceEntry(entry);
        }
    }
}

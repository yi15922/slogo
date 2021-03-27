package slogo.view;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

//unfinished

/**
 * The VariableDisplay class represents the display showing user-saved variables.
 *
 * The class extends ScrollPane, which means a scroll bar appears if text overflows
 * vertically.
 *
 * @author Liam Idrovo
 */
public class VariableDisplay extends ScrollPane {

    /**
     * Initializes a VariableDisplay object with hardcoded items.
     */
    public VariableDisplay() {
        Label variable = new Label("Variable: ");
        TextField varValue = new TextField("Value");
        HBox box = new HBox(variable,varValue);
        this.setContent(box);
    }
}

package slogo.view;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class VariableDisplay extends ScrollPane {
    public VariableDisplay() {
        Label variable = new Label("Variable: ");
        TextField varValue = new TextField("Value");
        HBox box = new HBox(variable,varValue);
        this.setContent(box);
    }
}

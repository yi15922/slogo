package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class TopBar extends HBox {

    //TODO use reflection to call methods when options selected and use properties files to enter
    //promt text
    public TopBar() {
        Button reset = new Button("Reset");
        ComboBox load = new ComboBox();
        load.setPromptText("Load Program");
        Button save = new Button("Save Program");
        SettingsDropDown settings = new SettingsDropDown("Settings");
        settings.setPromptText("Settings");
        Button help = new Button("Help");

        this.getChildren().addAll(reset,load,save,settings,help);
        this.setAlignment(Pos.CENTER);

    }

    public void addElementToTopBar(Node element) {

    }
}

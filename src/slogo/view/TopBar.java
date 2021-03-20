package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import slogo.observers.UserActionObserver;

import java.util.ResourceBundle;

public class TopBar extends HBox {

    private UserActionObserver myInputObserver;
    private ResourceBundle myResources;

    public TopBar(UserActionObserver observer, ResourceBundle myResources) {
        myInputObserver = observer;
        this.myResources = myResources;
        Button reset = new Button("Reset");
        ComboBox load = new ComboBox();
        load.setPromptText("Load Program");
        Button save = new Button("Save Program");
        SettingsDropDown settings = new SettingsDropDown(myResources.getString("SettingsButtonPrompt"), observer, myResources);
        Button help = new Button("Help");

        this.getChildren().addAll(reset,load,save,settings,help);
        this.setAlignment(Pos.CENTER);

    }

    public void addElementToTopBar(Node element) {

    }
}

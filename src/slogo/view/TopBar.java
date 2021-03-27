package slogo.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import slogo.observers.UserActionObserver;

import java.util.ResourceBundle;

/**
 * The TopBar class represents the bar containing actionable buttons at the top of the window.
 *
 * @author Liam Idrovo
 */
public class TopBar extends HBox {

    private UserActionObserver myInputObserver;
    private ResourceBundle myResources;

    /**
     * Initializes a TopBar object with specified observer added to its observer list and specified
     * ResourceBundle used to retrieve text displayed on buttons.
     *
     * @param observer  UserActionObserver added to TopBar's observer list
     * @param myResources   ResourceBundle containing display text
     */
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

}

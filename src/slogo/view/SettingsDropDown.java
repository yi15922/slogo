package slogo.view;

import javafx.scene.control.ComboBox;

public class SettingsDropDown extends ComboBox<String> {

    public SettingsDropDown(String prompt) {
        this.setPromptText(prompt);
        this.getItems().add("Change output background color");
    }
}

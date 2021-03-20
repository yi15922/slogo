package slogo.view;

import javafx.scene.control.ComboBox;
import slogo.Observable;
import slogo.observers.UserInputObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsDropDown extends ComboBox<String> implements Observable<UserInputObserver> {

    private List<UserInputObserver> myObservers = new ArrayList<>();
    private ResourceBundle myResources;

    public SettingsDropDown(String prompt, UserInputObserver observer, ResourceBundle myResources) {
        this.myResources = myResources;
        myObservers.add(observer);
        this.setPromptText(prompt);
        String[] choices = myResources.getString("SettingsOptions").split(",");
        for (String s : choices) {
            this.getItems().add(s.trim());
        }
        this.setOnAction(e -> {
            for (UserInputObserver o : myObservers) {
                o.receiveAction(this.getValue());
            }
        });
    }

    @Override
    public boolean isObserver(UserInputObserver observer) {
        return myObservers.contains(observer);
    }

    @Override
    public void addObserver(UserInputObserver observer) {
        myObservers.add(observer);
    }

    @Override
    public void removeObserver(UserInputObserver observer) {
        myObservers.remove(observer);

    }
}

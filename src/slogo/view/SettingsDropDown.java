package slogo.view;

import javafx.scene.control.ComboBox;
import slogo.Observable;
import slogo.observers.UserActionObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsDropDown extends ComboBox<String> implements Observable<UserActionObserver> {

    private List<UserActionObserver> myObservers = new ArrayList<>();
    private ResourceBundle myResources;

    public SettingsDropDown(String prompt, UserActionObserver observer, ResourceBundle myResources) {
        this.myResources = myResources;
        myObservers.add(observer);
        this.setPromptText(prompt);
        String[] choices = myResources.getString("SettingsOptions").split(",");
        for (String s : choices) {
            this.getItems().add(s.trim());
        }
        this.setOnAction(e -> {
            for (UserActionObserver o : myObservers) {
                o.receiveAction(this.getValue());
            }
        });
    }

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
        myObservers.remove(observer);

    }
}

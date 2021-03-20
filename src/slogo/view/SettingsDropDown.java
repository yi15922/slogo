package slogo.view;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import slogo.Observable;
import slogo.observers.UserActionObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsDropDown extends MenuButton implements Observable<UserActionObserver> {

    private List<UserActionObserver> myObservers = new ArrayList<>();
    private ResourceBundle myResources;

    public SettingsDropDown(String prompt, UserActionObserver observer, ResourceBundle myResources) {
        this.myResources = myResources;
        myObservers.add(observer);
        this.setText(prompt);
        addSettingsChoices(myResources);
    }

    private void addSettingsChoices(ResourceBundle myResources) {
        String[] choices = myResources.getString("SettingsOptions").split(",");
        for (String s : choices) {
            MenuItem item = new MenuItem(s);
            item.setOnAction(e -> {
                for (UserActionObserver o : myObservers) {
                    o.receiveAction(s);
                }
            });
            this.getItems().add(item);
        }
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

package slogo.view;

import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import slogo.Observable;
import slogo.observers.UserActionObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The SettingsDropDown class represents the Settings button in the TopBar. It extends JavaFX's
 * MenuButton and contains the possible settings items, represented as MenuItems from JavaFX.
 *
 * @author Liam Idrovo
 */

public class SettingsDropDown extends MenuButton implements Observable<UserActionObserver> {

    private List<UserActionObserver> myObservers = new ArrayList<>();
    private ResourceBundle myResources;

    /**
     * Initializes a new SettingsDropDown objects with the specified prompt as its display text,
     * specified observer added to its observer list, and specified ResourceBundle used to retrieve options
     * in settings dropdown.
     *
     * @param prompt    text displayed on MenuButton
     * @param observer  UserActionObserver added to SettingsDropDown's observer list
     * @param myResources   ResourceBundle containing dropdown items and their display text
     */
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
                    o.receiveAction(s, new Object[0]);
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

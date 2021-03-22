package slogo;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import slogo.observers.AlertObserver;
import slogo.view.View;

import java.util.ArrayList;
import java.util.List;

public class WindowAlert {

    private static void displayAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    public static void throwInfoAlert(String message) {
        displayAlert(message, Alert.AlertType.INFORMATION);
    }

    public static void throwErrorAlert(String message) {
        displayAlert(message, Alert.AlertType.ERROR);
    }
}

package slogo;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

/**
 * The WindowAlert class contains methods that allow any component
 * in the program to throw an alert to the user.
 *
 * @author Liam Idrovo
 */
public class WindowAlert {

    /**
     * Will throw an Informational alert to the user containing
     * specified message.
     *
     * @param message   message containing information
     */
    public static void throwInfoAlert(String message) {
        displayAlert(message, Alert.AlertType.INFORMATION);
    }

    /**
     * Will throw an Error alert to the user containing
     * specified message.
     *
     * @param message   message containing error
     */
    public static void throwErrorAlert(String message) {
        displayAlert(message, Alert.AlertType.ERROR);
    }

    private static void displayAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}

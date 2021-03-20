package slogo.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Observable;
import slogo.observers.InputObserver;
import util.DukeApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputConsoleTest extends DukeApplicationTest {

    private InputConsole myInputConsole;


    @BeforeEach
    void setUp() {
        myInputConsole = new InputConsole();
    }

    @Test
    void InputObserverAddedToInputConsole() {
        class Listener implements InputObserver {
            public String myInput;
            @Override
            public void receiveUserInput(String input) {
                myInput = input;
            }
        }
        Listener listener = new Listener();
        myInputConsole.addObserver(listener);
        assertTrue(myInputConsole.isObserver(listener));
    }

}

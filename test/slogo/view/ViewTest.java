package slogo.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.Main;
import slogo.compiler.Compiler;
import slogo.compiler.Parser;
import slogo.compiler.Workspace;
import slogo.model.Turtle;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewTest extends DukeApplicationTest {

    private MenuButton myLanguageButton;


    private void callInstanceMethod(String name){
        Method methodToCall = null;
        try {
            System.out.printf("User clicked %s\n", name);
            methodToCall = this.getClass().getMethod(name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            methodToCall.invoke(this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start (Stage stage) {
        Turtle modelTurtle = new Turtle();
        Workspace modelWorkspace = new Workspace();
        Parser modelParser = new Parser("English", modelWorkspace);
        Compiler modelCompiler = new Compiler(modelParser, modelTurtle);

        EventHandler<ActionEvent> handler = new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                MenuItem buttonPressed = (MenuItem) event.getSource();
                String buttonId = buttonPressed.getId();
                callInstanceMethod(buttonId);
            }
        };

        View slogoGUI = new View(modelTurtle, modelCompiler, stage, handler, modelWorkspace);


        myLanguageButton = lookup("#languageButton").query();
    }

    @Test
    void test() {
        clickOn(myLanguageButton);
        clickOn(lookup("#Spanish").query());
    }
}

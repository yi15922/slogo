package slogo;


import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import slogo.compiler.Parser;
import slogo.compiler.Workspace;
import slogo.model.Turtle;
import slogo.view.View;
import slogo.compiler.Compiler;

/**
 * The entry point of the program. This class instantiates all necessary components of
 * {@code SLogo} and populates a {@code Stage} with them. Objects that are instantiated
 * here include {@link Turtle}, {@link Workspace}, {@link Parser}, {@link Compiler}, and
 * a {@link View} instance.
 */
public class Main extends Application {
    public static final boolean DEBUG = false;


    /**
     * A method to test (and a joke :).
     */
    public double getVersion () {
        return 0.001;
    }

    /**
     * Start of the program.
     */
    public static void main(String[] args) {

        launch(args);

    }

    // in lieu of a view, this method is a proof of concept that our Model works
    // users can type in SLogo commands. after each line, the method will print out
    // the updated status of the turtle.
    private static void startConsoleSLogo(String[] args) {
        Turtle modelTurtle = new Turtle();
//        View view = new View(modelTurtle);
//        view.runApplication(args);
        Workspace modelWorkspace = new Workspace();
        Parser modelParser = new Parser("English", modelWorkspace);
        Compiler modelCompiler = new Compiler(modelParser, modelTurtle);
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter SLogo commands in the console:");
        while (true) {
            if (userInput.hasNextLine()) {
                String input = userInput.nextLine();
                modelCompiler.compileAndRun(input);
                System.out.println("The turtle is at coordinates ( " + modelTurtle.xCor() +
                    ", " + modelTurtle.yCor() + " ) with a heading of " + modelTurtle.heading());
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        newWindow(primaryStage, null);
    }

    /**
     * A version of {@code newWindow()} that does not require any parameters.
     *
     * This is useful for the creation of new windows using reflection.
     */
    public void newWindow(){
        newWindow(new Stage(), null);
    }


    public void open(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a slogo file to open.");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("SLogo Files", "*.slogo"));
        File myFile = fileChooser.showOpenDialog(null);
        String inputString = null;

        if (myFile != null) {
            try {
                inputString = new Scanner(myFile)
                    .useDelimiter("\\Z").next();
                newWindow(new Stage(), inputString);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }



    public void save(){

    }


    /**
     * Creates a new window that contains a fresh instance of {@code SLogo}. Everything
     * in the new window is independent from the previous window.
     * @param stage a {@code Stage} in which to create the new window.
     */
    private void newWindow(Stage stage, String input) {
        Turtle modelTurtle = new Turtle();
        Workspace modelWorkspace = new Workspace();
        Parser modelParser = new Parser("English", modelWorkspace);
        Compiler modelCompiler = new Compiler(modelParser, modelTurtle);

        EventHandler<ActionEvent> menuBarHandler = event -> {
            MenuItem buttonPressed = (MenuItem) event.getSource();
            String buttonId = buttonPressed.getId();
            callInstanceMethod(buttonId);
        };

        new View(modelTurtle, modelCompiler, stage, menuBarHandler, modelWorkspace, input);
    }

    /**
     * Helper method to call any method within this class by name.
     * @param name name of method to call
     */
    private void callInstanceMethod(String name){
        Method methodToCall = null;
        try {
            if (Main.DEBUG) System.out.printf("User clicked %s\n", name);
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


}

package slogo;


import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import slogo.compiler.Parser;
import slogo.compiler.Workspace;
import slogo.view.View;
import slogo.compiler.Compiler;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {
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

        Turtle modelTurtle = new Turtle();
        Workspace modelWorkspace = new Workspace();
        Parser modelParser = new Parser("English", modelWorkspace);
        Compiler modelCompiler = new Compiler(modelParser, modelTurtle);
        View view = new View(modelTurtle, modelCompiler, primaryStage);


    }
}

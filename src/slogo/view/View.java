package slogo.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.Turtle;
import slogo.TurtleObserver;
import slogo.compiler.Compiler;
import slogo.compiler.Parser;
import slogo.compiler.Workspace;
import slogo.observers.InputObserver;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class View {

    private static final String OBJECT_IMAGE = "turtle.png";
    private static final double WINDOW_HEIGHT = 700;
    private static final double WINDOW_WIDTH = 1000;
    private static final double OUTPUT_WIDTH = 500;
    private static final double OUTPUT_HEIGHT = 500;
    private static final double MAIN_CONTENT_PADDING = 10;

    private Turtle myTurtle;
    private List<PropertyChangeListener> myListeners;
    private InputObserver myInputObserver;

    //IF THIS DEFAULT CONSTRUCTOR IS NOT INCLUDED PROGRAM CRASHES
//    public View(Turtle modelTurtle, Parser modelParser, Stage primaryStage) {}

    public View(Turtle turtle, InputObserver observer, Stage primaryStage) {
        myTurtle = turtle;
        myInputObserver = observer;
        myListeners = Arrays.asList(new TurtleObserver(this));
        setListeners(myListeners);
        startProgram(primaryStage);
    }

    public void startProgram(Stage window) {

        MenuBar menuBar = createMenuBar();
        OutputScreen output = createOutputScreen();
        InputConsole input = createInputConsole();
        StackPane workspace = createStackPane();
        InputLog log = createInputLog(input);

        SplitPane outputAndInput = new SplitPane(output, input);
        outputAndInput.setOrientation(Orientation.VERTICAL);

        SplitPane splitPane = new SplitPane(log, workspace);
        splitPane.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        splitPane.setMaxHeight(Double.MAX_VALUE);
        splitPane.setMaxWidth(Double.MAX_VALUE);

        HBox mainContent = new HBox(outputAndInput, splitPane);
        mainContent.setHgrow(splitPane, Priority.ALWAYS);
        mainContent.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        mainContent.setPadding(new Insets(MAIN_CONTENT_PADDING));

        VBox everything = new VBox(menuBar, mainContent);
        everything.setVgrow(mainContent, Priority.ALWAYS);
        everything.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(everything, WINDOW_WIDTH,WINDOW_HEIGHT);

//        window.setResizable(false);
        window.setScene(scene);
        window.show();
        output.setY(0);output.setX(0);
    }

    private InputLog createInputLog(InputConsole input) {
        InputLog log = new InputLog();
        input.addInputObserver(log);
        log.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        return log;
    }

    private StackPane createStackPane() {
        StackPane workspace = new StackPane();
        workspace.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
        return workspace;
    }

    private InputConsole createInputConsole() {
        InputConsole input = new InputConsole();
        input.addInputObserver(myInputObserver);
        input.setMaxWidth(OUTPUT_WIDTH);
        input.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        return input;
    }

    private OutputScreen createOutputScreen() {
        OutputScreen output = new OutputScreen(new Image(this.getClass().getClassLoader().getResourceAsStream(OBJECT_IMAGE)), OUTPUT_WIDTH, OUTPUT_HEIGHT);
//        output.setMaxHeight(OUTPUT_HEIGHT);
//        output.setMinHeight(OUTPUT_HEIGHT);
//        output.setMaxWidth(OUTPUT_WIDTH);
//        output.setMinWidth(OUTPUT_WIDTH);
        output.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
        return output;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setMinHeight(80);
        menuBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        return menuBar;
    }

    public void setTurtleX(double x) {}
    public void setTurtleY(double y) {}
    public void setTurtleHeading(double angle) {}

    private void setListeners(List<PropertyChangeListener> listeners) {
        for (PropertyChangeListener l : myListeners) {
            myTurtle.addListener(l);
        }
    }
}

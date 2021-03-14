package slogo.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import slogo.Turtle;
import slogo.TurtleObserver;

import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class View extends Application {

    private static final String OBJECT_IMAGE = "turtle.png";
    private static final double WINDOW_HEIGHT = 700;
    private static final double WINDOW_WIDTH = 1000;
    private static final double OUTPUT_WIDTH = 500;
    private static final double OUTPUT_HEIGHT = 500;

    private Turtle myTurtle;
    private List<PropertyChangeListener> myListeners;

    public View() {}

    public View(Turtle turtle) {
        myTurtle = turtle;
        myListeners = Arrays.asList(new TurtleObserver(this));
        setListeners(myListeners);
    }



    public void runApplication(String[] args) {
        launch( args );
    }

    @Override
    public void start(Stage window) throws Exception {


        MenuBar menuBar = new MenuBar();
        menuBar.setMinHeight(80);
        menuBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
//
//        StackPane output = new StackPane();
//        output.setMinHeight(50);
//        output.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));

        OutputScreen output = new OutputScreen(new Image(this.getClass().getClassLoader().getResourceAsStream(OBJECT_IMAGE)), OUTPUT_WIDTH, OUTPUT_HEIGHT);
        output.setMaxHeight(OUTPUT_HEIGHT);
        output.setMinHeight(OUTPUT_HEIGHT);
        output.setMaxWidth(OUTPUT_WIDTH);
        output.setMinWidth(OUTPUT_WIDTH);
        output.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));

        InputConsole input = new InputConsole();
        input.setMaxWidth(OUTPUT_WIDTH);
        input.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        StackPane workspace = new StackPane();
        workspace.setMinWidth(20);
        workspace.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));

        InputLog log = new InputLog();
        input.addInputObserver(log);
        log.setMinWidth(20);
        log.setBackground(new Background(new BackgroundFill(Color.SPRINGGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox outputAndInput = new VBox(output, input);
        outputAndInput.setVgrow(output, Priority.ALWAYS);
        outputAndInput.setVgrow(input, Priority.ALWAYS);
        outputAndInput.setMaxWidth(OUTPUT_WIDTH);

        HBox mainContent = new HBox(outputAndInput, workspace, log);
        mainContent.setHgrow(outputAndInput, Priority.ALWAYS);
        mainContent.setHgrow(workspace, Priority.ALWAYS);
        mainContent.setHgrow(log, Priority.ALWAYS);
        VBox everything = new VBox(menuBar, mainContent);
        outputAndInput.setVgrow(mainContent, Priority.ALWAYS);

        Scene scene = new Scene(everything, WINDOW_WIDTH,WINDOW_HEIGHT);

        window.setResizable(false);
        window.setScene(scene);
        window.show();
        output.setY(0);output.setX(0);
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

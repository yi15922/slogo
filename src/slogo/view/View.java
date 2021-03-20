package slogo.view;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.SlogoModel;
import slogo.observers.InputObserver;

public class View {

    private static final String OBJECT_IMAGE = "turtle.png";
    private static final double WINDOW_HEIGHT = 700;
    private static final double WINDOW_WIDTH = 1000;
    private static final double OUTPUT_WIDTH = 500;
    private static final double OUTPUT_HEIGHT = 500;
    private static final double MAIN_CONTENT_PADDING = 10;
    private static final double INPUT_CONSOLE_MAX_HEIGHT = 200;
    private static final String DEFAULT_RESOURCE_PACKAGE = "slogo.view.UIResources.";
    private static final String MENUBAR_BUTTONS_BUNDLE = "menuBar";

    private SlogoModel myModel;
    private InputObserver myInputObserver;
    private final EventHandler<ActionEvent> menubarHandler;



    public View(SlogoModel model, InputObserver observer, Stage primaryStage, EventHandler<ActionEvent> handler)  {
        myModel = model;
        myInputObserver = observer;
        menubarHandler = handler;
        startProgram(primaryStage);
    }

    public void startProgram(Stage window) {

        MenuBar menuBar = createTopBar();
        MenuBar macOSMenuBar = new SLogoMenuBar(menubarHandler);
        OutputScreen output = createOutputScreen();
        myModel.addObserver(output);
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

        VBox everything = new VBox(menuBar, macOSMenuBar, mainContent);
        everything.setVgrow(mainContent, Priority.ALWAYS);
        everything.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(everything, WINDOW_WIDTH,WINDOW_HEIGHT);

//        window.setResizable(false);
        window.setScene(scene);
        window.show();
        output.setPosition(0,0);
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
        input.setMaxHeight(INPUT_CONSOLE_MAX_HEIGHT);
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

    /**
     * Creates a horizontal toolbar that can hold buttons
     * @return a {@code MenuBar} object
     */
    private MenuBar createTopBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setMinHeight(80);
        menuBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        return menuBar;
    }



    public void setTurtleX(double x) {}
    public void setTurtleY(double y) {}
    public void setTurtleHeading(double angle) {}

//    private void setListeners(List<PropertyChangeListener> listeners) {
//        for (PropertyChangeListener l : myListeners) {
//            myTurtle.addListener(l);
//        }
//    }

}

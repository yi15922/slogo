package slogo.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.SlogoModel;
import slogo.observers.InputObserver;

import java.util.ResourceBundle;

public class View {

    private static final String OBJECT_IMAGE = "turtle.png";
    private static final double WINDOW_HEIGHT = 700;
    private static final double WINDOW_WIDTH = 1000;
    private static final double OUTPUT_WIDTH = 500;
    private static final double OUTPUT_HEIGHT = 500;
    private static final double MAIN_CONTENT_PADDING = 10;
    private static final double INPUT_CONSOLE_MAX_HEIGHT = 200;
    private static final String VIEW_RESOURCE_FOLDER = "view";

    // get strings from resource file
    private ResourceBundle mySettings;
    private ResourceBundle myResources;
    private SlogoModel myModel;
    private InputObserver myInputObserver;

    public View(SlogoModel model, InputObserver observer, Stage primaryStage) {
        retrieveResources();
        System.out.println(myResources.getString("Intro"));
        myModel = model;
        myInputObserver = observer;
        startProgram(primaryStage);
    }

    private void retrieveResources() {
        mySettings = ResourceBundle.getBundle("Settings");
        try {
            myResources = ResourceBundle.getBundle(mySettings.getString("DefaultLanguage"));
        } catch (Exception ignore) {
            myResources = ResourceBundle.getBundle("English");
        }
    }

    public void startProgram(Stage window) {

        TopBar topBar = createTopBar();
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

        VBox everything = new VBox(topBar, mainContent);
        everything.setVgrow(mainContent, Priority.ALWAYS);
        everything.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(everything, WINDOW_WIDTH,WINDOW_HEIGHT);

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
        output.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
        return output;
    }

    private TopBar createTopBar() {
        TopBar topBar = new TopBar();
        topBar.setMinHeight(80);
        topBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        return topBar;
    }

}

package slogo.view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.SlogoModel;
import slogo.observers.AlertObserver;
import slogo.observers.InputObserver;
import slogo.observers.UserInputObserver;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class View implements AlertObserver, UserInputObserver {

    private ResourceBundle myLanguages;
    private ResourceBundle mySettings;
    private ResourceBundle myResources;
    private final SlogoModel myModel;
    private final InputObserver myInputObserver;
    private Stage myWindow;

    public View(SlogoModel model, InputObserver observer, Stage primaryStage) {
        myWindow = primaryStage;
        myLanguages = ResourceBundle.getBundle("Languages");
        myModel = model;
        myInputObserver = observer;
        mySettings = ResourceBundle.getBundle("Settings");
        retrieveResources(new Locale(mySettings.getString("DefaultLanguage")));
        startProgram();
    }

    private ComboBox<String> createLanguagesDropdown() {
        ComboBox<String> languages = new ComboBox();
        languages.setPromptText("Select Language");
        for (Enumeration<String> keys = myLanguages.getKeys(); keys.hasMoreElements();) {
            languages.getItems().add(myLanguages.getString(keys.nextElement()));
        }
        languages.setOnAction(e -> {
            for (Enumeration<String> keys = myLanguages.getKeys(); keys.hasMoreElements();) {
                String key = keys.nextElement();
                if (myLanguages.getString(key).equals(languages.getValue())) {
                    retrieveResources(new Locale(key));
                    startProgram();
                }
            }
        });
        return languages;
    }

    private void retrieveResources(Locale locale) {
        myResources = ResourceBundle.getBundle("MyResources", locale);
    }

    public void startProgram() {

        TopBar topBar = createTopBar(myResources);
        OutputScreen output = createOutputScreen();
        myModel.addObserver(output);
        InputConsole input = createInputConsole();
        StackPane workspace = createWorkSpace();
        InputLog log = createInputLog(input);



        SplitPane outputAndInput = new SplitPane(output, input);
        outputAndInput.setOrientation(Orientation.VERTICAL);
        outputAndInput.setDividerPosition(0,1);

        SplitPane logAndWorkspace = new SplitPane(log, workspace);
        logAndWorkspace.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        logAndWorkspace.setMinWidth(Double.parseDouble(mySettings.getString("LogAndWorkSpaceMinWidth")));
//        logAndWorkspace.setMaxHeight(Double.MAX_VALUE);
//        logAndWorkspace.setMaxWidth(Double.MAX_VALUE);

        HBox mainContent = new HBox(outputAndInput, logAndWorkspace);
        HBox.setHgrow(logAndWorkspace, Priority.ALWAYS);
        mainContent.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        mainContent.setPadding(new Insets(Double.parseDouble(mySettings.getString("MainContentPadding"))));

        VBox everything = new VBox(topBar, mainContent);
        VBox.setVgrow(mainContent, Priority.ALWAYS);
        everything.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(everything, Double.parseDouble(mySettings.getString("GUIWidth")),
                                 Double.parseDouble(mySettings.getString("GUIHeight")));

        myWindow.setScene(scene);
        myWindow.show();
        output.setPosition(0,0);
    }

    private InputLog createInputLog(InputConsole input) {
        InputLog log = new InputLog();
        input.addObserver(log);
        log.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        return log;
    }

    private StackPane createWorkSpace() {
        StackPane workspace = new StackPane();
        workspace.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));
        return workspace;
    }

    private InputConsole createInputConsole() {
        InputConsole input = new InputConsole();
//        input.setMaxHeight(Double.parseDouble(mySettings.getString("InputConsoleMaxHeight")));
        input.setMaxWidth(Double.parseDouble(mySettings.getString("OutputWidth")));
        input.addObserver(myInputObserver);
        input.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        return input;
    }

    private OutputScreen createOutputScreen() {
        OutputScreen output = new OutputScreen(mySettings.getString("ObjectImage"));
        output.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));
        output.setMinHeight(Double.parseDouble(mySettings.getString("OutputMinHeight")));
        output.setPrefWidth(Double.parseDouble(mySettings.getString("OutputWidth")));
        return output;
    }

    private TopBar createTopBar(ResourceBundle myResources) {
        mySettings.getKeys();
        TopBar topBar = new TopBar(this, myResources);
        topBar.getChildren().add(createLanguagesDropdown());
        topBar.setMinHeight(Double.parseDouble(mySettings.getString("MenuBarMaxHeight")));
        topBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        return topBar;
    }

    @Override
    public String receiveAlert(String message) {
        return null;
    }

    @Override
    public String receiveErrorAlert(String message) {
        return null;
    }

    @Override
    public void receiveAction(String s) {
        // searches for key with String matching the argument
        for (Enumeration<String> keys = myResources.getKeys(); keys.hasMoreElements();) {

        }
    }
}

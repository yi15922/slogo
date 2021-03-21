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
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.SlogoModel;
import slogo.observers.AlertObserver;
import slogo.observers.InputObserver;
import slogo.observers.UserActionObserver;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
public class View implements AlertObserver, UserActionObserver {

    private static final String DEFAULT_RESOURCE_PACKAGE = "slogo.view.UIResources.";
    private static final String MENUBAR_BUTTONS_BUNDLE = "menuBar";

    private final StackPane myWorkspace;
    private final InputConsole myInput;
    private final InputLog myLog;
    private ResourceBundle myLanguages;
    private ResourceBundle mySettings;
    private ResourceBundle myResources;
    private ResourceBundle myMethods;
    private Locale myLocale;
    private final SlogoModel myModel;
    private final InputObserver myInputObserver;
    private Stage myWindow;
    private OutputScreen myOutputScreen;
    private final EventHandler<ActionEvent> menubarHandler;


    public View(SlogoModel model, InputObserver observer, Stage primaryStage, EventHandler<ActionEvent> handler)  {
        myWindow = primaryStage;
        myLanguages = ResourceBundle.getBundle("Languages");
        myModel = model;
        myInputObserver = observer;
        mySettings = ResourceBundle.getBundle("Settings");
        retrieveResources(myLocale = new Locale(mySettings.getString("DefaultLanguage")));
        //fields assigned here instead of start program so they are not reset when language is switched
        myOutputScreen = createOutputScreen();
        myModel.addObserver(myOutputScreen);
        myWorkspace = createWorkSpace();
        myInput = createInputConsole();
        myLog = createInputLog(myInput);
        menubarHandler = handler;
        startProgram();
    }

    private MenuButton createLanguagesDropdown() {
        MenuButton languages = new MenuButton(myResources.getString("SelectLanguagePrompt"));
        for (Enumeration<String> keys = myLanguages.getKeys(); keys.hasMoreElements();) {
            String langKey = keys.nextElement();
            MenuItem item = new MenuItem(myLanguages.getString(langKey));
            item.setOnAction(e -> {
                retrieveResources(myLocale = new Locale(langKey));
                startProgram();
            });
            languages.getItems().add(item);
        }
        return languages;
    }

    private void retrieveResources(Locale locale) {
        myResources = ResourceBundle.getBundle("MyResources", locale);
        myMethods = ResourceBundle.getBundle("MyUserActionViewMethods", locale);
    }

    public void startProgram() {
        TopBar topBar = createTopBar(myResources);
        MenuBar macOSMenuBar = new SLogoMenuBar(menubarHandler);





        SplitPane outputAndInput = new SplitPane(myOutputScreen, myInput);
        outputAndInput.setOrientation(Orientation.VERTICAL);
        outputAndInput.setDividerPosition(0,1);

        SplitPane logAndWorkspace = new SplitPane(myLog, myWorkspace);
        logAndWorkspace.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, Insets.EMPTY)));
        logAndWorkspace.setMinWidth(Double.parseDouble(mySettings.getString("LogAndWorkSpaceMinWidth")));
//        logAndWorkspace.setMaxHeight(Double.MAX_VALUE);
//        logAndWorkspace.setMaxWidth(Double.MAX_VALUE);

        HBox mainContent = new HBox(outputAndInput, logAndWorkspace);
        HBox.setHgrow(logAndWorkspace, Priority.ALWAYS);
        mainContent.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        mainContent.setPadding(new Insets(Double.parseDouble(mySettings.getString("MainContentPadding"))));

        VBox everything = new VBox(macOSMenuBar, topBar, mainContent);
        everything.setVgrow(mainContent, Priority.ALWAYS);
        everything.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(everything, Double.parseDouble(mySettings.getString("GUIWidth")),
                                 Double.parseDouble(mySettings.getString("GUIHeight")));
        myWindow.setScene(scene);
        myWindow.show();
        myOutputScreen.initializeTurtle();
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
    public void receiveAlert(String message) {
        System.out.println(message);
    }

    @Override
    public void receiveErrorAlert(String message) {
        System.out.println(message);
    }

    @Override
    public void receiveAction(String action) {
        Class thisClass = this.getClass();
        //converts to lower case use selected locale, since different languages could have different ways
        //of lowercasing letters
        try {
            String methodName = myMethods.getString(action.toLowerCase(myLocale).replaceAll("\\s+",""));

            Method method = thisClass.getDeclaredMethod(methodName, null);
            method.invoke(this, null);
        } catch (NoSuchMethodException ignore) {
            receiveErrorAlert("Something went wrong calling the method\n" +
                    "Make sure .properties files have correct method names written");
        } catch (IllegalAccessException ignore) {
            receiveErrorAlert("Something went wrong calling the method\n" +
                    "Make sure .properties files have correct method names written");
        } catch (InvocationTargetException ignore) {
            receiveErrorAlert("Something went wrong calling the method\n" +
                    "Make sure .properties files have correct method names written");
        }
    }


    private void changeBackgroundColor() {
        myOutputScreen.changeBackgroundColor(createDialogueAndGetColor());
    }

    private void changePenColor() {
        myOutputScreen.changePenColor(createDialogueAndGetColor());
    }

    private String createDialogueAndGetColor() {
        Dialog dialog = new TextInputDialog();
        dialog.setHeaderText("Color in html, hex, or rgb format");
        Optional<String> result = dialog.showAndWait();
        String color = "";

        if (result.isPresent()) {

            color = result.get();
        }
        return color;
    }

}

package slogo.view;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import slogo.WindowAlert;
import slogo.compiler.Workspace;
import slogo.model.Turtle;
import slogo.observers.InputObserver;
import slogo.observers.UserActionObserver;


import java.util.Enumeration;
import java.util.Locale;
import java.util.Optional;

/**
 * The View class represents the window itself. It initializes all required
 * visual components and retrieves all necessary resources. The class
 * is ultimately repsonsible for establishing the relationships between
 * the visual components.
 *
 * @author Liam Idrovo
 */
public class View implements UserActionObserver {

    private static final String DEFAULT_RESOURCE_PACKAGE = "slogo.view.UIResources.";
    private static final String MENUBAR_BUTTONS_BUNDLE = "menuBar";

    private final WorkspaceDisplay myWorkspace;
    private final InputConsole myInput;
    private final InputLog myLog;
    private ResourceBundle myLanguages;
    private ResourceBundle mySettings;
    private ResourceBundle myResources;
    private ResourceBundle myMethods;
    private Locale myLocale;
    private final Turtle myModel;
    private final InputObserver myInputObserver;
    private Stage myWindow;
    private OutputScreen myOutputScreen;
    private StatsDisplay myStatsDisplay;
    private final EventHandler<ActionEvent> menubarHandler;
    private WindowAlert myWindowAlert;


    /**
     * Initializes a View object.
     *
     * @param model     Turtle object representing the program's model
     * @param observer  InputObserver that is added as an observer to the InputConsole
     * @param primaryStage  JavaFX stage representing the window and to which everything is added
     * @param handler   handler associated with the MenuBar (not to be confused with TopBar)
     * @param modelWorkspace    Workspace object to which user defined commands and variables will be saved
     */
    public View(Turtle model, InputObserver observer, Stage primaryStage, EventHandler<ActionEvent> handler, Workspace modelWorkspace, String input)  {
        myWindow = primaryStage;
        myLanguages = ResourceBundle.getBundle("Languages");
        myModel = model;
        myInputObserver = observer;
        mySettings = ResourceBundle.getBundle("Settings");
        retrieveResources(myLocale = new Locale(mySettings.getString("DefaultLanguage")));
        //fields assigned here instead of start program so they are not reset when language is switched
        myOutputScreen = createOutputScreen();
        myModel.assignObserverForTurtles(myOutputScreen);
        myModel.addObserver(myStatsDisplay);
        myWorkspace = createWorkSpace();
        modelWorkspace.addObserver(myWorkspace);
        myInput = createInputConsole();
        myLog = createInputLog(myInput);
        menubarHandler = handler;
        startProgram();

        if (input != null) {
            runInput(input);
        }
    }

    /**
     * Adds visual components to the stage and shows the stage.
     *
     * This method is called every time a View object is initialized or a
     * new language is selected. Thus, the most recently selected resources are used
     * in constructing the visual components.3
     */
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

    /**
     * Sends input to the InputConsole to be parsed.
     *
     * @param input     input to be parsed
     */
    public void runInput(String input) {
        myInput.sendInputToObservers(input);
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


    private InputLog createInputLog(InputConsole input) {
        InputLog log = new InputLog();
        log.addObserver(this);
        input.addObserver(log);
        log.setBackground(new Background(new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY)));
        return log;
    }

    private WorkspaceDisplay createWorkSpace() {
        WorkspaceDisplay workspace = new WorkspaceDisplay(this);
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
    public void receiveAction(String action, Object[] args) {
        Class thisClass = this.getClass();
        String error = "";
        //converts to lower case use selected locale, since different languages could have different ways
        //of lowercasing letters
        try {
            error = mySettings.getString("UserActionNotKey") + action;
            String methodName = myMethods.getString(action.toLowerCase(myLocale).replaceAll("\\s+", ""));
            Class[] paramTypes = new Class[args.length];
            for (int i = 0; i < args.length; ++i) {
                paramTypes[i] = args[i].getClass();
            }
            error = mySettings.getString("MethodNotImplemented") +  methodName + "\t" + Arrays.toString(paramTypes);
            Method method = thisClass.getDeclaredMethod(methodName, paramTypes);

            error = mySettings.getString("ViewMethodInvocationError");

            method.invoke(this, args);
        } catch (Exception ignore) {
            WindowAlert.throwErrorAlert(error);
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

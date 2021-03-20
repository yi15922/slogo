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
        MenuBar macOSMenuBar = makeMacOSMenuBar();
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


    /**
     * Populates the system default menu bar on MacOS devices.
     *
     * Detects the operating system, and if the system is MacOS, creates a menu bar with various
     * menus and options such as open, save, or open new window. Many of these options can
     * have keyboard combinations attached to them.
     * @return a {@code MenuBar} object.
     */
    private MenuBar makeMacOSMenuBar(){
        MenuBar menuBar = new MenuBar();
        String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac")) {
            Platform.runLater(() -> menuBar.useSystemMenuBarProperty().set(true)) ;
        }

        ResourceBundle menubarBundle = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + MENUBAR_BUTTONS_BUNDLE);

        menuBar.getMenus().add(makeMenuFromProperties("File", menubarBundle));
        return menuBar;
    }

    /**
     * Creates a {@code Menu} with all corresponding {@code MenuItem}s from a properties
     * file.
     *
     * An {@code String} input is needed for the name of the menu. This method will then
     * look in the properties file for all menu items for this menu, and add them as children
     * to this menu. This will invoke {@code makeMenuButton} to create menu item buttons as well
     * as their {@code EventHandler}.
     *
     * @param menuName name of the top level menu to create
     * @param bundle resource bundle for the menu bar
     * @return a {@code Menu} object complete with menu items connected to event handlers.
     */
    private Menu makeMenuFromProperties(String menuName, ResourceBundle bundle){
        String fileMenu = bundle.getString(menuName + ".menu");
        Menu menu = null;
        LinkedList<String> menuItems = new LinkedList<String>(Arrays.asList(fileMenu.split(",")));
        if (menuItems.size() != 0) {
            menu = new Menu(menuItems.remove());
            while (menuItems.size() != 0) {
                MenuItem newMenuItem = makeMenuButton(menuItems.remove(), menubarHandler, bundle);
                menu.getItems().add(newMenuItem);
            }
        }
        return menu;
    }

    /**
     * Creates a menu button using data from a resource bundle.
     *
     * The ID of the {@code MenuItem} created will also be used by reflection later to
     * invoke methods.
     *
     * @param property the property name of the button being created
     * @param eHandler the {@code EventHandler }for the button being created
     * @param bundle a {@code ResourceBundle} for the button being created
     * @return a {@code MenuItem} with a label, an ID, and an event handler
     */
    private MenuItem makeMenuButton(String property, EventHandler<ActionEvent> eHandler,
                                ResourceBundle bundle) {
        MenuItem result = new MenuItem();
        String label = bundle.getString(property);
        result.setText(label);
        result.setOnAction(eHandler);
        result.setId(property);
        return result;
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

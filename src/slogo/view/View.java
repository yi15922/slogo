package slogo.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.Stack;

public class View extends Application {

    public void runApplication(String[] args) {
        launch( args );
    }

    @Override
    public void start(Stage window) throws Exception {


        MenuBar menuBar = new MenuBar();
        menuBar.setMinHeight(10);
        menuBar.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        StackPane output = new StackPane();
        output.setMinHeight(50);
        output.setBackground(new Background(new BackgroundFill(Color.PURPLE, CornerRadii.EMPTY, Insets.EMPTY)));

        TextArea input = new TextArea();
        input.setMaxHeight(50);
        input.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        StackPane workspace = new StackPane();
        workspace.setMinWidth(20);
        workspace.setBackground(new Background(new BackgroundFill(Color.CHOCOLATE, CornerRadii.EMPTY, Insets.EMPTY)));

        StackPane log = new StackPane();
        log.setMinWidth(20);
        log.setBackground(new Background(new BackgroundFill(Color.SPRINGGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox outputAndInput = new VBox(output, input);
        outputAndInput.setVgrow(output, Priority.ALWAYS);
        outputAndInput.setVgrow(input, Priority.ALWAYS);
        HBox mainContent = new HBox(outputAndInput, workspace, log);
        mainContent.setHgrow(outputAndInput, Priority.ALWAYS);
        mainContent.setHgrow(workspace, Priority.ALWAYS);
        mainContent.setHgrow(log, Priority.ALWAYS);
        VBox everything = new VBox(menuBar, mainContent);
        outputAndInput.setVgrow(mainContent, Priority.ALWAYS);

        Scene scene = new Scene(everything);

        window.setScene(scene);
        window.show();
    }
}

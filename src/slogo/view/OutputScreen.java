package slogo.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import slogo.model.SingleTurtle;
import slogo.observers.ModelObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The OutputScreen class represent the graphical component displaying
 * the turtles.
 *
 * It extends Region from JavaFX.
 *
 * @author Liam Idrovo
 */

public class OutputScreen extends Region implements ModelObserver {

    private Map<Integer, Node> myTurtles = new HashMap<>();
    private Map<Integer, Boolean> myPens = new HashMap<>();
    private Map<Integer, Double> myHeadings= new HashMap<>();
    private Paint myPenColor = Color.BLACK;
    private boolean isTurtleInitialized = false;
    private String myDisplayImage;

    /**
     * Initializes a newly created OutputScreen object that
     * uses the specified file path to find an image to use
     * as the Turtle's image.
     *
     * If the image path is invalid, a circle is displayed by default.
     *
     * @param displayObject   String representation of image file
     */
    public OutputScreen(String displayObject) {
        // disallows displayed object from appearing outside of output screen
        myDisplayImage = displayObject;
        Rectangle outputClip = new Rectangle();
        this.setClip(outputClip);
        this.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

    /**
     * Sets position of Turtle to the center of output screen and puts its pen down.
     *
     * Should be called after adding all visual components to a scene in View and calling
     * show() on the stage.
     */
    public void initializeTurtle() {
        if (!isTurtleInitialized) setPosition(1,0,0);
        isTurtleInitialized = true;
        myPens.put(1, true);
    }

    /**
     * Changes the layout position of the specified Turtle
     *
     * The new x and y position are considered positions in a cartesian coordinate system with its origin at center of output screen.
     *
     * @param id    the id of the specified turtle
     * @param x     new x position
     * @param y     new y position
     */
    public void setPosition(int id, double x, double y) {
        double localX = x + this.getBoundsInLocal().getWidth()/2;
        double localY = this.getBoundsInLocal().getHeight()/2 - y;
        if (isPenDown(id)) drawLine(id, localX, localY);
        myTurtles.get(id).setLayoutX(localX);
        myTurtles.get(id).setLayoutY(localY);
    }

    /**
     * Changes the background of output screen to color specified.
     *
     * @param color     String representing HTML color
     */
    public void changeBackgroundColor(String color) {
        if (!color.equals("")) this.setBackground(new Background(new BackgroundFill(Color.valueOf(color), null, null)));
    }

    /**
     * Changes the color of all pens.
     *
     * @param color     String representing HTML color
     */
    public void changePenColor(String color) {
        if (!color.equals("")) myPenColor = Color.valueOf(color);
    }

    @Override
    public void receiveNewPosition(int id, double x, double y) {
        setPosition(id ,x, y);
    }

    @Override
    public void receiveHeading(int id, double heading) {
        myHeadings.put(id, heading);
    }

    @Override
    public void checkPenStatus(int id, boolean b) {
        myPens.put(id, b);
    }

    @Override
    public void receiveShow(int id, boolean show) {
        myTurtles.get(id).setVisible(show);
    }

    @Override
    public void receiveShape(int id, int shape) {

    }

    @Override
    public void checkPenColor(int id, int index) {

    }

    @Override
    public void checkPenSize(int id, double pixels) {

    }

    @Override
    public void addTurtle(int id) {
        try {
            myTurtles.put(id, makeDisplayObject(20, new Image(this.getClass().getClassLoader().getResourceAsStream(myDisplayImage))));
        } catch (Exception ignore) {
            myTurtles.put(id, new Circle(10));
        }
        myPens.put(id, false);
        myHeadings.put(id, 0.0);
        this.getChildren().add(myTurtles.get(id));
    }

    private boolean isPenDown(int id) {
        return myPens.get(id);
    }

    private void drawLine(int id, double x, double y) {
        Line line = new Line(myTurtles.get(id).getLayoutX(), myTurtles.get(id).getLayoutY(), x, y);
        line.setStroke(myPenColor);
        this.getChildren().add(line);
    }


    // make object to be displayed
    private ImageView makeDisplayObject (int width, Image image) {
        ImageView result = new ImageView(image);
        result.setFitWidth(width);
        result.setPreserveRatio(true);
        setOnMouseClicked(event -> System.out.println("User clicked"));
        return result;
    }
}

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
import slogo.observers.ModelObserver;


public class OutputScreen extends Region implements ModelObserver {

    private Node myObject;
    private boolean isPenDown = false;
    private Paint myPenColor = Color.BLACK;
    private boolean isTurtleInitialized = false;

    public OutputScreen(String displayObject) {
        try {
            myObject = makeDisplayObject("displayObject", 20, new Image(this.getClass().getClassLoader().getResourceAsStream(displayObject)));
        } catch (Exception ignore) {
            myObject = new Circle(10);
        }
        this.getChildren().addAll(myObject);

        // disallows displayed object from appearing outside of output screen
        Rectangle outputClip = new Rectangle();
        this.setClip(outputClip);
        this.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

    public void initializeTurtle() {
        if (!isTurtleInitialized) setPosition(0,0);
        isTurtleInitialized = true;
    }

    public void setPosition(double x, double y) {
        double localX = x + this.getBoundsInLocal().getWidth()/2;
        double localY = this.getBoundsInLocal().getHeight()/2 - y;
        if (isPenDown) drawLine(localX, localY);
        myObject.setLayoutX(localX);
        myObject.setLayoutY(localY);
    }

    private void drawLine(double x, double y) {
        Line line = new Line(myObject.getLayoutX(), myObject.getLayoutY(), x, y);
        line.setStroke(myPenColor);
        this.getChildren().add(line);
    }


    // make object to be displayed
    private ImageView makeDisplayObject (String id, int width, Image image) {
        ImageView result = new ImageView(image);
        result.setId(id);
        result.setFitWidth(width);
        result.setPreserveRatio(true);
        return result;
    }

    public void changeBackgroundColor(String color) {
        if (!color.equals("")) this.setBackground(new Background(new BackgroundFill(Color.valueOf(color), null, null)));
    }

    public void changePenColor(String color) {
        if (!color.equals("")) myPenColor = Color.valueOf(color);
    }


    @Override
    public void receiveNewPosition(double x, double y) {
        setPosition(x, y);
    }

    @Override
    public void receiveHeading(double heading) {

    }

    @Override
    public void checkPen(boolean b) {
        isPenDown = b;
    }
}

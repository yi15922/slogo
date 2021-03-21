package slogo.view;

import java.util.Collection;
import java.util.Collections;
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

    private Node myTurtleNode;
    private TurtleView myTurtleView;
    private boolean isPenDown = false;
    private Paint myPenColor = Color.BLACK;
    private boolean isTurtleInitialized = false;

    public OutputScreen(String displayObject) {

        myTurtleView = new TurtleView(displayObject);
        myTurtleNode = myTurtleView.getTurtleNode();
        this.getChildren().add(myTurtleNode);
        this.getChildren().add(myTurtleView);

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
        myTurtleView.setPosition(localX, localY);
    }

    private void drawLine(double x, double y) {
        Line line = new Line(myTurtleNode.getLayoutX(), myTurtleNode.getLayoutY(), x, y);
        line.setStroke(myPenColor);
        this.getChildren().add(line);
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
    public void checkPenStatus(boolean b) {
        isPenDown = b;
    }

    @Override
    public void receiveShow(boolean show) {

    }

    @Override
    public void receiveShape(int shape) {

    }

    @Override
    public void checkPenColor(int index) {

    }

    @Override
    public void checkPenSize(double pixels) {

    }


}

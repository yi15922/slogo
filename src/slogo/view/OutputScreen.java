package slogo.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import slogo.observers.ModelObserver;


public class OutputScreen extends Region implements ModelObserver {

    private Node myObject;
    private double myWidth;
    private double myHeight;
    private boolean isPenDown = false;

    public OutputScreen(String displayObject) {
        try {
            myObject = makeDisplayObject("displayObject", 20, new Image(this.getClass().getClassLoader().getResourceAsStream(displayObject)));
        } catch (Exception ignore) {
            myObject = new Circle(10);
        }
//        this.setWidth(width);
//        this.setHeight(height);
        this.getChildren().addAll(myObject);

        // disallows displayed object from appearing outside of output screen
        Rectangle outputClip = new Rectangle();
        this.setClip(outputClip);
        this.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

//    public void setX(double x) {
//        myObject.setX(x + myWidth/2);
//    }
//
//    public void setY(double y) {
//        myObject.setY(myHeight/2 - y);
//    }

    public void setPosition(double x, double y) {
        double localX = x + this.getBoundsInLocal().getWidth()/2;
        double localY = this.getBoundsInLocal().getHeight()/2 - y;
        if (isPenDown) drawLine(localX, localY);
        myObject.setLayoutX(localX);
        myObject.setLayoutY(localY);
    }

    private void drawLine(double x, double y) {
        Line line = new Line(myObject.getLayoutX(), myObject.getLayoutY(), x, y);
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

//    @Override
//    public void receiveXCor(double x) {
//        setX(x);
//    }
//
//    @Override
//    public void receiveYCor(double y) {
//        setY(y);
//    }

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

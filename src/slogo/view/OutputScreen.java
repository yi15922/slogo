package slogo.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import slogo.observers.ModelObserver;


public class OutputScreen extends Region implements ModelObserver {

    private ImageView myObject;
    private double myWidth;
    private double myHeight;

    public OutputScreen(Image displayObject, double width, double height) {
        myObject = makeDisplayObject("displayObject", 20, displayObject);
        myWidth = width;
        myHeight = height;
        this.getChildren().addAll(myObject);

        // disallows displayed object from appearing outside of output screen
        Rectangle outputClip = new Rectangle();
        this.setClip(outputClip);
        this.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

    public void setX(double x) {
        myObject.setX(x + myWidth/2);
    }

    public void setY(double y) {
        myObject.setY(myHeight/2 - y);
    }

    // make object to be displayed
    private ImageView makeDisplayObject (String id, int width, Image image) {
        ImageView result = new ImageView(image);
        result.setId(id);
        result.setFitWidth(width);
        result.setPreserveRatio(true);
        return result;
    }

    @Override
    public void receiveXCor(double x) {
        setX(x);
    }

    @Override
    public void receiveYCor(double y) {
        setY(y);
    }

    @Override
    public void receiveHeading(double heading) {

    }

    @Override
    public void checkPen(boolean b) {

    }
}

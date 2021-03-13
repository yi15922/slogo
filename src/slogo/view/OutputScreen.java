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


public class OutputScreen extends Region {

    private ImageView myObject;

    public OutputScreen(Image displayObject) {
        myObject = makeDisplayObject("displayObject", 20, displayObject);
        setX(0);
        setY(0);
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
        myObject.setX(x + this.getWidth());
    }

    public void setY(double y) {
        myObject.setY(y + this.getWidth());
    }

    // make object to be displayed
    private ImageView makeDisplayObject (String id, int width, Image image) {
        ImageView result = new ImageView(image);
        result.setId(id);
        result.setFitWidth(width);
        result.setPreserveRatio(true);
        return result;
    }
}

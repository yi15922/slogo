package slogo.view;

import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class OutputScreen extends Region {

    private Circle myObject = new Circle(0, 0, 20, Color.BLUE);

    public OutputScreen() {
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
        myObject.setCenterX(x);
    }

    public void setY(double y) {
        myObject.setCenterY(y);
    }
}

package slogo.view;

import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;

public class OutputScreen extends Region {

    private Circle myObject = new Circle(0, 0, 20, Color.PURPLE);

    public OutputScreen() {
        this.getChildren().add(myObject);
    }

    public void setX(double x) {
        myObject.setCenterX(x);
    }

    public void setY(double y) {
        myObject.setCenterY(y);
    }
}

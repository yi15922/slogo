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
import slogo.TurtleObserver;
import slogo.model.Turtle;
import slogo.observers.ModelObserver;


public class OutputScreen extends Region implements TurtleObserver {

    private Collection<TurtleView> myTurtleViews;
    private Turtle myTurtle;
    private String IMAGEFILE = null;
    private Paint myPenColor = Color.BLACK;


    public OutputScreen(String displayObject, Turtle turtle) {
        IMAGEFILE = displayObject;
        myTurtle = turtle;
        turtle.addObserver(this);

        this.getChildren().add(new TurtleView(IMAGEFILE, 1, this));
        // disallows displayed object from appearing outside of output screen
        Rectangle outputClip = new Rectangle();
        this.setClip(outputClip);
        this.layoutBoundsProperty().addListener((observableValue, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

    private void addTurtleView(int id){
        TurtleView newTurtleView = new TurtleView(IMAGEFILE, id, this);
        this.getChildren().add(newTurtleView);
    }


    public void drawLine(Line line){
        this.getChildren().add(line);
    }



    public void changeBackgroundColor(String color) {
        if (!color.equals("")) this.setBackground(new Background(new BackgroundFill(Color.valueOf(color), null, null)));
    }

    public void changePenColor(String color) {
        if (!color.equals("")) myPenColor = Color.valueOf(color);
    }


    @Override
    public void receiveTurtle(int id) {
        System.out.println("Created turtle " + id);
        addTurtleView(id);
    }

    @Override
    public void receiveBackground(int index) {

    }
}

package slogo.view;

import java.util.Objects;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import slogo.observers.ModelObserver;

/**
 * An image representation of a {@link slogo.model.SingleTurtle}. This object extends
 * {@code Button} so that the user can click on it. If the given image filename cannot
 * be found in the resources folder, the turtle will be displayed as a circle by default.
 *
 * This object implements {@link ModelObserver} which updates the visual turtle's position
 * and heading.
 */

public class TurtleView extends ImageView implements ModelObserver {

  private Button myButton = new Button();

  private static final int WIDTH = 20;
  private final int ID;
  private boolean isPenDown;
  private Paint myPenColor = Color.BLACK;
  private OutputScreen outputScreen;


  public TurtleView(String imageFileName, int id, OutputScreen outputScreen){
    this.outputScreen = outputScreen;
    super.setImage(new Image(Objects
        .requireNonNull(this.getClass().getClassLoader().getResourceAsStream(imageFileName))));
    ID = id;
    setFitWidth(WIDTH);
    setPreserveRatio(true);
    setPosition(50,50);
    myButton.setMinSize(10, 10);
    myButton.setOpacity(0);
    setOnMouseClicked(event -> System.out.println("User clicked " + ID));

    isPenDown = true;

    setPosition(70, 100);
  }



  private void setPosition(double x, double y){
    double localX = x + this.getBoundsInLocal().getWidth()/2;
    double localY = this.getBoundsInLocal().getHeight()/2 - y;
    if (isPenDown) drawLine(localX, localY);
    this.setLayoutX(x);
    this.setLayoutY(y);
  }


  private void drawLine(double x, double y) {
    Line line = new Line(getLayoutX(), getLayoutY(), x, y);
    line.setStroke(myPenColor);
    outputScreen.drawLine(line);
  }


  @Override
  public void receiveNewPosition(int id, double x, double y) {
    setPosition(x, y);
  }

  @Override
  public void receiveHeading(int id, double heading) {

  }

  @Override
  public void checkPenStatus(int id, boolean b) {
    isPenDown = b;
  }

  @Override
  public void receiveShow(int id, boolean show) {

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
}

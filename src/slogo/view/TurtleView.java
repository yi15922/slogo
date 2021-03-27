package slogo.view;

import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import slogo.observers.ModelObserver;

/**
 * An image representation of a {@link slogo.model.SingleTurtle}. This object extends
 * {@code Button} so that the user can click on it. If the given image filename cannot
 * be found in the resources folder, the turtle will be displayed as a circle by default.
 */
// TODO: Make this contain both the image and the button somehow
public class TurtleView extends ImageView implements ModelObserver {

  private Button myButton = new Button();

  private static final int WIDTH = 20;
  private final int ID;

  public TurtleView(String imageFileName, int id){

    super.setImage(new Image(Objects
        .requireNonNull(this.getClass().getClassLoader().getResourceAsStream(imageFileName))));
    ID = id;
    setFitWidth(WIDTH);
    setPreserveRatio(true);
    myButton.setMinSize(10, 10);
    myButton.setOpacity(0);
    setOnMouseClicked(event -> System.out.println("User clicked " + ID));

  }


  public void setPosition(double x, double y){
    this.setLayoutX(x);
    this.setLayoutY(y);
  }

  @Override
  public void receiveNewPosition(int id, double x, double y) {

  }

  @Override
  public void receiveHeading(int id, double heading) {

  }

  @Override
  public void checkPenStatus(int id, boolean b) {

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

  @Override
  public void addTurtle(int id) {

  }
}

package slogo.view;

import java.util.Objects;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

/**
 * An image representation of a {@link slogo.model.SingleTurtle}. This object extends
 * {@code Button} so that the user can click on it. If the given image filename cannot
 * be found in the resources folder, the turtle will be displayed as a circle by default.
 */
public class TurtleView extends Button {

  private Node myNode;
  private static int ID = 0;

  public TurtleView(String imageFileName){
    try {
      myNode = makeDisplayObject("displayObject", 20, new Image(Objects
          .requireNonNull(this.getClass().getClassLoader().getResourceAsStream(imageFileName))));
    } catch (Exception ignore) {
      myNode = new Circle(10);
    }
    setMinSize(10, 10);
    setOnAction(event -> System.out.printf("User clicked turtle %d\n", ID));
    setOpacity(0);
    ID++;
  }

  // make object to be displayed
  private ImageView makeDisplayObject (String id, int width, Image image) {
    ImageView result = new ImageView(image);
    result.setId(id);
    result.setFitWidth(width);
    result.setPreserveRatio(true);
    return result;
  }

  public Node getTurtleNode(){
    return myNode;
  }

  public void setPosition(double x, double y){
    myNode.setLayoutX(x);
    myNode.setLayoutY(y);
    this.setLayoutX(x);
    this.setLayoutY(y);
  }

}

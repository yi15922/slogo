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
// TODO: Make this contain both the image and the button somehow
public class TurtleView extends ImageView {

  private Button myButton = new Button();

  private static final int WIDTH = 20;

  public TurtleView(String imageFileName){

    super.setImage(new Image(Objects
        .requireNonNull(this.getClass().getClassLoader().getResourceAsStream(imageFileName))));
    setFitWidth(WIDTH);
    setPreserveRatio(true);
    myButton.setMinSize(10, 10);
    myButton.setOpacity(0);
    setOnMouseClicked(event -> System.out.println("User clicked"));

  }


  public void setPosition(double x, double y){
    this.setLayoutX(x);
    this.setLayoutY(y);
  }

}

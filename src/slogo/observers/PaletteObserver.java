package slogo.observers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public interface PaletteObserver {
  void receiveUpdatedColor(int index, Color updatedColor);

  void receiveUpdatedShape(int index, Shape updatedShape);

}

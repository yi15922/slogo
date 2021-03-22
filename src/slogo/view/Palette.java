package slogo.view;

import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import slogo.Observable;
import slogo.observers.PaletteObserver;

public class Palette implements Observable<PaletteObserver> {
  protected List<PaletteObserver> myObservers;

  protected void notifyUpdatedColor(int index, Color updatedColor) {
    for (PaletteObserver o : myObservers) {
      o.receiveUpdatedColor(index, updatedColor);
    }
  }

  protected void receiveUpdatedShape(int index, Shape updatedShape) {
    for (PaletteObserver o : myObservers) {
      o.receiveUpdatedShape(index, updatedShape);
    }
  }

  @Override
  public boolean isObserver(PaletteObserver observer) {
    return myObservers.contains(observer);
  }

  @Override
  public void addObserver(PaletteObserver observer) {
    myObservers.add(observer);
  }

  @Override
  public void removeObserver(PaletteObserver observer) {
    myObservers.remove(observer);
  }
}

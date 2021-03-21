package slogo.view;

import javafx.scene.layout.VBox;
import slogo.observers.ModelObserver;

public class StatsDisplay extends VBox implements ModelObserver {



  @Override
  public void receiveNewPosition(double x, double y) {

  }

  @Override
  public void receiveHeading(double heading) {

  }

  @Override
  public void checkPen(boolean b) {

  }

  @Override
  public void receiveShow(boolean show) {

  }

  @Override
  public void receiveShape(int shape) {

  }
}

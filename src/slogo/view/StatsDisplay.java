package slogo.view;

import javafx.scene.layout.VBox;
import slogo.observers.ModelObserver;

public class StatsDisplay extends VBox implements ModelObserver {



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


}

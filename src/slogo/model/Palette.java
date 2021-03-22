package slogo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import slogo.Observable;
import slogo.SLogoException;
import slogo.observers.PaletteObserver;

/**
 * An observable palette that holds {@code Color} and {@code Shape} objects that can
 * be referenced by index. The palette holds an active color, which notifies all
 * listeners when changed.
 *
 * Color information is obtained from a {@code ResourceBundle}.
 */
public class Palette implements Observable<PaletteObserver> {
  protected List<PaletteObserver> myObservers = new ArrayList<>();
  private Color activeColor;
  private Map<Integer, Color> colorMap;
  private Map<Integer, Shape> shapeMap;
  private final String COLOR_PROPERTIES = "slogo.view.UIResources.colorPalette";
  private ResourceBundle resources;

  /**
   * Constructs a {@code Palette} object with the default color active.
   */
  public Palette(){
    resources = ResourceBundle.getBundle(COLOR_PROPERTIES);
    colorMap = new HashMap<>();
    for (String index : resources.keySet()) {
      colorMap.put(Integer.parseInt(index), Color.web(resources.getString(index)));
    }
    activeColor = Color.web(resources.getString(Integer.toString(1)));
  }

  public Color getActiveColor() {
    return activeColor;
  }

  /**
   * Sets the color referenced by {@code index} as the active color, also notifies
   * all {@link PaletteObserver}.
   * @param index the index of color needed
   */
  public void setActiveColor(int index) {
    activeColor = colorMap.get(index);
    if (activeColor == null) throw new SLogoException("Invalid color choice");
    notifyUpdatedColor(index);
  }

  /**
   * Create a new color and set it as the active color by supplying a {@code String}
   * containing a hex value. If the user inputs an invalid string, a {@link SLogoException}
   * will be thrown.
   * @param hexValue a string representation of a hex color value.
   */
  public void setNewColor(int index, String hexValue) {
    Color color = null;
    try {
      color = Color.web(hexValue);
      activeColor = color;
      colorMap.put(index, color);
      notifyUpdatedColor(index);
    } catch (IllegalArgumentException exception) {
      throw new SLogoException("Invalid hex color input");
    }
  }

  public Color getColorAtIndex(int index) {
    Color ret = colorMap.get(index);
    if (ret == null) throw new SLogoException("Invalid color index");
    return ret;

  }

  protected void notifyUpdatedColor(int index) {
    for (PaletteObserver o : myObservers) {
      o.receiveUpdatedColor(index, activeColor);
    }
  }

  protected void notifyUpdatedShape(int index, Shape updatedShape) {
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

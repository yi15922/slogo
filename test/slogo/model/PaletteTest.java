package slogo.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.SLogoException;
import slogo.observers.PaletteObserver;

public class PaletteTest implements PaletteObserver {
  Palette palette;
  Color updatedColor;

  @BeforeEach
  void createPalette() {
    palette = new Palette();
    palette.addObserver(this);
  }

  @Test
  void testDefaultColor() {
    updatedColor = palette.getActiveColor();
    assertEquals(Color.web("#2ECC71"), updatedColor);
  }

  @Test
  void testSetActiveColor() {
    assertDoesNotThrow(() -> palette.setActiveColor(3));
    assertEquals(Color.web("#000000"), updatedColor);
  }

  @Test
  void testSetNewColor() {
    assertDoesNotThrow(() -> palette.setNewColor(5, "#2980B9"));
    assertEquals(Color.web("#2980B9"), updatedColor);
    assertEquals(Color.web("#2980B9"), palette.getColorAtIndex(5));
  }

  @Test
  void testInvalidSetNewColor() {
    assertThrows(SLogoException.class, () -> palette.setNewColor(3, "flskadjf"));
  }

  @Test
  void testInvalidIndex() {
    assertThrows(SLogoException.class, () -> palette.getColorAtIndex(3245));
  }

  @Override
  public void receiveUpdatedColor(int index, Color updatedColor) {
    this.updatedColor = updatedColor;
  }

  @Override
  public void receiveUpdatedShape(int index, Shape updatedShape) {

  }
}

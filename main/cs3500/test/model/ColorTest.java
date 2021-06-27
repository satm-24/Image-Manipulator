package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Hold tests for the color class. Ensures all functionality for colors works properly.
 */
public class ColorTest {

  Color white;
  Color black;

  @Before
  public void setUp() {
    white = new Color(255, 255, 255);
    black = new Color(0, 0, 0);
  }

  @Test
  public void testToString() {
    assertEquals("Color{red=255, green=255, blue=255}", white.toString());
    assertEquals("Color{red=0, green=0, blue=0}", black.toString());
  }

  @Test
  public void testTurnIntoDoubleVector() {
    assertArrayEquals(new Double[][]{{(double) 255}, {(double) 255}, {(double) 255}},
        white.turnIntoDblVector());
    assertArrayEquals(new Double[][]{{(double) 0}, {(double) 0}, {(double) 0}},
        black.turnIntoDblVector());
  }

  @Test
  public void testRGBToString() {
    assertEquals("255\n255\n255\n", white.rgbToStr());
    assertEquals("0\n0\n0\n", black.rgbToStr());
  }

  @Test
  public void testCopy() {
    assertEquals(new Color(255, 255, 255), white.copy());
    assertEquals(new Color(0, 0, 0), black.copy());
  }

  @Test
  public void testColorConstructorClamp() {
    assertEquals(new Color(0, 0, 0), new Color(-100, -100, -100));
    assertEquals(new Color(255, 255, 255), new Color(1000, 1000, 1000));
  }


}

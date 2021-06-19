package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import model.Color;
import model.Pixel;
import org.junit.Before;
import org.junit.Test;

/**
 * Holds tests for the pixel class.
 */
public class PixelTest {

  Color white;
  Color black;

  Pixel whitePixel;
  Pixel blackPixel;

  @Before
  public void setUp() {
    white = new Color(255, 255, 255);
    black = new Color(0, 0, 0);

    whitePixel = new Pixel(white);
    blackPixel = new Pixel(black);
  }

  @Test
  public void testTurnIntoDoubleVector() {
    assertArrayEquals(new Double[][]{{(double) 255}, {(double) 255}, {(double) 255}},
        whitePixel.getClrAsDoubleVector());
    assertArrayEquals(new Double[][]{{(double) 0}, {(double) 0}, {(double) 0}},
        blackPixel.getClrAsDoubleVector());
  }

  @Test
  public void testRGBToString() {
    assertEquals("255\n255\n255\n", whitePixel.convertRGBToStr());
    assertEquals("0\n0\n0\n", blackPixel.convertRGBToStr());
  }

  @Test
  public void testGetColor() {
    assertEquals(white, whitePixel.getClr());
    assertEquals(black, blackPixel.getClr());
  }

  @Test
  public void testToString() {
    assertEquals("Color{red=255, green=255, blue=255}", whitePixel.toString());
    assertEquals("Color{red=0, green=0, blue=0}", blackPixel.toString());
  }

  @Test
  public void testCopy() {
    assertEquals(new Pixel(white), whitePixel.copy());
    assertEquals(new Pixel(black), blackPixel.copy());
  }
}

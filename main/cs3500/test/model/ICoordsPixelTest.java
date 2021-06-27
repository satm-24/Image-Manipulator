package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for ICoordsPixels.
 */
public class ICoordsPixelTest {

  @Test
  public void testGetY() {

    PixelWithCoords pixel = new PixelWithCoords(new Pixel(new Color(100, 100, 100)), 50, 40);

    assertEquals(40, pixel.getY());

  }

  @Test
  public void testGetX() {

    PixelWithCoords pixel = new PixelWithCoords(new Pixel(new Color(100, 100, 100)), 50, 40);

    assertEquals(50, pixel.getX());

  }

  @Test
  public void testGetPixel() {

    PixelWithCoords pixel = new PixelWithCoords(new Pixel(new Color(100, 100, 100)), 50, 40);

    assertEquals(new Pixel(new Color(100, 100, 100)), pixel.getPixel());

  }


}

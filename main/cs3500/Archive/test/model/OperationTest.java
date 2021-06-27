package model;

import static org.junit.Assert.assertArrayEquals;

import model.operations.ColorTransformation;
import model.operations.Filter;
import org.junit.Test;

/**
 * Tests for operations.
 */
public class OperationTest {


  @Test(expected = IllegalArgumentException.class)
  public void testColorTransNull() {
    new ColorTransformation(TransformationType.GREYSCALE).apply(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterNull() {
    new Filter(FilterType.BLUR).apply(null);
  }

  @Test
  public void testApplyColorTrans() {

    Pixel[][] pixels3X3 = new Pixel[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }

    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(100, 230, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    IGrid p = new ColorTransformation(TransformationType.GREYSCALE)
        .apply(new ImageGrid(pixels3X3, 3, 3));

    Pixel[][] pixelsNew = new Pixel[3][3];

    pixelsNew[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsNew[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsNew[0][2] = new Pixel(new Color(193, 193, 193));

    pixelsNew[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsNew[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsNew[1][2] = new Pixel(new Color(68, 68, 68));

    pixelsNew[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsNew[2][1] = new Pixel(new Color(24, 24, 24));
    pixelsNew[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsNew, p.getPixels());

  }

  @Test
  public void testApplyFilter() {
    Pixel[][] pixels3X3 = new Pixel[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }

    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(100, 230, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    IGrid p = new Filter(FilterType.BLUR).apply(new ImageGrid(pixels3X3, 3, 3));

    Pixel[][] pixelsNew = new Pixel[3][3];

    pixelsNew[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsNew[0][1] = new Pixel(new Color(13, 33, 19));
    pixelsNew[0][2] = new Pixel(new Color(27, 67, 39));

    pixelsNew[1][0] = new Pixel(new Color(0, 1, 6));
    pixelsNew[1][1] = new Pixel(new Color(9, 27, 32));
    pixelsNew[1][2] = new Pixel(new Color(18, 49, 45));

    pixelsNew[2][0] = new Pixel(new Color(0, 2, 13));
    pixelsNew[2][1] = new Pixel(new Color(1, 10, 32));
    pixelsNew[2][2] = new Pixel(new Color(3, 12, 26));

    assertArrayEquals(pixelsNew, p.getPixels());
  }

}

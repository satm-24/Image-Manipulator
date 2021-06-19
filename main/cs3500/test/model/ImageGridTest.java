import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import model.Color;
import model.FilterType;
import model.IGrid;
import model.ImageUtil;
import model.ImageGrid;
import model.SimpleImageModel;
import model.Pixel;
import model.TransformationType;
import model.operations.ColorTransformation;
import model.operations.Filter;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for image grids.
 */
public class ImageGridTest {

  SimpleImageModel p;
  ImageGrid ig;
  SimpleImageModel testModel;

  List<IGrid> imageList;

  Color white;
  Color black;
  Color purple;
  Color lightRed;


  Pixel whitePixel;
  Pixel blackPixel;
  Pixel purplePixel;
  Pixel lightRedPixel;


  Pixel[][] purpleAndLightRedPixels;
  Pixel[][] pixels3X3;
  ImageGrid threeXthreeGrid;
  Pixel[][] pixelsExpected;


  IGrid purpleAndLightRedGrid;


  @Before
  public void setUp() {
    ig = ImageUtil.readPPM("/Users/satwikmisra/CS3500/hw5/Koala.ppm");

    imageList = new ArrayList<>();

    pixelsExpected = new Pixel[3][3];

    pixels3X3 = new Pixel[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }

    threeXthreeGrid = new ImageGrid(pixels3X3, 3, 3);

    p = new SimpleImageModel();

    white = new Color(255, 255, 255);
    black = new Color(0, 0, 0);
    purple = new Color(127, 50, 169);
    lightRed = new Color(238, 63, 74);

    whitePixel = new Pixel(white);
    blackPixel = new Pixel(black);
    purplePixel = new Pixel(purple);
    lightRedPixel = new Pixel(lightRed);

    testModel = new SimpleImageModel();
    purpleAndLightRedPixels = new Pixel[][]{{purplePixel, purplePixel, purplePixel},
        {lightRedPixel, lightRedPixel, lightRedPixel}, {purplePixel, purplePixel, purplePixel}};
    purpleAndLightRedGrid = new ImageGrid(purpleAndLightRedPixels, 3, 3);
    testModel.add(purpleAndLightRedGrid);
  }


  // tests that the colorTransform method works correctly with a 3x3 multicolored image in
  // greyscale. Greyscale transformations cannot be clamped as values never get that high.
  @Test
  public void testGreyscaleTransformNoClamp() {

    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(100, 230, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

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

    assertArrayEquals(pixelsNew, m.getImageAt(1).getPixels());


  }


  // tests that the colorTransform method works correctly with a 3x3 multicolored image in sepia
  // with clamping.
  @Test
  public void testSepiaTransformWithClamping() {

    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(100, 230, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    // originally 260 and 290, should be clamped down to 255
    pixelsExpected[0][2] = new Pixel(new Color(236, 210, 163));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(88, 79, 61));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(37, 33, 26));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());
  }

  // tests that the colorTransform method works correctly with a 3x3 multicolored image in sepia
  // mo clamping.
  @Test
  public void testSepiaTransformNoClamping() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }
    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(10, 23, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(41, 36, 28));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(88, 79, 61));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(37, 33, 26));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());
  }

  // tests that the colorTransform method (sepia) works correctly with a 3x3 image
  // that is all black.
  @Test
  public void testSepiaTransformAllBlackImg() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel(black);
      }
    }

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixelsExpected[i][j] = new Pixel(black);
      }
    }

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

  }


  // tests that the colorTransform method (sepia) works correctly with a 3x3 image that is all
  // white with clamping
  @Test
  public void testSepiaTransformAllWhiteImgWithClamping() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel(white);
      }
    }

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        // clamps the r: 344 and g: 306 to 255
        pixelsExpected[i][j] = new Pixel(new Color(255, 255, 238));
      }
    }

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

  }

  // tests that the colorTransform method (greyscale) works correctly with a 3x3 image that is all
  // black.
  @Test
  public void testGreyscaleTransformAllBlackImg() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel(black);
      }
    }

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixelsExpected[i][j] = new Pixel(black);
      }
    }

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());


  }

  // tests that the colorTransform method works correctly with a 3x3 image that is all white.
  @Test
  public void testGreyscaleTransformAllWhiteImg() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel(white);
      }
    }

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixelsExpected[i][j] = new Pixel(new Color(254, 254, 254));
      }
    }

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

  }

  // tests that the colorTransform method works correctly with a 1x1 image in sepia.
  @Test
  public void testSepiaTransform1By1Img() {

    Pixel[][] pixels1x1 = new Pixel[1][1];

    for (int i = 0; i < 1; i++) {
      for (int j = 0; j < 1; j++) {
        pixels1x1[i][j] = new Pixel(white);
      }
    }

    ImageGrid oneByOnegrid = new ImageGrid(pixels1x1, 1, 1);

    SimpleImageModel m = new SimpleImageModel();

    m.add(oneByOnegrid);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    Pixel[][] expected1x1 = new Pixel[1][1];

    // clamped
    expected1x1[0][0] = new Pixel(new Color(255, 255, 238));

    assertArrayEquals(expected1x1, m.getImageAt(1).getPixels());

  }

  // tests that the colorTransform method works correctly with a 1x1 image in greyscale.
  @Test
  public void testGreyscaleTransform1By1Img() {

    Pixel[][] pixels1x1 = new Pixel[1][1];

    pixels1x1[0][0] = new Pixel(new Color(100, 255, 0));

    ImageGrid oneByOnegrid = new ImageGrid(pixels1x1, 1, 1);

    SimpleImageModel m = new SimpleImageModel();

    m.add(oneByOnegrid);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    Pixel[][] expected1x1 = new Pixel[1][1];

    // clamped
    expected1x1[0][0] = new Pixel(new Color(203, 203, 203));

    assertArrayEquals(expected1x1, m.getImageAt(1).getPixels());

  }

  // tests that the colorTransform method works correctly with a 0x0 image in sepia.
  @Test
  public void testSepiaTransform0By0Img() {

    Pixel[][] pixels0x0 = new Pixel[0][0];

    ImageGrid zeroByzeroGrid = new ImageGrid(pixels0x0, 0, 0);

    SimpleImageModel m = new SimpleImageModel();

    m.add(zeroByzeroGrid);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    Pixel[][] expected = new Pixel[0][0];

    assertArrayEquals(expected, m.getImageAt(1).getPixels());

  }

  // tests that the colorTransform method works correctly with a 0x0 image in greyscale.
  @Test
  public void testGreyscaleTransform0By0Img() {
    Pixel[][] pixels0x0 = new Pixel[0][0];

    ImageGrid zeroByzeroGrid = new ImageGrid(pixels0x0, 0, 0);

    SimpleImageModel m = new SimpleImageModel();

    m.add(zeroByzeroGrid);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    Pixel[][] expected = new Pixel[0][0];

    assertArrayEquals(expected, m.getImageAt(1).getPixels());
  }

  // tests that the colorTransform method works correctly for multiple greyscale transformations
  // (doesn't change values).
  @Test
  public void testMultipleGreyscaleTransformations3x3() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }
    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(10, 23, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(26, 26, 26));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(68, 68, 68));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(24, 24, 24));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

    // these have already been transformed once, shouldn't change

    Pixel[][] pixels3x3Rev2 = new Pixel[3][3];

    pixels3x3Rev2[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][2] = new Pixel(new Color(26, 26, 26));

    pixels3x3Rev2[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][2] = new Pixel(new Color(68, 68, 68));

    pixels3x3Rev2[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[2][1] = new Pixel(new Color(24, 24, 24));
    pixels3x3Rev2[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid2 = new ImageGrid(pixels3x3Rev2, 3, 3);

    m.add(grid2);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(25, 25, 25));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(68, 68, 68));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(24, 24, 24));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(3).getPixels());

    // second transformation, values shouldnt change again

    Pixel[][] pixels3x3Rev3 = new Pixel[3][3];

    pixels3x3Rev3[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[0][2] = new Pixel(new Color(25, 25, 25));

    pixels3x3Rev3[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[1][2] = new Pixel(new Color(68, 68, 68));

    pixels3x3Rev3[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[2][1] = new Pixel(new Color(24, 24, 24));
    pixels3x3Rev3[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid3 = new ImageGrid(pixels3x3Rev3, 3, 3);

    m.add(grid3);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(25, 25, 25));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(68, 68, 68));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(24, 24, 24));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(5).getPixels());

  }

  // tests that the colorTransform method works correctly for multiple sepia transformations.
  @Test
  public void testMultipleSepiaTransformations3x3() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }
    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(10, 23, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(41, 36, 28));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(88, 79, 61));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(37, 33, 26));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

    // these have already been transformed once

    Pixel[][] pixels3x3Rev2 = new Pixel[3][3];

    pixels3x3Rev2[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][2] = new Pixel(new Color(41, 36, 28));

    pixels3x3Rev2[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][2] = new Pixel(new Color(88, 79, 61));

    pixels3x3Rev2[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[2][1] = new Pixel(new Color(37, 33, 26));
    pixels3x3Rev2[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid2 = new ImageGrid(pixels3x3Rev2, 3, 3);

    m.add(grid2);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(49, 43, 34));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(106, 95, 74));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(44, 39, 31));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(3).getPixels());

    // second transformation

    Pixel[][] pixels3x3Rev3 = new Pixel[3][3];

    pixels3x3Rev3[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[0][2] = new Pixel(new Color(49, 43, 34));

    pixels3x3Rev3[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[1][2] = new Pixel(new Color(106, 95, 74));

    pixels3x3Rev3[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[2][1] = new Pixel(new Color(44, 33, 26));
    pixels3x3Rev3[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid3 = new ImageGrid(pixels3x3Rev3, 3, 3);

    m.add(grid3);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(58, 52, 40));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(128, 114, 89));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(47, 42, 32));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(5).getPixels());
  }

  // test greyscale and sepia transformation on same image
  @Test
  public void testGreyscaleThenSepia3x3() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }
    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(10, 23, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(26, 26, 26));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(68, 68, 68));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(24, 24, 24));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

    // these have already been transformed once

    Pixel[][] pixels3x3Rev2 = new Pixel[3][3];

    pixels3x3Rev2[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][2] = new Pixel(new Color(26, 26, 26));

    pixels3x3Rev2[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][2] = new Pixel(new Color(68, 68, 68));

    pixels3x3Rev2[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[2][1] = new Pixel(new Color(24, 24, 24));
    pixels3x3Rev2[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid2 = new ImageGrid(pixels3x3Rev2, 3, 3);

    m.add(grid2);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(35, 31, 24));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(91, 81, 63));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(32, 28, 22));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(3).getPixels());


  }


  // test greyscale and sepia transformation on same image
  @Test
  public void testSepiaThenGreyscale3x3() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }

    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(10, 23, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(41, 36, 28));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(88, 79, 61));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(37, 33, 26));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

    // these have already been transformed once

    Pixel[][] pixels3x3Rev2 = new Pixel[3][3];

    pixels3x3Rev2[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][2] = new Pixel(new Color(41, 36, 28));

    pixels3x3Rev2[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][2] = new Pixel(new Color(88, 79, 61));

    pixels3x3Rev2[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[2][1] = new Pixel(new Color(37, 33, 26));
    pixels3x3Rev2[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid2 = new ImageGrid(pixels3x3Rev2, 3, 3);

    m.add(grid2);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][2] = new Pixel(new Color(36, 36, 36));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][2] = new Pixel(new Color(79, 79, 79));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(33, 33, 33));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsExpected, m.getImageAt(3).getPixels());


  }


  // test greyscale on a 2x2 image
  @Test
  public void testTransformationEvenDimensions() {

    Pixel[][] pixels2x2 = new Pixel[2][2];

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        pixels2x2[i][j] = new Pixel(black);
      }
    }

    // set some misc. colors
    pixels2x2[1][1] = new Pixel(new Color(1, 23, 105));
    pixels2x2[0][0] = new Pixel(new Color(100, 230, 105));

    SimpleImageModel m = new SimpleImageModel();

    ImageGrid twoBytwoGrid = new ImageGrid(pixels2x2, 2, 2);

    m.add(twoBytwoGrid);

    m.operate(new ColorTransformation(TransformationType.GREYSCALE));

    Pixel[][] pixelsNew = new Pixel[2][2];

    pixelsNew[0][0] = new Pixel(new Color(193, 193, 193));
    pixelsNew[0][1] = new Pixel(new Color(0, 0, 0));

    pixelsNew[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsNew[1][1] = new Pixel(new Color(24, 24, 24));

    assertArrayEquals(pixelsNew, m.getImageAt(1).getPixels());
  }

  // test sepia on a 2x3 image (height x width)
  @Test
  public void testTransformationMismatchedDimensions() {
    Pixel[][] pixels2x3 = new Pixel[2][3];

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        pixels2x3[i][j] = new Pixel(black);
      }
    }

    // set some misc. colors
    pixels2x3[1][1] = new Pixel(new Color(1, 23, 105));
    pixels2x3[0][0] = new Pixel(new Color(100, 230, 105));

    SimpleImageModel m = new SimpleImageModel();

    ImageGrid twoBy3Grid = new ImageGrid(pixels2x3, 3, 2);

    m.add(twoBy3Grid);

    m.operate(new ColorTransformation(TransformationType.SEPIA));

    Pixel[][] pixelsNew = new Pixel[2][3];

    pixelsNew[0][0] = new Pixel(new Color(236, 210, 163));
    pixelsNew[0][1] = new Pixel(new Color(0, 0, 0));
    pixelsNew[0][2] = new Pixel(new Color(0, 0, 0));

    pixelsNew[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsNew[1][1] = new Pixel(new Color(37, 33, 26));
    pixelsNew[1][2] = new Pixel(new Color(0, 0, 0));

    assertArrayEquals(pixelsNew, m.getImageAt(1).getPixels());
  }
  /*






  break


   */

  @Test
  public void testBlurNoClamp() {

    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(100, 230, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.BLUR));

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

    assertArrayEquals(pixelsNew, m.getImageAt(1).getPixels());


  }


  @Test
  public void testSharpenNoClamp() {

    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(100, 230, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.SHARPEN));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(30, 74, 39));
    // originally 260 and 290, should be clamped down to 255
    pixelsExpected[0][2] = new Pixel(new Color(105, 246, 118));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(31, 82, 78));
    pixelsExpected[1][2] = new Pixel(new Color(48, 141, 157));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(0, 13, 118));
    pixelsExpected[2][2] = new Pixel(new Color(0, 0, 39));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());
  }


  // tests that the colorTransform method (sepia) works correctly with a 3x3 image
  // that is all black.
  @Test
  public void testBlurAllBlackImg() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel(black);
      }
    }

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.BLUR));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixelsExpected[i][j] = new Pixel(black);
      }
    }

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

  }


  // tests that the colorTransform method (sepia) works correctly with a 3x3 image that is all
  // white with clamping
  @Test
  public void testSharpenAllWhiteImg() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel(white);
      }
    }

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.SHARPEN));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        // clamps the r: 344 and g: 306 to 255
        pixelsExpected[i][j] = new Pixel(new Color(255, 255, 255));
      }
    }

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

  }

  // tests that the colorTransform method (greyscale) works correctly with a 3x3 image that is all
  // black.
  @Test
  public void testSharpenAllBlackImg() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel(black);
      }
    }

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.SHARPEN));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixelsExpected[i][j] = new Pixel(black);
      }
    }

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());


  }

  // tests that the colorTransform method works correctly with a 3x3 image that is all white.
  @Test
  public void testBlurAllWhiteImgWithClamping() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel(white);
      }
    }

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.BLUR));

    pixelsExpected[0][0] = new Pixel(new Color(143, 143, 143));
    pixelsExpected[0][1] = new Pixel(new Color(191, 191, 191));
    pixelsExpected[0][2] = new Pixel(new Color(143, 143, 143));

    pixelsExpected[1][0] = new Pixel(new Color(191, 191, 191));
    pixelsExpected[1][1] = new Pixel(new Color(255, 255, 255));
    pixelsExpected[1][2] = new Pixel(new Color(191, 191, 191));

    pixelsExpected[2][0] = new Pixel(new Color(143, 143, 143));
    pixelsExpected[2][1] = new Pixel(new Color(191, 191, 191));
    pixelsExpected[2][2] = new Pixel(new Color(143, 143, 143));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

  }

  // tests that the colorTransform method works correctly with a 1x1 image in sepia.
  @Test
  public void testBlur1By1Img() {

    Pixel[][] pixels1x1 = new Pixel[1][1];

    for (int i = 0; i < 1; i++) {
      for (int j = 0; j < 1; j++) {
        pixels1x1[i][j] = new Pixel(white);
      }
    }

    ImageGrid oneByOnegrid = new ImageGrid(pixels1x1, 1, 1);

    SimpleImageModel m = new SimpleImageModel();

    m.add(oneByOnegrid);

    m.operate(new Filter(FilterType.BLUR));

    Pixel[][] expected1x1 = new Pixel[1][1];

    // clamped
    expected1x1[0][0] = new Pixel(new Color(63, 63, 63));

    assertArrayEquals(expected1x1, m.getImageAt(1).getPixels());

  }

  // tests that the colorTransform method works correctly with a 0x0 image in sepia.
  @Test
  public void testSharpen0By0Img() {

    Pixel[][] pixels0x0 = new Pixel[0][0];

    ImageGrid zeroByzeroGrid = new ImageGrid(pixels0x0, 0, 0);

    SimpleImageModel m = new SimpleImageModel();

    m.add(zeroByzeroGrid);

    m.operate(new Filter(FilterType.SHARPEN));

    Pixel[][] expected = new Pixel[0][0];

    assertArrayEquals(expected, m.getImageAt(1).getPixels());

  }


  // tests that the colorTransform method works correctly for multiple greyscale transformations
  // (doesn't change values).
  @Test
  public void testMultipleBlurs3x3() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }
    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(10, 23, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.BLUR));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(2, 7, 19));
    pixelsExpected[0][2] = new Pixel(new Color(5, 15, 39));

    pixelsExpected[1][0] = new Pixel(new Color(0, 1, 6));
    pixelsExpected[1][1] = new Pixel(new Color(3, 14, 32));
    pixelsExpected[1][2] = new Pixel(new Color(7, 23, 45));

    pixelsExpected[2][0] = new Pixel(new Color(0, 2, 13));
    pixelsExpected[2][1] = new Pixel(new Color(1, 10, 32));
    pixelsExpected[2][2] = new Pixel(new Color(3, 12, 26));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

    // these have already been transformed once, shouldn't change

    Pixel[][] pixels3x3Rev2 = new Pixel[3][3];

    pixels3x3Rev2[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][2] = new Pixel(new Color(26, 26, 26));

    pixels3x3Rev2[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][2] = new Pixel(new Color(68, 68, 68));

    pixels3x3Rev2[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[2][1] = new Pixel(new Color(24, 24, 24));
    pixels3x3Rev2[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid2 = new ImageGrid(pixels3x3Rev2, 3, 3);

    m.add(grid2);

    m.operate(new Filter(FilterType.BLUR));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(7, 7, 7));
    pixelsExpected[0][2] = new Pixel(new Color(15, 15, 15));

    pixelsExpected[1][0] = new Pixel(new Color(1, 1, 1));
    pixelsExpected[1][1] = new Pixel(new Color(13, 13, 13));
    pixelsExpected[1][2] = new Pixel(new Color(21, 21, 21));

    pixelsExpected[2][0] = new Pixel(new Color(3, 3, 3));
    pixelsExpected[2][1] = new Pixel(new Color(10, 10, 10));
    pixelsExpected[2][2] = new Pixel(new Color(11, 11, 11));

    assertArrayEquals(pixelsExpected, m.getImageAt(3).getPixels());

    // second transformation, values shouldnt change again

    Pixel[][] pixels3x3Rev3 = new Pixel[3][3];

    pixels3x3Rev3[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[0][2] = new Pixel(new Color(25, 25, 25));

    pixels3x3Rev3[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[1][2] = new Pixel(new Color(68, 68, 68));

    pixels3x3Rev3[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[2][1] = new Pixel(new Color(24, 24, 24));
    pixels3x3Rev3[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid3 = new ImageGrid(pixels3x3Rev3, 3, 3);

    m.add(grid3);

    m.operate(new Filter(FilterType.BLUR));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(7, 7, 7));
    pixelsExpected[0][2] = new Pixel(new Color(14, 14, 14));

    pixelsExpected[1][0] = new Pixel(new Color(1, 1, 1));
    pixelsExpected[1][1] = new Pixel(new Color(13, 13, 13));
    pixelsExpected[1][2] = new Pixel(new Color(21, 21, 21));

    pixelsExpected[2][0] = new Pixel(new Color(3, 3, 3));
    pixelsExpected[2][1] = new Pixel(new Color(10, 10, 10));
    pixelsExpected[2][2] = new Pixel(new Color(11, 11, 11));

    assertArrayEquals(pixelsExpected, m.getImageAt(5).getPixels());

  }

  // tests that the colorTransform method works correctly for multiple sepia transformations.
  @Test
  public void testMultipleSharpen3x3() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }
    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(10, 23, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.SHARPEN));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(8, 22, 39));
    pixelsExpected[0][2] = new Pixel(new Color(15, 39, 118));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(8, 31, 78));
    pixelsExpected[1][2] = new Pixel(new Color(25, 89, 157));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(5, 39, 118));
    pixelsExpected[2][2] = new Pixel(new Color(4, 22, 39));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

    // these have already been transformed once

    Pixel[][] pixels3x3Rev2 = new Pixel[3][3];

    pixels3x3Rev2[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][2] = new Pixel(new Color(41, 36, 28));

    pixels3x3Rev2[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][2] = new Pixel(new Color(88, 79, 61));

    pixels3x3Rev2[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[2][1] = new Pixel(new Color(37, 33, 26));
    pixels3x3Rev2[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid2 = new ImageGrid(pixels3x3Rev2, 3, 3);

    m.add(grid2);

    m.operate(new Filter(FilterType.SHARPEN));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(27, 24, 19));
    pixelsExpected[0][2] = new Pixel(new Color(58, 51, 40));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(41, 37, 28));
    pixelsExpected[1][2] = new Pixel(new Color(107, 96, 74));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(53, 48, 37));
    pixelsExpected[2][2] = new Pixel(new Color(26, 23, 18));

    assertArrayEquals(pixelsExpected, m.getImageAt(3).getPixels());

    // second transformation

    Pixel[][] pixels3x3Rev3 = new Pixel[3][3];

    pixels3x3Rev3[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[0][2] = new Pixel(new Color(49, 43, 34));

    pixels3x3Rev3[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[1][2] = new Pixel(new Color(106, 95, 74));

    pixels3x3Rev3[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev3[2][1] = new Pixel(new Color(44, 33, 26));
    pixels3x3Rev3[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid3 = new ImageGrid(pixels3x3Rev3, 3, 3);

    m.add(grid3);

    m.operate(new Filter(FilterType.SHARPEN));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(33, 30, 23));
    pixelsExpected[0][2] = new Pixel(new Color(70, 62, 49));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(49, 42, 33));
    pixelsExpected[1][2] = new Pixel(new Color(129, 114, 89));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(64, 51, 40));
    pixelsExpected[2][2] = new Pixel(new Color(31, 26, 20));

    assertArrayEquals(pixelsExpected, m.getImageAt(5).getPixels());
  }


  @Test
  public void testBlurThenSharpen3x3() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }
    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(10, 23, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.BLUR));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(2, 7, 19));
    pixelsExpected[0][2] = new Pixel(new Color(5, 15, 39));

    pixelsExpected[1][0] = new Pixel(new Color(0, 1, 6));
    pixelsExpected[1][1] = new Pixel(new Color(3, 14, 32));
    pixelsExpected[1][2] = new Pixel(new Color(7, 23, 45));

    pixelsExpected[2][0] = new Pixel(new Color(0, 2, 13));
    pixelsExpected[2][1] = new Pixel(new Color(1, 10, 32));
    pixelsExpected[2][2] = new Pixel(new Color(3, 12, 26));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

    // these have already been transformed once

    Pixel[][] pixels3x3Rev2 = new Pixel[3][3];

    pixels3x3Rev2[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][2] = new Pixel(new Color(26, 26, 26));

    pixels3x3Rev2[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][2] = new Pixel(new Color(68, 68, 68));

    pixels3x3Rev2[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[2][1] = new Pixel(new Color(24, 24, 24));
    pixels3x3Rev2[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid2 = new ImageGrid(pixels3x3Rev2, 3, 3);

    m.add(grid2);

    m.operate(new Filter(FilterType.SHARPEN));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(20, 20, 20));
    pixelsExpected[0][2] = new Pixel(new Color(40, 40, 40));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(29, 29, 29));
    pixelsExpected[1][2] = new Pixel(new Color(80, 80, 80));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(37, 37, 37));
    pixelsExpected[2][2] = new Pixel(new Color(19, 19, 19));

    assertArrayEquals(pixelsExpected, m.getImageAt(3).getPixels());


  }


  // test greyscale and sepia transformation on same image
  @Test
  public void testSharpenThenBlur3x3() {

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixels3X3[i][j] = new Pixel();
      }
    }

    // set some misc. colors
    pixels3X3[2][1] = new Pixel(new Color(1, 23, 105));
    pixels3X3[0][2] = new Pixel(new Color(10, 23, 105));
    pixels3X3[1][2] = new Pixel(new Color(23, 78, 105));

    SimpleImageModel m = new SimpleImageModel();

    m.add(threeXthreeGrid);

    m.operate(new Filter(FilterType.SHARPEN));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(8, 22, 39));
    pixelsExpected[0][2] = new Pixel(new Color(15, 39, 118));

    pixelsExpected[1][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[1][1] = new Pixel(new Color(8, 31, 78));
    pixelsExpected[1][2] = new Pixel(new Color(25, 89, 157));

    pixelsExpected[2][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[2][1] = new Pixel(new Color(5, 39, 118));
    pixelsExpected[2][2] = new Pixel(new Color(4, 22, 39));

    assertArrayEquals(pixelsExpected, m.getImageAt(1).getPixels());

    // these have already been transformed once

    Pixel[][] pixels3x3Rev2 = new Pixel[3][3];

    pixels3x3Rev2[0][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[0][2] = new Pixel(new Color(41, 36, 28));

    pixels3x3Rev2[1][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][1] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[1][2] = new Pixel(new Color(88, 79, 61));

    pixels3x3Rev2[2][0] = new Pixel(new Color(0, 0, 0));
    pixels3x3Rev2[2][1] = new Pixel(new Color(37, 33, 26));
    pixels3x3Rev2[2][2] = new Pixel(new Color(0, 0, 0));

    ImageGrid grid2 = new ImageGrid(pixels3x3Rev2, 3, 3);

    m.add(grid2);

    m.operate(new Filter(FilterType.BLUR));

    pixelsExpected[0][0] = new Pixel(new Color(0, 0, 0));
    pixelsExpected[0][1] = new Pixel(new Color(10, 9, 7));
    pixelsExpected[0][2] = new Pixel(new Color(21, 18, 14));

    pixelsExpected[1][0] = new Pixel(new Color(2, 2, 1));
    pixelsExpected[1][1] = new Pixel(new Color(18, 16, 12));
    pixelsExpected[1][2] = new Pixel(new Color(29, 26, 20));

    pixelsExpected[2][0] = new Pixel(new Color(4, 4, 3));
    pixelsExpected[2][1] = new Pixel(new Color(14, 13, 10));
    pixelsExpected[2][2] = new Pixel(new Color(15, 14, 10));

    assertArrayEquals(pixelsExpected, m.getImageAt(3).getPixels());


  }


  // test greyscale on a 2x2 image
  @Test
  public void testSharpenEvenDimensions() {

    Pixel[][] pixels2x2 = new Pixel[2][2];

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        pixels2x2[i][j] = new Pixel(black);
      }
    }

    // set some misc. colors
    pixels2x2[1][1] = new Pixel(new Color(1, 23, 105));
    pixels2x2[0][0] = new Pixel(new Color(100, 230, 105));

    SimpleImageModel m = new SimpleImageModel();

    ImageGrid twoBytwoGrid = new ImageGrid(pixels2x2, 2, 2);

    m.add(twoBytwoGrid);

    m.operate(new Filter(FilterType.SHARPEN));

    Pixel[][] pixelsNew = new Pixel[2][2];

    pixelsNew[0][0] = new Pixel(new Color(100, 235, 131));
    pixelsNew[0][1] = new Pixel(new Color(25, 63, 52));

    pixelsNew[1][0] = new Pixel(new Color(25, 63, 52));
    pixelsNew[1][1] = new Pixel(new Color(26, 80, 131));

    assertArrayEquals(pixelsNew, m.getImageAt(1).getPixels());
  }

  @Test
  public void testBlurMismatchedDimensions() {
    Pixel[][] pixels2x3 = new Pixel[2][3];

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        pixels2x3[i][j] = new Pixel(black);
      }
    }

    // set some misc. colors
    pixels2x3[1][1] = new Pixel(new Color(1, 23, 105));
    pixels2x3[0][0] = new Pixel(new Color(100, 230, 105));

    SimpleImageModel m = new SimpleImageModel();

    ImageGrid twoBy3Grid = new ImageGrid(pixels2x3, 3, 2);

    m.add(twoBy3Grid);

    m.operate(new Filter(FilterType.BLUR));

    Pixel[][] pixelsNew = new Pixel[2][3];

    pixelsNew[0][0] = new Pixel(new Color(25, 58, 32));
    pixelsNew[0][1] = new Pixel(new Color(12, 31, 26));
    pixelsNew[0][2] = new Pixel(new Color(0, 1, 6));

    pixelsNew[1][0] = new Pixel(new Color(12, 31, 26));
    pixelsNew[1][1] = new Pixel(new Color(6, 20, 32));
    pixelsNew[1][2] = new Pixel(new Color(0, 2, 13));

    assertArrayEquals(pixelsNew, m.getImageAt(1).getPixels());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullColorTransform() {
    ig.colorTransform(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullFilter() {
    ig.filter(null);
  }

  @Test
  public void testGetPixels() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        pixelsExpected[i][j] = new Pixel();
      }
    }

    assertArrayEquals(new ImageGrid(pixelsExpected, 3, 3).getPixels(),
        new ImageGrid(pixels3X3, 3, 3).getPixels());

  }

  @Test
  public void testCopyGrid() {

    IGrid expected = new ImageGrid(pixels3X3, 3, 3).copyGrid();

    assertEquals(expected, new ImageGrid(pixels3X3, 3, 3));

  }

  @Test
  public void testConvertGridToPPM() {
    String s = new ImageGrid(pixels3X3, 3, 3).convertGridToPPM();

    StringBuilder expected = new StringBuilder();

    expected.append("P3\n");

    expected.append(3).append(" ").append(3).append("\n");

    expected.append("255\n");

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {

        expected.append(pixels3X3[i][j].convertRGBToStr());
      }
    }

    assertEquals(expected.toString(), s);

  }

  @Test
  public void testCalcNeighboringSum0() {

    ImageGrid g = new ImageGrid(pixels3X3, 3, 3);

    double[][] testKern = new double[2][2];

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        testKern[i][j] = 0;
      }
    }

    assertEquals(new Pixel(new Color(0, 0, 0)),
        g.calcNeighboringSum(pixels3X3, testKern, 0, 0));

  }


}

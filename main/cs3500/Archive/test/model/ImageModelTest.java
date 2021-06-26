package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.creators.CreateCheckerboard;
import model.creators.ICreator;
import model.operations.ColorTransformation;
import model.operations.Filter;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the SimpleImageModel class.
 */
public class ImageModelTest {

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


  Pixel[][] pixelGrid1x1;
  Pixel[][] pixelGrid4x4;
  Pixel[][] pixelGrid3x3;
  Pixel[][] pixelGrid1x3;


  Pixel[][] purpleAndLightRedPixels;

  IGrid empty4x4;
  IGrid checkerBoard1x1;
  IGrid checkerBoard4x4;
  IGrid checkerBoard3x3;


  IGrid purpleAndLightRedGrid;

  ICreator createBoard1x1;
  ICreator createBoard2x2;
  ICreator createBoard1x3;

  @Before
  public void setup() {

    ig = ImageUtil.readPPM("/Users/satwikmisra/CS3500/hw5/Koala.ppm");

    imageList = new ArrayList<>();

    p = new SimpleImageModel();

    white = new Color(255, 255, 255);
    black = new Color(0, 0, 0);
    purple = new Color(127, 50, 169);
    lightRed = new Color(238, 63, 74);

    whitePixel = new Pixel(white);
    blackPixel = new Pixel(black);
    purplePixel = new Pixel(purple);
    lightRedPixel = new Pixel(lightRed);

    empty4x4 = new ImageGrid(new Pixel[4][4], 4, 4);
    pixelGrid1x1 = new Pixel[][]{{whitePixel}};
    pixelGrid1x3 = new Pixel[][]{{whitePixel, blackPixel, whitePixel}};
    pixelGrid4x4 = new Pixel[][]{{whitePixel, whitePixel, blackPixel, blackPixel},
        {whitePixel, whitePixel, blackPixel, blackPixel},
        {blackPixel, blackPixel, whitePixel, whitePixel},
        {blackPixel, blackPixel, whitePixel, whitePixel}};
    pixelGrid3x3 = new Pixel[][]{{whitePixel, blackPixel, whitePixel}, {blackPixel, whitePixel,
        blackPixel}, {whitePixel, blackPixel, whitePixel}};

    checkerBoard1x1 = new ImageGrid(pixelGrid1x1, 1, 1);
    checkerBoard4x4 = new ImageGrid(pixelGrid4x4, 4, 4);
    checkerBoard3x3 = new ImageGrid(pixelGrid3x3, 3, 3);

    testModel = new SimpleImageModel();
    purpleAndLightRedPixels = new Pixel[][]{{purplePixel, purplePixel, purplePixel},
        {lightRedPixel, lightRedPixel, lightRedPixel}, {purplePixel, purplePixel, purplePixel}};
    purpleAndLightRedGrid = new ImageGrid(purpleAndLightRedPixels, 3, 3);
    testModel.add(purpleAndLightRedGrid);

    createBoard1x1 = new CreateCheckerboard(1, 1, new ArrayList<>(Arrays.asList(
        white, black)));
    createBoard2x2 = new CreateCheckerboard(2, 2, new ArrayList<>(Arrays.asList(white,
        black)));
    createBoard1x3 = new CreateCheckerboard(1, 3, new ArrayList<>(Arrays.asList(white,
        black)));


  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorTransformNullType() {
    p.operate(new ColorTransformation(null));
  }


  @Test
  public void testCreateCheckerBoard1x1() {
    assertEquals(checkerBoard1x1, p.create(createBoard1x1));
  }

  @Test
  public void testCreateCheckerBoard2x2() {
    assertEquals(checkerBoard4x4, p.create(createBoard2x2));
  }

  @Test
  public void testOperateBlur() {

    assertEquals(0, p.getImagesSize());

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

    p.add(new ImageGrid(pixelsNew, 3, 3));

    p.operate(new Filter(FilterType.BLUR));

    assertEquals(2, p.getImagesSize());


  }

  @Test
  public void testOperateGreyscaleTransform() {

    assertEquals(0, p.getImagesSize());

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

    p.add(new ImageGrid(pixelsNew, 3, 3));

    p.operate(new Filter(FilterType.BLUR));

    p.operate(new ColorTransformation(TransformationType.GREYSCALE));

    assertEquals(3, p.getImagesSize());

  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullCreator() {
    p.create(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullOperator() {
    p.operate(null);
  }
}



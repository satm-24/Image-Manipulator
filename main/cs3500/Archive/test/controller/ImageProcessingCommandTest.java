package controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import model.Color;
import model.IGrid;
import model.ImageGrid;
import model.ImageUtil;
import model.Pixel;
import model.SimpleImageModel;
import model.creators.CreateCheckerboard;
import org.junit.Before;
import org.junit.Test;
import view.ILayer;
import view.IProcessingImageView;
import view.Layer;
import view.SimpleProcessingImageView;

/**
 * Tests for the command interface.
 */
public class ImageProcessingCommandTest {

  ArrayList<ILayer> empty;
  ILayer blankLayer;
  SimpleImageModel model;
  IGrid checkerboard2x2;
  Appendable app;
  IProcessingImageView view;
  BufferedImage inJPEG;
  BufferedImage inPNG;

  @Before
  public void setUp() throws IOException {
    empty = new ArrayList<ILayer>();

    model = new SimpleImageModel();

    blankLayer = new Layer(true, "first");

    checkerboard2x2 = new CreateCheckerboard(10, 2,
        new ArrayList<Color>(Arrays.asList(new Color(255, 255, 255), new Color(0, 0, 0)))).apply();

    app = new StringBuffer();

    view = new SimpleProcessingImageView(app);

    inJPEG = ImageIO.read(new File("blankSquare.jpg"));
    inPNG = ImageIO.read(new File("blankSquare.png"));

    for (int x = 0; x < inJPEG.getWidth(); x++) {
      for (int y = 0; y < inJPEG.getHeight(); y++) {
        inJPEG.setRGB(x, y, new Color(255, 255, 255).getRGB());
      }
    }

    for (int x = 0; x < inPNG.getWidth(); x++) {
      for (int y = 0; y < inPNG.getHeight(); y++) {
        inPNG.setRGB(x, y, new Color(255, 255, 255).getRGB());
      }
    }

  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerNullModel() {
    ImageProcessingCommand add = new AddLayer("test");
    add.execute(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNullModel() {
    ImageProcessingCommand remove = new RemoveLayer();
    remove.execute(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurLayerNullModel() {
    ImageProcessingCommand blur = new BlurImage();
    blur.execute(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenLayerNullModel() {
    ImageProcessingCommand sharpen = new SharpenImage();
    sharpen.execute(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaLayerNullModel() {
    ImageProcessingCommand sepia = new TransformSepia();
    sepia.execute(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleLayerNullModel() {
    ImageProcessingCommand greyscale = new TransformGreyscale();
    greyscale.execute(null, null);
  }

  @Test
  public void testAddLayer() {
    Readable read = new StringReader("add test");
    assertEquals(new ArrayList<ILayer>(), empty);
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read,
        app);

    controller.parseInput();
    ILayer testLayer = new Layer(true, "test");

    assertNotEquals(new ArrayList<ILayer>(), empty);
  }

  @Test
  public void testBlurLayer() {
    Readable read = new StringReader("blur");
    List<Color> colors = new ArrayList<Color>(Arrays.asList(new Color(255, 255, 255),
        new Color(0, 0, 0)));
    IGrid checkerboard2x2 = new CreateCheckerboard(10, 2, colors).apply();
    model.add(checkerboard2x2);
    IProcessingController controller = new SimpleImageProcessingController(model,
        new ArrayList<ILayer>(Arrays.asList(new Layer(true, checkerboard2x2, "first"))), read,
        app);
    assertEquals(checkerboard2x2, controller.getCurrent().getImage());
    controller.parseInput();
    assertNotEquals(checkerboard2x2, controller.getCurrent().getImage());
  }

  @Test
  public void testSharpenLayer() {
    Readable read = new StringReader("sharpen");
    List<Color> colors = new ArrayList<Color>(Arrays.asList(new Color(255, 255, 255),
        new Color(0, 0, 0)));
    IGrid checkerboard2x2 = new CreateCheckerboard(10, 2, colors).apply();
    model.add(checkerboard2x2);
    IProcessingController controller = new SimpleImageProcessingController(model,
        new ArrayList<ILayer>(Arrays.asList(new Layer(true, checkerboard2x2, "first"))), read,
        app);
    assertEquals(checkerboard2x2, controller.getCurrent().getImage());
    controller.parseInput();
    IGrid result = controller.getCurrent().getImage();
    assertNotEquals(checkerboard2x2, result);
  }

  @Test
  public void testSepiaLayer() {
    Readable read = new StringReader("sepia");
    List<Color> colors = new ArrayList<Color>(Arrays.asList(new Color(255, 255, 255),
        new Color(0, 0, 0)));
    IGrid checkerboard2x2 = new CreateCheckerboard(10, 2, colors).apply();
    model.add(checkerboard2x2);
    IProcessingController controller = new SimpleImageProcessingController(model,
        new ArrayList<ILayer>(Arrays.asList(new Layer(true, checkerboard2x2, "first"))), read,
        app);
    assertEquals(checkerboard2x2, controller.getCurrent().getImage());
    controller.parseInput();
    IGrid result = controller.getCurrent().getImage();
    assertNotEquals(checkerboard2x2, result);
  }

  @Test
  public void testGreyscaleLayer() {

    Readable read = new StringReader("greyscale");
    List<Color> colors = new ArrayList<Color>(Arrays.asList(new Color(255, 255, 255),
        new Color(0, 0, 0)));
    IGrid checkerboard2x2 = new CreateCheckerboard(10, 2, colors).apply();
    model.add(checkerboard2x2);
    IProcessingController controller = new SimpleImageProcessingController(model,
        new ArrayList<ILayer>(Arrays.asList(new Layer(true, checkerboard2x2, "first"))), read,
        app);
    assertEquals(checkerboard2x2, controller.getCurrent().getImage());
    controller.parseInput();
    IGrid result = controller.getCurrent().getImage();
    assertNotEquals(checkerboard2x2, result);
  }

  @Test
  public void testLoadImageJPEG() {

    Pixel[][] pixels = new Pixel[221][234];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = new Pixel(new Color(0, 0, 0));
      }
    }

    List<ILayer> layers = new ArrayList<>();

    ImageGrid redSquare = new ImageGrid(pixels, pixels[0].length, pixels.length);

    layers.add(new Layer(true, redSquare, "first"));

    Readable read = new StringReader("load test.jpg");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model,
        layers, read, app);

    assertEquals(new Pixel(new Color(0, 0, 0)),
        controller.getLayers().get(0).getImage().getPixels()[0][0]);

    controller.parseInput();

    assertEquals(1, layers.size());

    for (int i = 0; i < controller.getLayers().get(0).getImage().getPixels().length; i++) {
      for (int j = 0; j < controller.getLayers().get(0).getImage().getPixels()[0].length; j++) {
        assertEquals(new Pixel(new Color(254, 0, 0)),
            controller.getLayers().get(0).getImage().getPixels()[i][j]);
      }
    }

  }

  @Test
  public void testLoadImagePNG() {

    Pixel[][] pixels = new Pixel[221][234];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = new Pixel(new Color(0, 0, 0));
      }
    }

    List<ILayer> layers = new ArrayList<>();

    ImageGrid blackSquare = new ImageGrid(pixels, pixels[0].length, pixels.length);

    layers.add(new Layer(true, blackSquare, "first"));

    Readable read = new StringReader("load redSquare.png");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model,
        layers, read, app);

    assertEquals(new Pixel(new Color(0, 0, 0)),
        controller.getLayers().get(0).getImage().getPixels()[0][0]);

    controller.parseInput();

    assertEquals(1, layers.size());

    for (int i = 0; i < controller.getLayers().get(0).getImage().getPixels().length; i++) {
      for (int j = 0; j < controller.getLayers().get(0).getImage().getPixels()[0].length; j++) {
        assertEquals(new Pixel(new Color(255, 0, 0)),
            controller.getLayers().get(0).getImage().getPixels()[i][j]);
      }
    }

  }


  @Test
  public void testSaveImageJPEG() throws IOException {

    Pixel[][] pixels = new Pixel[221][234];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = new Pixel(new Color(254, 0, 0));
      }
    }

    List<ILayer> layers = new ArrayList<>();

    ImageGrid redSquare = new ImageGrid(pixels, pixels[0].length, pixels.length);

    layers.add(new Layer(true, redSquare, "first"));

    Readable read = new StringReader("save blankSquare.jpg JPEG");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model,
        layers, read, app);

    IGrid gridToBeChanged = ImageUtil.convertToGrid(inJPEG, inJPEG.getWidth(), inJPEG.getHeight());

    Pixel[][] gridToBeChangedPixels = gridToBeChanged.getPixels();

    for (int i = 0; i < gridToBeChangedPixels.length; i++) {
      for (int j = 0; j < gridToBeChangedPixels[0].length; j++) {

        assertEquals(new Pixel(new Color(255, 255, 255)),
            gridToBeChangedPixels[i][j]);

      }
    }

    controller.parseInput();

    IGrid redGrid =
        ImageUtil.
            convertToGrid(
                ImageIO.read(new File("blankSquare.jpg")), 234, 221);

    Pixel[][] redGridPixels = redGrid.getPixels();

    for (int i = 0; i < redGridPixels.length; i++) {
      for (int j = 0; j < redGridPixels[0].length; j++) {
        assertEquals(new Pixel(new Color(254, 0, 0)),
            redGridPixels[i][j]);
      }
    }

  }

  @Test
  public void testSaveImagePNG() throws IOException {

    Pixel[][] pixels = new Pixel[221][234];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = new Pixel(new Color(254, 0, 0));
      }
    }

    List<ILayer> layers = new ArrayList<>();

    ImageGrid redSquare = new ImageGrid(pixels, pixels[0].length, pixels.length);

    layers.add(new Layer(true, redSquare, "first"));

    Readable read = new StringReader("save blankSquare.png PNG");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model,
        layers, read, app);

    IGrid gridToBeChanged = ImageUtil.convertToGrid(inPNG, inPNG.getWidth(), inPNG.getHeight());

    Pixel[][] gridToBeChangedPixels = gridToBeChanged.getPixels();

    for (int i = 0; i < gridToBeChangedPixels.length; i++) {
      for (int j = 0; j < gridToBeChangedPixels[0].length; j++) {

        assertEquals(new Pixel(new Color(255, 255, 255)),
            gridToBeChangedPixels[i][j]);

      }
    }

    controller.parseInput();

    IGrid redGrid =
        ImageUtil.
            convertToGrid(
                ImageIO.read(new File("blankSquare.jpg")), 234, 221);

    Pixel[][] redGridPixels = redGrid.getPixels();

    for (int i = 0; i < redGridPixels.length; i++) {
      for (int j = 0; j < redGridPixels[0].length; j++) {
        assertEquals(new Pixel(new Color(254, 0, 0)),
            redGridPixels[i][j]);
      }
    }

  }


}
package controller;

import java.util.ArrayList;
import java.util.Arrays;
import model.Color;
import model.ImageGrid;
import model.Pixel;
import model.SimpleImageModel;
import model.creators.CreateCheckerboard;
import org.junit.Before;
import org.junit.Test;
import view.ILayer;
import view.Layer;
import model.IGrid;
import java.lang.StringBuffer;
import java.io.StringReader;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

public class ImageProcessingCommandTest {

  ArrayList<ILayer> empty;
  ILayer blankLayer;
  SimpleImageModel model;
  IGrid checkerboard2x2;
  Appendable app;

  @Before
  public void setUp() {
    empty = new ArrayList<ILayer>();

    model = new SimpleImageModel();

    blankLayer = new Layer(true, "first", fileLocation);

    checkerboard2x2 = new CreateCheckerboard(10, 2,
        new ArrayList<Color>(Arrays.asList(new Color(255, 255, 255), new Color(0,0,0)))).apply();

    app = new StringBuffer();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerNullModel() {
    ImageProcessingCommand add = new AddLayer("test", empty);
    add.execute(null, new Layer(true, "test", fileLocation));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNullModel() {
    ImageProcessingCommand remove = new RemoveLayer(empty);
    remove.execute(null, new Layer(true, "test", fileLocation));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurLayerNullModel() {
    ImageProcessingCommand blur = new BlurImage();
    blur.execute(null, new Layer(true, "test", fileLocation));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenLayerNullModel() {
    ImageProcessingCommand sharpen = new SharpenImage();
    sharpen.execute(null, new Layer(true, "test", fileLocation));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaLayerNullModel() {
    ImageProcessingCommand sepia = new TransformSepia();
    sepia.execute(null, new Layer(true, "test", fileLocation));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleLayerNullModel() {
    ImageProcessingCommand greyscale = new TransformGreyscale();
    greyscale.execute(null, new Layer(true, "test", fileLocation));
  }
  
  @Test
  public void testAddLayer() {
    Readable read = new StringReader("add test");
    assertEquals(new ArrayList<ILayer>(), empty);
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read,
        app);

    controller.parseInput();
    ILayer testLayer = new Layer(true, "test", fileLocation);

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
        new ArrayList<ILayer>(Arrays.asList(new Layer(true, checkerboard2x2, "first", fileLocation))), read, app);
    assertEquals(checkerboard2x2, model.getImageAt(0));
    controller.parseInput();
    assertNotEquals(checkerboard2x2, model.getImageAt(1));
  }

  @Test
  public void testLoadImage() {

    Pixel[][] pixels = new Pixel[221][234];

    System.out.println(pixels[0][0]);

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = new Pixel(new Color(0,0,0));
      }
    }


    SimpleImageModel model = new SimpleImageModel();

    List<ILayer> layers = new ArrayList<>();

    ImageGrid redSquare = new ImageGrid(pixels, pixels[0].length, pixels.length);

    layers.add(new Layer(true, redSquare, "first", fileLocation));

    Readable read = new StringReader("load /Users/satwikmisra/Downloads/test.jpg");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model,
        layers, read, app);

    controller.parseInput();


    System.out.println(layers.get(0).getImage().getPixels()[0][0]);

//    System.out.println(Arrays.deepToString(layers.get(0).getImage().getPixels()));

//    for (Pixel[] x : layers.get(0).getImage().getPixels()) {
//      for (Pixel p : x) {
//        System.out.println(p.toString());
//      }
//    }

//    assertEquals(5, layers.size());

  }

  @Test
  public void testSaveImage() {

  }
}
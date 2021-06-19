package controller;

import java.util.ArrayList;
import java.util.Arrays;
import model.Color;
import model.SimpleImageModel;
import model.creators.CreateCheckerboard;
import org.junit.Before;
import org.junit.Test;
import view.ILayer;
import view.Layer;
import model.IGrid;
import model.IColor;
import java.lang.StringBuffer;
import java.io.StringReader;
import static org.junit.Assert.assertEquals;

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

    blankLayer = new Layer(true, "first");

    checkerboard2x2 = new CreateCheckerboard(10, 2,
        new ArrayList<Color>(Arrays.asList(new Color(255, 255, 255), new Color(0,0,0)))).apply();

    app = new StringBuffer();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddLayerNullModel() {
    ImageProcessingCommand add = new AddLayer("test", empty);
    add.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLayerNullModel() {
    ImageProcessingCommand remove = new RemoveLayer("test", empty);
    remove.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlurLayerNullModel() {
    ImageProcessingCommand blur = new BlurImage();
    blur.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSharpenLayerNullModel() {
    ImageProcessingCommand sharpen = new SharpenImage();
    sharpen.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSepiaLayerNullModel() {
    ImageProcessingCommand sepia = new TransformSepia();
    sepia.execute(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleLayerNullModel() {
    ImageProcessingCommand greyscale = new TransformGreyscale();
    greyscale.execute(null);
  }

  @Test
  public void testBlur() {
    //model.add(checkerboard2x2);
    //IProcessingController controller = new SimpleImageProcessingController();
  }

  @Test
  public void testAddLayer() {
    Readable read = new StringReader("add test");
    assertEquals(new ArrayList<ILayer>(), empty);
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read,
        app);

    controller.parseInput();
    ILayer testLayer = new Layer(true, "test");


    assertEquals(new ArrayList<ILayer>(Arrays.asList(testLayer)), empty);
  }
}
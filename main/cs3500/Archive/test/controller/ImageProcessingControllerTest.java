package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import model.Color;
import model.IGrid;
import model.SimpleImageModel;
import model.creators.CreateCheckerboard;
import org.junit.Before;
import org.junit.Test;
import view.ILayer;
import view.IProcessingImageView;
import view.Layer;
import view.SimpleProcessingImageView;

/**
 * Represents tests for the simple image processing controller.
 */
public class ImageProcessingControllerTest {

  ArrayList<ILayer> empty;
  ILayer blankLayer;
  SimpleImageModel model;
  IGrid checkerboard2x2;
  Appendable app;
  IProcessingImageView view;

  @Before
  public void setUp() {
    empty = new ArrayList<ILayer>();

    model = new SimpleImageModel();

    blankLayer = new Layer(true, "first");

    checkerboard2x2 = new CreateCheckerboard(10, 2,
        new ArrayList<Color>(Arrays.asList(new Color(255, 255, 255), new Color(0, 0, 0)))).apply();

    app = new StringBuffer();

    view = new SimpleProcessingImageView(app);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCommand() {
    Readable read = new StringReader("qwerty");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.parseInput();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCurrent() {
    Readable read = new StringReader("blur");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.parseInput();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullCommand() {
    Readable read = new StringReader("null");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.parseInput();
  }

  @Test
  public void testQuit() {
    Readable read = new StringReader("q");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.parseInput();
    assertEquals("Program has quit.", app.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAppendable() {
    Readable read = new StringReader("q");
    Appendable illegal = new IllegalAppendable();
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read,
        illegal);
    controller.parseInput();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTwoInvalidStrings() {
    Readable read = new StringReader("asd asdasda");
    Appendable illegal = new IllegalAppendable();
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read,
        illegal);
    controller.parseInput();
    assertEquals("Invalid command.", app.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testValidCommandWithBadString() {
    Readable read = new StringReader("load blackSquare.jpg asdasda");
    Appendable illegal = new IllegalAppendable();
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read,
        illegal);
    controller.parseInput();
    assertEquals("Invalid command.", app.toString());
  }

  @Test
  public void testCurrentCommand() {
    Readable read = new StringReader("add first add second current first");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.parseInput();
    assertEquals("first", controller.getCurrent().getName());
  }

  @Test
  public void testBatchCommands() throws FileNotFoundException {

    FileInputStream fstream = new FileInputStream("batchCommands.txt");
    BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    IProcessingController controller = new SimpleImageProcessingController(model, empty, br, app);

    assertEquals(0, controller.getLayers().size());

    controller.parseInput();

    for (ILayer layer : controller.getLayers()) {
      System.out.println(layer.getName());
    }

    assertFalse(controller.getLayers().get(0).getVisibility());

    assertTrue(controller.getLayers().get(1).getVisibility());

    assertEquals(4, controller.getLayers().size());

    assertEquals(4, controller.getLayers().size());


  }
}
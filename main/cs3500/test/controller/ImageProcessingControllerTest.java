package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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
import view.TextView;

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

    view = new TextView(app);
  }

  @Test()
  public void testInvalidCommand() {
    Readable read = new StringReader("qwerty");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.parseInput();

    assertEquals("asd", app.toString());
  }

  @Test()
  public void testInvalidCurrent() {
    Readable read = new StringReader("blur");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.parseInput();
    assertEquals("asd", app.toString());

  }

  @Test()
  public void testNullCommand() {
    Readable read = new StringReader("null");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.parseInput();
    assertEquals("asd", app.toString());

  }

  @Test
  public void testQuit() {
    Readable read = new StringReader("q");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.parseInput();
    assertEquals("Welcome to Simp, an free Photoshop alternative. " + "Create a layer to begin. \n"
        + "Program has quit.", app.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalAppendable() {
    Readable read = new StringReader("q");
    Appendable illegal = new IllegalAppendable();
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read,
        illegal);
    controller.parseInput();
  }

  @Test()
  public void testTwoInvalidStrings() {
    Readable read = new StringReader("asd asdasda");
    Appendable illegal = new IllegalAppendable();
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read,
        illegal);
    controller.parseInput();
    assertEquals("Invalid command.", app.toString());
  }

  @Test()
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
    Readable read = new StringReader("add first add second current first q");
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

  @Test
  public void testCheckNullCurrentTrue() {

    SimpleImageProcessingController controller = new SimpleImageProcessingController(
        new SimpleImageModel(), new ArrayList<>(), new StringReader("test"), app);

    assertTrue(controller.checkNullCurrent());

  }

  @Test
  public void testCheckNullCurrentFalse() {

    SimpleImageProcessingController controller = new SimpleImageProcessingController(
        new SimpleImageModel(), new ArrayList<>(), new StringReader("test"), app);

    controller.setCurrent(new Layer(true, "test"));

    assertFalse(controller.checkNullCurrent());

  }

  @Test
  public void testRenderMsgToView() {

    Readable read = new StringReader("asdasd");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.renderMessageToView("hello world");
    assertEquals("hello world", app.toString());

  }

  @Test
  public void testRemoveCurrent() {

    Readable read = new StringReader("asdasd");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);
    controller.renderMessageToView("hello world");

    empty.add(new Layer(true, "first"));
    controller.setCurrent(new Layer(true, "first"));

    assertEquals(new Layer(true, "first"), controller.getCurrent());

    assertEquals(1, controller.getLayers().size());

    controller.removeCurrent();

    assertEquals(0, controller.getLayers().size());

  }

  @Test
  public void testSetLayerWithSameName() {

    Readable read = new StringReader("asdasd");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);

    empty.add(new Layer(true, "first"));

    assertTrue(controller.getLayers().get(0).getVisibility());

    controller.setLayerWithSameName(new Layer(false, "first"));

    assertFalse(controller.getLayers().get(0).getVisibility());

  }

  @Test
  public void testSetCurrent() {

    Readable read = new StringReader("asdasd");
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);

    assertEquals(0, controller.getLayers().size());

    assertNull(controller.getCurrent());

    controller.getLayers().add(new Layer(true, "first"));

    controller.setCurrent(new Layer(true, "first"));

    assertEquals(1, controller.getLayers().size());

    assertEquals(new Layer(true, "first"), controller.getCurrent());

  }

  @Test
  public void testGetWidth() {

    Readable read = new StringReader(
        "add first current first" + " load main/cs3500/src/testImages/cyanSq.jpg q");

    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);

    assertEquals(250, controller.getWidth());

    controller.parseInput();

    assertEquals(373, controller.getWidth());


  }

  @Test
  public void testGetHeight() {

    Readable read = new StringReader(
        "add first current first" + " load main/cs3500/src/testImages/cyanSq.jpg q");

    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);

    assertEquals(250, controller.getHeight());

    controller.parseInput();

    assertEquals(240, controller.getHeight());

  }

  @Test
  public void testSetWidth() {

    Readable read = new StringReader(
        "add first current first" + " load main/cs3500/src/testImages/cyanSq.jpg q");

    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);

    assertEquals(250, controller.getWidth());

    controller.setWidth(500);

    assertEquals(500, controller.getWidth());
  }

  @Test
  public void testSetHeight() {

    Readable read = new StringReader(
        "add first current first" + " load main/cs3500/src/testImages/cyanSq.jpg q");

    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);

    assertEquals(250, controller.getHeight());

    controller.setHeight(500);

    assertEquals(500, controller.getHeight());

  }

  @Test
  public void testGetCurrent() {

    Readable read = new StringReader(
        "add first current first" + " load main/cs3500/src/testImages/cyanSq.jpg q");

    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);

    assertNull(controller.getCurrent());

    controller.setCurrent(new Layer(false, "hello"));

    assertEquals(new Layer(false, "hello"), controller.getCurrent());

  }

  @Test
  public void testGetLayersAndParseInput() {

    Readable read = new StringReader("add first add second add third q");

    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);

    assertEquals(0, controller.getLayers().size());

    controller.parseInput();

    assertEquals(3, controller.getLayers().size());
  }


}
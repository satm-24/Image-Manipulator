package controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
import view.TextView;

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

  File exportFile;
  File savePPMFile;

  @Before
  public void setUp() throws IOException {
    empty = new ArrayList<ILayer>();

    exportFile = new File("main/cs3500/src/testImages/exportTest.txt");

    savePPMFile = new File("main/cs3500/src/testImages/testPPM2.ppm");

    PrintWriter writer = new PrintWriter(exportFile);
    writer.print("");
    writer.close();


    model = new SimpleImageModel();

    blankLayer = new Layer(true, "first");

    checkerboard2x2 = new CreateCheckerboard(10, 2,
        new ArrayList<Color>(Arrays.asList(new Color(255, 255, 255), new Color(0, 0, 0)))).apply();

    app = new StringBuffer();

    view = new TextView(app);

    inJPEG = ImageIO.read(new File("main/cs3500/src/testImages/blankSquare.jpg"));
    inPNG = ImageIO.read(new File("main/cs3500/src/testImages/blankSquare.png"));

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
  public void test() {
    ImageProcessingCommand add = new AddLayer("test");
    add.execute(null, null);
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
    IProcessingController controller = new SimpleImageProcessingController(model, empty, read, app);

    controller.parseInput();
    ILayer testLayer = new Layer(true, "test");

    assertNotEquals(new ArrayList<ILayer>(), empty);
  }

  @Test
  public void testBlurLayer() {
    Readable read = new StringReader("blur");
    List<Color> colors = new ArrayList<Color>(
        Arrays.asList(new Color(255, 255, 255), new Color(0, 0, 0)));
    IGrid checkerboard2x2 = new CreateCheckerboard(10, 2, colors).apply();
    model.add(checkerboard2x2);
    IProcessingController controller = new SimpleImageProcessingController(model,
        new ArrayList<ILayer>(Arrays.asList(new Layer(true, checkerboard2x2, "first"))), read, app);
    assertEquals(checkerboard2x2, controller.getCurrent().getImage());
    controller.parseInput();
    assertEquals(checkerboard2x2, controller.getCurrent().getImage());
  }

  @Test
  public void testSharpenLayer() {
    Readable read = new StringReader("sharpen");
    List<Color> colors = new ArrayList<Color>(
        Arrays.asList(new Color(255, 255, 255), new Color(0, 0, 0)));
    IGrid checkerboard2x2 = new CreateCheckerboard(10, 2, colors).apply();
    model.add(checkerboard2x2);
    IProcessingController controller = new SimpleImageProcessingController(model,
        new ArrayList<ILayer>(Arrays.asList(new Layer(true, checkerboard2x2, "first"))), read, app);
    assertEquals(checkerboard2x2, controller.getCurrent().getImage());
    controller.parseInput();
    IGrid result = controller.getCurrent().getImage();
    assertEquals(checkerboard2x2, result);
  }

  @Test
  public void testSepiaLayer() {
    Readable read = new StringReader("sepia");
    List<Color> colors = new ArrayList<Color>(
        Arrays.asList(new Color(255, 255, 255), new Color(0, 0, 0)));
    IGrid checkerboard2x2 = new CreateCheckerboard(10, 2, colors).apply();
    model.add(checkerboard2x2);
    IProcessingController controller = new SimpleImageProcessingController(model,
        new ArrayList<ILayer>(Arrays.asList(new Layer(true, checkerboard2x2, "first"))), read, app);
    assertEquals(checkerboard2x2, controller.getCurrent().getImage());
    controller.parseInput();
    IGrid result = controller.getCurrent().getImage();
    assertEquals(checkerboard2x2, result);
  }

  @Test
  public void testGreyscaleLayer() {

    Readable read = new StringReader("greyscale");
    List<Color> colors = new ArrayList<Color>(
        Arrays.asList(new Color(255, 255, 255), new Color(0, 0, 0)));
    IGrid checkerboard2x2 = new CreateCheckerboard(10, 2, colors).apply();
    model.add(checkerboard2x2);
    IProcessingController controller = new SimpleImageProcessingController(model,
        new ArrayList<ILayer>(Arrays.asList(new Layer(true, checkerboard2x2, "first"))), read, app);
    assertEquals(checkerboard2x2, controller.getCurrent().getImage());
    controller.parseInput();
    IGrid result = controller.getCurrent().getImage();
    assertEquals(checkerboard2x2, result);
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

    Readable read = new StringReader("load main/cs3500/src/testImages/redSqJPG.jpg");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);

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

    Readable read = new StringReader("load main/cs3500/src/testImages/redSqPNG.png");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);

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
  public void testSaveImageJPEG() throws IOException {


    List<ILayer> layers = new ArrayList<>();



    Readable read = new StringReader("save main/cs3500/src/testImages/redSqJPG.jpg JPEG");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);



    controller.parseInput();


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

    Readable read = new StringReader("save main/cs3500/src/testImages/redSqPNG.png PNG");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);

    IGrid gridToBeChanged = ImageUtil.convertImgToGrid(inPNG, inPNG.getWidth(), inPNG.getHeight());

    Pixel[][] gridToBeChangedPixels = gridToBeChanged.getPixels();

    for (int i = 0; i < gridToBeChangedPixels.length; i++) {
      for (int j = 0; j < gridToBeChangedPixels[0].length; j++) {

        assertEquals(new Pixel(new Color(255, 255, 255)), gridToBeChangedPixels[i][j]);

      }
    }

    controller.parseInput();

    IGrid redGrid = ImageUtil
        .convertImgToGrid(ImageIO.read(new File("main/cs3500/src/testImages/redSqPNG.png")), 234,
            221);

    Pixel[][] redGridPixels = redGrid.getPixels();

    for (int i = 0; i < redGridPixels.length; i++) {
      for (int j = 0; j < redGridPixels[0].length; j++) {
        assertEquals(new Pixel(new Color(254, 0, 0)), redGridPixels[i][j]);
      }
    }
  }

  // do these

  @Test
  public void testSaveImagePPM()  {

    Pixel[][] pixels = new Pixel[221][234];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = new Pixel(new Color(254, 0, 0));
      }
    }

    ImageGrid savedPPM = ImageUtil.readPPM("main/cs3500/src/testImages/testPPM1.ppm");


    assertEquals(new Pixel(new Color(85,150,116)), savedPPM.getPixels()[0][0]);

    List<ILayer> layers = new ArrayList<>();

    Readable read = new StringReader("add first current first load "
        + "main/cs3500/src/testImages/smallCat.jpg save "
        + "main/cs3500/src/testImages/testPPM1.ppm ppm q");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);


    controller.parseInput();

    savedPPM = ImageUtil.readPPM("main/cs3500/src/testImages/testPPM1.ppm");


    assertEquals(new Pixel(new Color(0,0,0)), savedPPM.getPixels()[0][0]);


  }

  @Test
  public void testLoadImagePPM() throws IOException {

    Pixel[][] pixels = new Pixel[221][234];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = new Pixel(new Color(254, 0, 0));
      }
    }

    List<ILayer> layers = new ArrayList<>();

    ImageGrid redSquare = new ImageGrid(pixels, pixels[0].length, pixels.length);

    layers.add(new Layer(true, redSquare, "first"));

    Readable read = new StringReader("load main/cs3500/src/testImages/testPPM1.ppm ppm");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);

    IGrid gridToBeChanged = ImageUtil.convertImgToGrid(inPNG, inPNG.getWidth(), inPNG.getHeight());

    Pixel[][] gridToBeChangedPixels = gridToBeChanged.getPixels();

    for (int i = 0; i < gridToBeChangedPixels.length; i++) {
      for (int j = 0; j < gridToBeChangedPixels[0].length; j++) {

        assertEquals(new Pixel(new Color(255, 255, 255)), gridToBeChangedPixels[i][j]);

      }
    }

    controller.parseInput();

    IGrid redGrid = ImageUtil.convertImgToGrid(ImageIO.read(new File("koalaTest.jpg")), 1024, 768);

    Pixel[][] redGridPixels = redGrid.getPixels();

    for (int i = 0; i < redGridPixels.length; i++) {
      for (int j = 0; j < redGridPixels[0].length; j++) {
        assertEquals(new Pixel(new Color(254, 0, 0)), redGridPixels[i][j]);
      }
    }
  }

  @Test
  public void testMosaic() {

    Pixel[][] pixels;

    File file = new File("main/cs3500/src/testImages/smallCat" + ".jpg");
    BufferedImage bi = null;
    try {
      bi = ImageIO.read(file);
    } catch (IOException e) {
      // could not read file
    }

    pixels = ImageUtil.convertImgToGrid(bi, 200, 250).getPixels();

    List<ILayer> layers = new ArrayList<>();

    assertEquals(new Pixel(new Color(85, 150, 116)), pixels[0][10]);

    assertEquals(new Pixel(new Color(26, 27, 55)), pixels[100][100]);

    assertEquals(new Pixel(new Color(77, 76, 82)), pixels[59][199]);

    Readable read = new StringReader(
        "add f" + " current f load main/cs3500/src/testImages/smallCat.jpg mosaic 10 save "
            + "main/cs3500/src/testImages/smallCat2.jpg jpg");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);

    controller.parseInput();

    Pixel[][] afterMosaic = controller.getLayers().get(0).getImage().getPixels();

    assertNotEquals(new Pixel(new Color(85, 150, 116)), afterMosaic[0][10]);

    assertNotEquals(new Pixel(new Color(26, 27, 55)), afterMosaic[100][100]);

    assertNotEquals(new Pixel(new Color(77, 76, 82)), afterMosaic[59][199]);


  }

  @Test
  public void testDownsize() {

    List<ILayer> layers = new ArrayList<>();

    Readable read = new StringReader(
        "add f" + " current f load main/cs3500/src/testImages/smallCat.jpg downsize 100 150 "
            + "save " + "main/cs3500/src/testImages/smallCat2.jpg jpg");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);

    assertEquals(250, controller.getWidth());

    assertEquals(250, controller.getWidth());

    controller.parseInput();

    assertEquals(100, controller.getWidth());

    assertEquals(150, controller.getHeight());

  }

  @Test
  public void testDownsizeAllLayers() {

    List<ILayer> layers = new ArrayList<>();

    Readable read = new StringReader(
        "add f add s add third" + " current third load main/cs3500/src/testImages/smallCat.jpg "
            + "downsize " + "100 150 " + "save " + "main/cs3500/src/testImages/smallCat2.jpg jpg "
            + "q");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);

    Pixel[][] pixels = new Pixel[250][250];

    for (int i = 0; i < pixels.length; i++) {
      for (int j = 0; j < pixels[0].length; j++) {
        pixels[i][j] = new Pixel(new Color(0, 0, 0));
      }
    }

    layers.add(new Layer(true, new ImageGrid(pixels, 250, 250), "f"));
    layers.add(new Layer(true, new ImageGrid(pixels, 250, 250), "s"));
    layers.add(new Layer(true, new ImageGrid(pixels, 250, 250), "third"));

    assertEquals(250, controller.getLayers().get(0).getImage().getPixels().length);

    assertEquals(250, controller.getLayers().get(0).getImage().getPixels()[0].length);

    assertEquals(250, controller.getLayers().get(1).getImage().getPixels().length);

    assertEquals(250, controller.getLayers().get(1).getImage().getPixels()[0].length);

    assertEquals(250, controller.getLayers().get(2).getImage().getPixels().length);

    assertEquals(250, controller.getLayers().get(2).getImage().getPixels()[0].length);

    controller.parseInput();

    assertEquals(150, controller.getLayers().get(0).getImage().getPixels().length);

    assertEquals(100, controller.getLayers().get(0).getImage().getPixels()[0].length);

    assertEquals(150, controller.getLayers().get(1).getImage().getPixels().length);

    assertEquals(100, controller.getLayers().get(1).getImage().getPixels()[0].length);

    assertEquals(150, controller.getLayers().get(2).getImage().getPixels().length);

    assertEquals(100, controller.getLayers().get(2).getImage().getPixels()[0].length);
  }

  @Test
  public void testExportMultipleImages() {

    List<ILayer> layers = new ArrayList<>();

    Readable read = new StringReader("add f add s add third"
        + " current third loadmany main/cs3500/src/testImages/loadTest.txt export "
        + "main/cs3500/src/testImages/exportTest.txt q");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);

    try {
      FileReader fr = new FileReader(exportFile);
      BufferedReader br = new BufferedReader(fr);
      StringBuilder buffer = new StringBuilder();
      String line;

      while ((line = br.readLine()) != null) {
        buffer.append(line);
        buffer.append("\n");
      }

      fr.close();

      assertEquals("", buffer.toString());
    } catch (IOException e) {
      // could not perform the file operation
    }

    controller.parseInput();

    try {
      FileReader fr = new FileReader(exportFile);
      BufferedReader br = new BufferedReader(fr);
      StringBuilder sb = new StringBuilder();
      String line;

      while ((line = br.readLine()) != null) {
        sb.append(line);
        sb.append("\n");
      }

      fr.close();

      assertEquals(
          "main/cs3500/src/testImages/mario.png\n" + "main/cs3500/src/testImages/smallCat.jpg\n"
              + "main/cs3500/src/testImages/mario2.png\n", sb.toString());
    } catch (IOException e) {
      // could not read the file
    }

  }

  @Test
  public void testLoadMany() {

    List<ILayer> layers = new ArrayList<>();

    Readable read = new StringReader("add f add s add third"
        + " current third loadmany main/cs3500/src/testImages/loadTest.txt q");

    SimpleImageProcessingController controller = new SimpleImageProcessingController(model, layers,
        read, app);

    assertEquals(0, controller.getLayers().size());

    layers.add(new Layer(true, "f"));
    layers.add(new Layer(true, "s"));
    layers.add(new Layer(true, "third"));

    assertEquals("", controller.getLayers().get(0).getFileLocation());
    assertEquals("", controller.getLayers().get(1).getFileLocation());
    assertEquals("", controller.getLayers().get(2).getFileLocation());

    layers = new ArrayList<>();

    controller.parseInput();

    assertEquals("main/cs3500/src/testImages/mario.png",
        controller.getLayers().get(0).getFileLocation());
    assertEquals("main/cs3500/src/testImages/smallCat.jpg",
        controller.getLayers().get(1).getFileLocation());
    assertEquals("main/cs3500/src/testImages/mario2.png",
        controller.getLayers().get(2).getFileLocation());

  }


}
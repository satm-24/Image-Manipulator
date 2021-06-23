package model;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import model.operations.ColorTransformation;
import model.operations.Filter;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil extends java.awt.Color {


  public ImageUtil(int rgb) {
    super(rgb);
  }

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  public static ImageGrid readPPM(String filename) {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");

      return new ImageGrid(new Pixel[0][0], 0, 0);
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    System.out.println("Maximum value of a color in this file (usually 256): " + maxValue);

    Pixel[][] pixels = new Pixel[height][width];

    // make this return a list of lists
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        pixels[i][j] = new Pixel(new Color(r, g, b));
      }
    }

    return new ImageGrid(pixels, width, height);


  }

  /**
   * Writes the given image to the file destination.
   *
   * @param ig            the image
   * @param fileToWriteTo the file to be written to
   */
  public static void writeToPPM(ImageGrid ig, String fileToWriteTo) {

    try {

      PrintWriter out
          = new PrintWriter(
          new BufferedWriter(new FileWriter(fileToWriteTo, false)));

      out.write(ig.convertGridToPPM());

      out.close();


    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("File written Successfully");


  }


  /**
   * Sets the rgb values from the given image to a certain pixel location.
   *
   * @param imgPNG  the given image
   * @param pixels2 the array of pixels we're mutating
   * @param i       the row index
   * @param j       the col index
   */
  private static void setPixelToRGB(BufferedImage imgPNG, Pixel[][] pixels2, int i, int j) {
    int r = new ImageUtil(imgPNG.getRGB(i, j)).getRed();
    int g = new ImageUtil(imgPNG.getRGB(i, j)).getGreen();
    int b = new ImageUtil(imgPNG.getRGB(i, j)).getBlue();
    pixels2[j][i] = new Pixel(new Color(r, g, b));
  }

  /**
   * Converts the given BI to an ImageGrid.
   *
   * @param img    given BI
   * @param width  width of BI
   * @param height height of BI
   * @return imagegrid rep of BI
   */
  public static ImageGrid convertToGrid(BufferedImage img, int width, int height) {

    Pixel[][] pixels = new Pixel[height][width];

    for (int i = 0; i < width; i++) { // width
      for (int j = 0; j < height; j++) { // height
        setPixelToRGB(img, pixels, i, j);
      }
    }

    return new ImageGrid(pixels, width, height);
  }

  /**
   * Runs the image processing program.
   *
   * @param args the image files
   */
  public static void main(String[] args) throws IOException {

    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "Koala.ppm";
    }

    System.out.println(new File("/Users/satwikmisra/Desktop/koalaTest.ppm"));

    ImageUtil.writeToPPM(readPPM(filename), "/Users/satwikmisra/Desktop/koalaTest.ppm");


    /*
    This is the code for writing the processed versions of our sample PPMs into their respective
    files.
     */

    SimpleImageModel m = new SimpleImageModel();
    SimpleImageModel m2 = new SimpleImageModel();
    SimpleImageModel m3 = new SimpleImageModel();
    SimpleImageModel m4 = new SimpleImageModel();

    SimpleImageModel m5 = new SimpleImageModel();
    SimpleImageModel m6 = new SimpleImageModel();
    SimpleImageModel m7 = new SimpleImageModel();
    SimpleImageModel m8 = new SimpleImageModel();

    m.add(readPPM("/Users/satwikmisra/Desktop/TransformedCopies/blackbuck.ascii.ppm"));
    m2.add(readPPM("/Users/satwikmisra/Desktop/TransformedCopies/blackbuck.ascii.ppm"));
    m3.add(ImageUtil.readPPM("/Users/satwikmisra/Desktop/TransformedCopies/blackbuck.ascii.ppm"));
    m4.add(ImageUtil.readPPM("/Users/satwikmisra/Desktop/TransformedCopies/blackbuck.ascii.ppm"));

    m5.add(ImageUtil.readPPM("/Users/satwikmisra/Desktop/TransformedCopies/snail.ascii.ppm"));
    m6.add(ImageUtil.readPPM("/Users/satwikmisra/Desktop/TransformedCopies/snail.ascii.ppm"));
    m7.add(ImageUtil.readPPM("/Users/satwikmisra/Desktop/TransformedCopies/snail.ascii.ppm"));
    m8.add(ImageUtil.readPPM("/Users/satwikmisra/Desktop/TransformedCopies/snail.ascii.ppm"));

    IGrid img1 = m.operate(new ColorTransformation(TransformationType.GREYSCALE));
    IGrid img2 = m2.operate(new ColorTransformation(TransformationType.SEPIA));

    // blur and sharpen multiple times
    IGrid img3 = m3.operate(new Filter(FilterType.BLUR));
    img3 = m3.operate(new Filter(FilterType.BLUR));
    img3 = m3.operate(new Filter(FilterType.BLUR));
    IGrid img4 = m4.operate(new Filter(FilterType.SHARPEN));
    img4 = m4.operate(new Filter(FilterType.SHARPEN));
    img4 = m4.operate(new Filter(FilterType.SHARPEN));

    IGrid img5 = m5.operate(new ColorTransformation(TransformationType.GREYSCALE));
    IGrid img6 = m6.operate(new ColorTransformation(TransformationType.SEPIA));
    IGrid img7 = m7.operate(new Filter(FilterType.BLUR));
    img7 = m7.operate(new Filter(FilterType.BLUR));
    img7 = m7.operate(new Filter(FilterType.BLUR));
    img7 = m7.operate(new Filter(FilterType.BLUR));
    IGrid img8 = m8.operate(new Filter(FilterType.SHARPEN));
    img8 = m8.operate(new Filter(FilterType.SHARPEN));
    img8 = m8.operate(new Filter(FilterType.SHARPEN));

    writeToPPM((ImageGrid) img1,
        "/Users/satwikmisra/Desktop/TransformedCopies/greyscaleBuck.ppm");

    ImageUtil.writeToPPM((ImageGrid) img2,
        "/Users/satwikmisra/Desktop/TransformedCopies/sepiaBuck.ppm");

    ImageUtil.writeToPPM((ImageGrid) img3,
        "/Users/satwikmisra/Desktop/TransformedCopies/blurredBuck.ppm");

    ImageUtil.writeToPPM((ImageGrid) img4,
        "/Users/satwikmisra/Desktop/TransformedCopies/sharpBuck.ppm");

    ImageUtil.writeToPPM((ImageGrid) img5,
        "/Users/satwikmisra/Desktop/TransformedCopies/greyscaleSnail.ppm");

    ImageUtil.writeToPPM((ImageGrid) img6,
        "/Users/satwikmisra/Desktop/TransformedCopies/sepiaSnail.ppm");

    ImageUtil.writeToPPM((ImageGrid) img7,
        "/Users/satwikmisra/Desktop/TransformedCopies/blurSnail.ppm");

    ImageUtil.writeToPPM((ImageGrid) img8,
        "/Users/satwikmisra/Desktop/TransformedCopies/sharpSnail.ppm");


  }


}


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
import javax.imageio.ImageIO;

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

  // tests writing PNG and JPEG files to PPM format
  public static void writeDiffFormatsToPPM() throws IOException {

    File fileJPEG = new File("/Users/satwikmisra/Desktop/smallCat.jpeg");

    File filePNG = new File("/Users/satwikmisra/Desktop/mario.png");


    BufferedImage imgJPEG = ImageIO.read(fileJPEG);
    BufferedImage imgPNG = ImageIO.read(filePNG);

    int width = imgJPEG.getWidth();
    int height = imgJPEG.getHeight();

    int widthPNG = imgPNG.getWidth();
    int heightPNG = imgPNG.getHeight();

    ImageIO.write(imgJPEG, "JPEG", new File("/Users/satwikmisra/Desktop/smallCat copy.jpeg"));

    ImageIO.write(imgPNG, "PNG", new File("/Users/satwikmisra/Desktop/mariocopy.png"));

    Pixel[][] pixels = new Pixel[height][width];

    Pixel[][] pixels2 = new Pixel[heightPNG][widthPNG];

   for (int i = 0; i < 300; i++) { // width
     for (int j = 0; j < 200; j++) { // height
       setPixelToRGB(imgJPEG, pixels, i, j);
     }
   }

    for (int i = 0; i < widthPNG; i++) { // width
      for (int j = 0; j < heightPNG; j++) { // height
        setPixelToRGB(imgPNG, pixels2, i, j);
      }
    }

   for (int i = 0; i < pixels.length; i++) {
     for (int j = 0; j < pixels[0].length; j++) {
//       System.out.println("Pixel at (" + i + ", " + j + "): " + pixels[i][j]);
     }
   }

   ImageGrid gridJPEG = new ImageGrid(pixels, width, height);

   ImageGrid gridPNG = new ImageGrid(pixels2, widthPNG, heightPNG);

   writeToPPM(gridJPEG, "/Users/satwikmisra/Desktop/smallCat.ppm");
   writeToPPM(gridPNG, "/Users/satwikmisra/Desktop/mario.ppm");

  }

  /**
   * Sets the rgb values from the given image to a certain pixel location.
   * @param imgPNG the given image
   * @param pixels2 the array of pixels we're mutating
   * @param i the row index
   * @param j the col index
   */
  private static void setPixelToRGB(BufferedImage imgPNG, Pixel[][] pixels2, int i, int j) {
    int r = new ImageUtil(imgPNG.getRGB(i,j)).getRed();
    int g = new ImageUtil(imgPNG.getRGB(i,j)).getGreen();
    int b = new ImageUtil(imgPNG.getRGB(i,j)).getBlue();
    pixels2[j][i] = new Pixel(new Color(r, g, b));
    System.out.println();
  }

  /**
   * Runs the image processing program.
   *
   * @param args the image files
   */
  public static void main(String[] args) throws IOException {

    writeDiffFormatsToPPM();

    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "Koala.ppm";
    }

   // ImageUtil.writeToPPM(readPPM(filename), "/Users/satwikmisra/Desktop/koalaTest.ppm");


    /*
    This is the code for writing the processed versions of our sample PPMs into their respective
    files.
     */

    /*
    PPMImageModel m = new PPMImageModel();
    PPMImageModel m2 = new PPMImageModel();
    PPMImageModel m3 = new PPMImageModel();
    PPMImageModel m4 = new PPMImageModel();

    PPMImageModel m5 = new PPMImageModel();
    PPMImageModel m6 = new PPMImageModel();
    PPMImageModel m7 = new PPMImageModel();
    PPMImageModel m8 = new PPMImageModel();

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

    */
  }


}


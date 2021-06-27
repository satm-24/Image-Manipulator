package controller;

import java.awt.Image;
import model.Color;
import model.IGrid;
import model.IProcessingImageModel;
import model.ImageGrid;
import model.ImageProcessingUtils;
import model.ImageUtil;
import model.Pixel;
import view.ILayer;

/**
 * Represents a command to downsize images.
 */
public class Downsize implements ImageProcessingCommand {

  private final String width;
  private final String height;

  /**
   * Constructs an instance of Downsize with given dimensions to downsize to.
   *
   * @param width  the width we want to downsize to
   * @param height the height we want to downsize to
   */
  public Downsize(String width, String height) {
    this.width = width;
    this.height = height;
  }


  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    ImageProcessingUtils.checkNotNull(m, "Model cant be null \n");
    ImageProcessingUtils.checkNotNull(controller, "Controller can't be null \n");


    int newWidth;
    int newHeight;

    try {
      newWidth = Integer.parseInt(this.width);
    } catch (NumberFormatException e) {
      controller.renderMessageToView("Invalid width! \n");
      return;
    }

    try {
      newHeight = Integer.parseInt(this.height);
    } catch (NumberFormatException e) {
      controller.renderMessageToView("Invalid height! \n");
      return;
    }

    for (ILayer layer : controller.getLayers()) {
      Pixel[][] oldImgPixels = layer.getImage().getPixels();

      Pixel[][] downsizedPxlsInFloatingPnt = downsizeOldPixels(oldImgPixels, newWidth, newHeight);

      IGrid downsizedImg = new ImageGrid(downsizedPxlsInFloatingPnt, newWidth, newHeight);

      controller.renderMessageToView(
          "downsized image width: " + downsizedImg.getPixels()[0].length + "\n");
      controller
          .renderMessageToView("downsized image height: " + downsizedImg.getPixels().length
              + "\n");

      layer.setImage(downsizedImg);

      controller.setWidth(newWidth);
      controller.setHeight(newHeight);
    }


  }

  /**
   * Downsizes the old image's pixels and returns the downsizel pixel array.
   *
   * @param oldImgPixels the old pixels
   * @param width        width to downsize to
   * @param height       height to downsize to
   * @return downsized pixels
   */
  private Pixel[][] downsizeOldPixels(Pixel[][] oldImgPixels, int width, int height) {

    Pixel[][] downsizedPxls = new Pixel[height][width];

    for (int i = 0; i < height; i++) { // y val
      for (int j = 0; j < width; j++) { // x val
        downsizedPxls[i][j] = new Pixel(
            new Color(getDownsizedRGBValue(downsizedPxls, j, i, oldImgPixels, "red"),
                getDownsizedRGBValue(downsizedPxls, j, i, oldImgPixels, "green"),
                getDownsizedRGBValue(downsizedPxls, j, i, oldImgPixels, "blue")));
      }
    }

    return downsizedPxls;

  }

  /**
   * Gets an int value between 0 and 255 for a specific R,G,B color of a downsized pixel.
   *
   * @param downsizedPxls array of downsized pixels
   * @param x             x index of downsized pixels
   * @param y             y index of downsized pixels
   * @param oldImgPixels  pixels of old image
   * @return RGB value corresponding to a particular channel
   */
  private int getDownsizedRGBValue(Pixel[][] downsizedPxls, int x, int y, Pixel[][] oldImgPixels,
      String rgb) {

    int colorVectorIdx;

    colorVectorIdx = determineColorVectorIdx(rgb);

    double downsizedWidth = downsizedPxls[0].length;
    double downsizedHeight = downsizedPxls.length;

    double oldWidth = oldImgPixels[0].length;
    double oldHeight = oldImgPixels.length;

    double xPrime = (x * oldWidth) / downsizedWidth;
    double yPrime = (y * oldHeight) / downsizedHeight;

    Location a = new Location(Math.floor(xPrime), Math.floor(yPrime));
    Location b = new Location(Math.ceil(xPrime), Math.floor(yPrime));
    Location c = new Location(Math.floor(xPrime), Math.ceil(yPrime));
    Location d = new Location(Math.ceil(xPrime), Math.ceil(yPrime));

    double cA = oldImgPixels[(int) a.getY()][(int) a.getX()]
        .getClrAsDoubleVector()[colorVectorIdx][0];
    double cB = oldImgPixels[(int) b.getY()][(int) b.getX()]
        .getClrAsDoubleVector()[colorVectorIdx][0];
    double cC = oldImgPixels[(int) c.getY()][(int) c.getX()]
        .getClrAsDoubleVector()[colorVectorIdx][0];
    double cD = oldImgPixels[(int) d.getY()][(int) d.getX()]
        .getClrAsDoubleVector()[colorVectorIdx][0];

    double m = cB * (xPrime - Math.floor(xPrime)) + cA * (Math.ceil(xPrime) - xPrime);
    double n = cD * (xPrime - Math.floor(xPrime)) + cC * (Math.ceil(xPrime) - xPrime);

    if (xPrime - Math.floor(xPrime) == 0) {
      m = cA;
      n = cC;
    }

    if (yPrime - Math.floor(yPrime) == 0) {
      return (int) m;
    }

    return (int) (n * (yPrime - Math.floor(yPrime)) + m * (Math.ceil(yPrime) - yPrime));


  }

  /**
   * Determines the color vector idx.
   *
   * @param rgb the String R,G,B type
   * @return the correct index
   */
  private int determineColorVectorIdx(String rgb) {
    int colorVectorIdx;
    switch (rgb) {
      case "red":
        colorVectorIdx = 0;
        break;
      case "green":
        colorVectorIdx = 1;
        break;
      case "blue":
        colorVectorIdx = 2;
        break;
      default:
        throw new IllegalArgumentException("Invalid color arg, must be R, G, or B!");
    }
    return colorVectorIdx;
  }
}

package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import model.Color;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import model.Pixel;
import model.PixelWithCoords;

/**
 * Represents a command for creating an image mosaic.
 */
public class Mosaic implements ImageProcessingCommand {

  private final int numSeeds;

  /**
   * Constructs a Mosaic object with a certain number of seeds.
   *
   * @param numSeeds number of seeds we want
   */
  public Mosaic(int numSeeds) {
    this.numSeeds = numSeeds;
  }

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    ImageProcessingUtils.checkNotNull(m, "Model cant be null \n");
    ImageProcessingUtils.checkNotNull(controller, "Controller can't be null \n");

    Pixel[][] imageToMosiac = controller.getLayers().get(0).getImage().getPixels();

    Random rand = new Random();

    List<PixelWithCoords> seedPixels = new ArrayList<>();

    for (int i = 0; i < numSeeds; i++) {

      int coordY = rand.nextInt(imageToMosiac.length);
      int coordX = rand.nextInt(imageToMosiac[0].length);

      seedPixels.add(new PixelWithCoords(imageToMosiac[coordY][coordX], coordX, coordY));
    }

    Map<PixelWithCoords, List<PixelWithCoords>> assignedPixels = new HashMap<>();

    for (int i = 0; i < imageToMosiac.length; i++) {
      for (int j = 0; j < imageToMosiac[0].length; j++) {

        PixelWithCoords pixel = new PixelWithCoords(imageToMosiac[i][j], j, i);

        PixelWithCoords closestSeed = findClosestSeed(pixel, seedPixels);

        if (!assignedPixels.containsKey(closestSeed)) {
          assignedPixels.put(closestSeed, new ArrayList<>(Arrays.asList(pixel)));
        } else {

          assignedPixels.get(closestSeed).add(pixel);
        }


      }
    }

    setAvgColorInClusters(seedPixels, assignedPixels);

  }


  /**
   * Sets the pixels associated with all the seeds to the average color of their cluster.
   *
   * @param seedPixels     the list of seeds
   * @param assignedPixels the map with seed pixels and the pixels in the grid that they are
   *                       associated with.
   */
  private void setAvgColorInClusters(List<PixelWithCoords> seedPixels,
      Map<PixelWithCoords, List<PixelWithCoords>> assignedPixels) {

    ImageProcessingUtils.checkNotNull(seedPixels, "Seeds cant be null \n");
    ImageProcessingUtils.checkNotNull(assignedPixels, "Assigned Pxls can't be null \n");

    for (PixelWithCoords pixel : seedPixels) {

      List<PixelWithCoords> associatedPixels = assignedPixels.get(pixel);


      if (associatedPixels != null) {
        Color avgColor = findAverageColor(associatedPixels);

        for (PixelWithCoords pixelFromAssociated : associatedPixels) {
          pixelFromAssociated.getPixel().setClr(avgColor);
        }
      }

    }

  }

  /**
   * Finds the average color of all pixels in the given list.
   *
   * @param associatedPixels the given list of pixels
   */
  private Color findAverageColor(List<PixelWithCoords> associatedPixels) {


    ImageProcessingUtils.checkNotNull(associatedPixels, "Pixels cant be null \n");

    double sumRed = 0;
    double sumGreen = 0;
    double sumBlue = 0;

    for (PixelWithCoords pixel : associatedPixels) {
      sumRed += pixel.getPixel().getClrAsDoubleVector()[0][0];
      sumGreen += pixel.getPixel().getClrAsDoubleVector()[1][0];
      sumBlue += pixel.getPixel().getClrAsDoubleVector()[2][0];
    }

    int avgRed = (int) (sumRed / associatedPixels.size());
    int avgGreen = (int) (sumGreen / associatedPixels.size());
    int avgBlue = (int) (sumBlue / associatedPixels.size());

    return new Color(avgRed, avgGreen, avgBlue);


  }

  /**
   * Finds each pixel's closest seed.
   *
   * @param pixel      the pixel whose seed we are trying to find
   * @param seedPixels all the seed pixels
   * @return the seed pixel that is closest to the given one
   */
  private PixelWithCoords findClosestSeed(PixelWithCoords pixel, List<PixelWithCoords> seedPixels) {

    ImageProcessingUtils.checkNotNull(pixel, "Pixel cant be null \n");
    ImageProcessingUtils.checkNotNull(seedPixels, "Seeds cant be null \n");

    double closestDistance = Double.MAX_VALUE;
    PixelWithCoords closestSeed = seedPixels.get(0);

    for (PixelWithCoords seedPixel : seedPixels) {
      if (distanceBetween(pixel, seedPixel) < closestDistance) {
        closestDistance = distanceBetween(pixel, seedPixel);
        closestSeed = seedPixel;
      }
    }

    return closestSeed;

  }

  /**
   * Computes the euclidean distance between two pixels.
   *
   * @param pixel     first pixel
   * @param seedPixel other pixel we're computing dist to
   * @return distance
   */
  private double distanceBetween(PixelWithCoords pixel, PixelWithCoords seedPixel) {

    ImageProcessingUtils.checkNotNull(pixel, "Pixel cant be null \n");
    ImageProcessingUtils.checkNotNull(seedPixel, "Seeds cant be null \n");

    double squaredSum =
        Math.pow(pixel.getX() - seedPixel.getX(), 2) + Math.pow(pixel.getY() - seedPixel.getY(), 2);

    return Math.abs(Math.sqrt(squaredSum));

  }
}

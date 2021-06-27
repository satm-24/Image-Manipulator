package model;

/**
 * Represents different implementations of grids for images and their respective methods.
 */
public interface IGrid {

  /**
   * Filters an image, given a filter type, which has an associated kernel. For each pixel of an
   * image, the center of the kernel is placed on the pixel and each channel value is multiplied by
   * the corresponding kernel value than added together to get the final channel value. All values
   * are clamped
   *
   * @param ft the type of filter to be applied
   * @return the resulting filtered image
   * @throws IllegalArgumentException if the given type is null
   */
  IGrid filter(FilterType ft) throws IllegalArgumentException;

  /**
   * Transforms the color of each pixel's channel, based on the matrix specified by each
   * transformation type and the pixel's current channels. The resulting RGB values of a pixel are a
   * linear combination of its previous RGB values with the transformation matrix. All values are
   * clamped.
   *
   * @return the resulting transformed image
   * @throws IllegalArgumentException if the given type is null
   */
  IGrid colorTransform(TransformationType t) throws IllegalArgumentException;

  /**
   * Gets this grid's 2d array of pixels.
   *
   * @return the 2d array of pixels
   */
  Pixel[][] getPixels();


  /**
   * Creates a deep copy of this grid.
   *
   * @return the deep copy
   */
  IGrid copyGrid();

  /**
   * Returns a string representation of this grid as a ppm file, including the "P3", width and
   * height values, and max color value (255) of the image. Every RGB value of every pixel of this
   * grid is on a new line.
   *
   * @return String representation of PPM
   */
  String convertGridToPPM();

  /**
   * Calculates the sum of all of a cell's neighbors.
   *
   * @param gridPixelsWithPadding the padded grid of pixels
   * @param kernel                the filter kernel
   * @param row                   the row of the pixel
   * @param col                   the column of the pixel
   * @return the pixel of sums
   */
  Pixel calcNeighboringSum(Pixel[][] gridPixelsWithPadding, double[][] kernel, int row, int col);
}

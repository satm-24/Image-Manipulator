package model;

import static model.ImageProcessingUtils.checkNotNull;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an image and all of its pixels as an array of pixels, while also recording its height
 * and width.
 */
public class ImageGrid implements IGrid {


  // this was made protected because we have to modify pixel colors from the default (black) when
  // taking in images.
  protected Pixel[][] pixels;


  private final int width;
  private final int height;

  /**
   * Constructs a grid objects with a nested array of pixels organized by column, row. Initializes
   * our array of pixels to an all-black pixel grid.
   *
   * @param width  the grid's width
   * @param height the grid's height
   */
  public ImageGrid(Pixel[][] pixels, int width, int height) {

    checkNotNull(pixels, "Pixels cannot be null");

    this.pixels = pixels;
    this.width = width;
    this.height = height;
  }
  

  @Override
  public String convertGridToPPM() {

    StringBuilder res = new StringBuilder();

    res.append("P3\n");

    res.append(this.width).append(" ").append(this.height).append("\n");

    res.append("255\n");

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {

        res.append(this.pixels[i][j].convertRGBToStr());
      }
    }

    return res.toString();

  }


  @Override
  public IGrid filter(FilterType ft) throws IllegalArgumentException {

    checkNotNull(ft, "The filter type cannot be null.");

    double[][] kernel = ft.getConversionMatrix();

    int paddingVal = kernel.length / 2;

    Pixel[][] transformedPxls = new Pixel[this.height][this.width];

    ImageGrid transformedImg = new ImageGrid(transformedPxls, this.width, this.height);

    Pixel[][] oldPixels = new Pixel[height][width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        oldPixels[i][j] = new Pixel(this.pixels[i][j].getClr().copy());
      }
    }

    Pixel[][] gridPixelsWithPadding = new Pixel[this.height + paddingVal * 2][this.width
        + paddingVal * 2];

    // separate indices for old grid, it has no padding
    int oldRow = 0;
    int oldCol = 0;

    populateGridWithPadding(paddingVal, oldPixels, gridPixelsWithPadding, oldRow, oldCol);

    initGridPixelsWithPaddingIfNull(gridPixelsWithPadding);

    transformFilteredPixels(kernel, transformedPxls, gridPixelsWithPadding);

    return transformedImg;
  }

  /**
   * Transforms pixeling using filtering formula and ads them to new pixel grid.
   *
   * @param kernel                what we use for filter
   * @param transformedPxls       new pixel grid
   * @param gridPixelsWithPadding old grid we're getting pixels from
   */
  private void transformFilteredPixels(double[][] kernel, Pixel[][] transformedPxls,
      Pixel[][] gridPixelsWithPadding) {
    for (int i = 0; i < transformedPxls.length; i++) {
      for (int j = 0; j < transformedPxls[0].length; j++) {

        transformedPxls[i][j] = calcNeighboringSum(gridPixelsWithPadding, kernel, i, j);

      }
    }
  }

  /**
   * Inits the grid pixels with padding to change nulls to pixels.
   *
   * @param gridPixelsWithPadding the pixels we are changign
   */
  private void initGridPixelsWithPaddingIfNull(Pixel[][] gridPixelsWithPadding) {
    for (int i = 0; i < gridPixelsWithPadding.length; i++) {
      for (int j = 0; j < gridPixelsWithPadding[0].length; j++) {

        if (gridPixelsWithPadding[i][j] == null) {
          gridPixelsWithPadding[i][j] = new Pixel();
        }

      }
    }
  }

  /**
   * Populate values from old grid to padded grid.
   *
   * @param paddingVal            how much padding we have
   * @param oldPixels             old grid
   * @param gridPixelsWithPadding new grid
   * @param oldRow                row index of old grid
   * @param oldCol                col index of old grid
   */
  private void populateGridWithPadding(int paddingVal, Pixel[][] oldPixels,
      Pixel[][] gridPixelsWithPadding, int oldRow, int oldCol) {
    for (int i = paddingVal; i < gridPixelsWithPadding.length - paddingVal; i++) {
      for (int j = paddingVal; j < gridPixelsWithPadding[0].length - paddingVal; j++) {

        gridPixelsWithPadding[i][j] = oldPixels[oldRow][oldCol];

        oldCol++;

      }
      oldCol = 0;
      oldRow++;
    }
  }

  /**
   * Calculates the sum of the current pixel and all its neighbors.
   *
   * @param gridPixelsWithPadding the padded grid of pixels
   * @param kernel                the filter kernel
   * @param row                   the row of the pixel
   * @param col                   the column of the pixel
   * @return the pixel with its respective r,g,b sums as ints.
   */
  public Pixel calcNeighboringSum(Pixel[][] gridPixelsWithPadding, double[][] kernel, int row,
      int col) {

    double redSum = 0;
    double blueSum = 0;
    double greenSum = 0;

    for (int i = 0; i < kernel.length; i++) {
      for (int j = 0; j < kernel[0].length; j++) {

        int updatedY = i + row;
        int updatedX = j + col;

        redSum +=
            gridPixelsWithPadding[updatedY][updatedX].getClrAsDoubleVector()[0][0] * kernel[i][j];
        greenSum +=
            gridPixelsWithPadding[updatedY][updatedX].getClrAsDoubleVector()[1][0] * kernel[i][j];
        blueSum +=
            gridPixelsWithPadding[updatedY][updatedX].getClrAsDoubleVector()[2][0] * kernel[i][j];


      }
    }

    redSum = clampValue(redSum);
    greenSum = clampValue(greenSum);
    blueSum = clampValue(blueSum);

    return new Pixel(new Color((int) redSum, (int) greenSum, (int) blueSum));
  }

  /**
   * Clamps a given value to the 0 or 255, if less than or greater than the extreme values.
   *
   * @param num the values
   * @return the clamped value
   */
  private double clampValue(double num) {
    if (num > 255) {
      num = 255;
    }

    if (num < 0) {
      num = 0;
    }
    return num;
  }

  @Override
  public IGrid colorTransform(TransformationType t) throws IllegalArgumentException {
    checkNotNull(t, "The transformation type cannot be null.");

    double[][] transformation = t.getConversionMatrix();

    Pixel[][] transformedPixels = new Pixel[this.height][this.width];

    ImageGrid transformedImg = new ImageGrid(transformedPixels, this.width, this.height);

    Pixel[][] oldPixels = this.copyGrid().getPixels();

    for (int i = 0; i < oldPixels.length; i++) {
      for (int j = 0; j < oldPixels[0].length; j++) {

        transformedPixels[i][j] = multiplyMatrices(transformation,
            oldPixels[i][j].getClrAsDoubleVector());

      }
    }

    return transformedImg;
  }

  /**
   * Multiplies a given matrix by another vector.
   *
   * @param matrix            the 3x3 transformation matrix
   * @param clrAsDoubleVector the 3x1 rgb color vector
   * @return the resulting rgb color vector
   */
  private Pixel multiplyMatrices(double[][] matrix, double[][] clrAsDoubleVector) {

    double[][] result = new double[3][1];

    for (int i = 0; i < 3; i++) {
      for (int k = 0; k < 3; k++) {

        result[i][0] += matrix[i][k] * clrAsDoubleVector[k][0];

        // clamps rgb values if > 255 or < 0

        result[i][0] = clampValue(result[i][0]);

      }
    }

    return new Pixel(new Color((int) result[0][0], (int) result[1][0], (int) result[2][0]));

  }

  @Override
  public Pixel[][] getPixels() {
    return pixels;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof ImageGrid)) {
      return false;
    }

    ImageGrid g = (ImageGrid) o;
    return Arrays.deepEquals(this.pixels, g.pixels) && this.width == g.width
        && this.height == g.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(Arrays.deepHashCode(this.pixels), this.height, this.width);
  }

  @Override
  public IGrid copyGrid() {
    Pixel[][] copy = new Pixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      System.arraycopy(this.pixels[i], 0, copy[i], 0, this.width);
    }
    return new ImageGrid(copy, this.width, this.height);
  }
}
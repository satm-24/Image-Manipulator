package model;

/**
 * Represents the different types of filters (blur, sharpen, etc.) that can be applied to an image.
 */
public enum FilterType {

  BLUR(createBlurMatrix()), SHARPEN(createSharpenMatrix());


  private final double[][] conversionMatrix;

  /**
   * Constructors an object of FilterType with a certain conversion matrix associated with the
   * desired effect.
   *
   * @param conversionMatrix the matrix we can multiply by to apply the filter to an image
   */
  FilterType(double[][] conversionMatrix) {
    this.conversionMatrix = conversionMatrix;
  }

  /**
   * Returns the matrix to be used for blurring.
   *
   * @return the blurring matrix
   */
  private static double[][] createBlurMatrix() {
    double[][] matrix = new double[3][3];

    matrix[0][0] = 0.0625;
    matrix[0][2] = 0.0625;
    matrix[2][0] = 0.0625;
    matrix[2][2] = 0.0625;

    matrix[0][1] = 0.125;
    matrix[1][0] = 0.125;
    matrix[1][2] = 0.125;
    matrix[2][1] = 0.125;

    matrix[1][1] = 0.25;

    return matrix;
  }

  /**
   * Returns the matrix to be used for sharpening images.
   *
   * @return the sharpen matrix
   */
  private static double[][] createSharpenMatrix() {
    double[][] matrix = new double[5][5];

    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {

        if (i == 0 || i == 4 || j == 0 || j == 4) {
          matrix[i][j] = -0.125;
        } else if (!(i == 2 && j == 2)) {
          matrix[i][j] = 0.25;
        } else {
          matrix[i][j] = 1;
        }

      }
    }

    return matrix;
  }

  /**
   * Returns the matrix to be used for custom filters.
   *
   * @return the custom filter matrix
   */
  protected double[][] getConversionMatrix() {
    return conversionMatrix;
  }
}

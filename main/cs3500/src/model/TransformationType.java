package model;

/**
 * Represents the different types of color transformations that can be applied to images.
 */
public enum TransformationType {


  GREYSCALE(createGreyscaleMatrix()), SEPIA(createSepiaMatrix());


  private final double[][] conversionMatrix;

  /**
   * Constructs a color transformation type, given its conversion matrix.
   *
   * @param conversionMatrix the conversion matrix
   */
  TransformationType(double[][] conversionMatrix) {
    this.conversionMatrix = conversionMatrix;
  }

  protected double[][] getConversionMatrix() {
    return conversionMatrix;
  }

  /**
   * Creates a 3x3 matrix with the values for the greyscale color transformation.
   *
   * @return the desired 3x3 transformation matrix
   */
  private static double[][] createGreyscaleMatrix() {
    double[][] matrix = new double[3][3];

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {

        if (j == 0) {
          matrix[i][j] = 0.2126;
        } else if (j == 1) {
          matrix[i][j] = 0.7152;
        } else {
          matrix[i][j] = 0.0722;
        }

      }
    }

    return matrix;
  }

  /**
   * Creates a 3x3 matrix with the values for the sepia color transformation.
   *
   * @return the desired 3x3 transformation matrix
   */
  private static double[][] createSepiaMatrix() {
    double[][] matrix = new double[3][3];

    double[] sepiaColors = new double[]{0.393, 0.349, 0.272, 0.769, 0.686, 0.534, 0.189, 0.168,
        0.131};

    int count = 0;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {

        matrix[j][i] = sepiaColors[count];
        count++;
      }
    }

    return matrix;
  }


}

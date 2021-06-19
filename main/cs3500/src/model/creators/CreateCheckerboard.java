package model.creators;

import java.util.List;
import model.Color;
import model.IGrid;
import model.ImageGrid;
import model.Pixel;

/**
 * Represents a function object for creating a checkerboard image. The square size and number of
 * squares are specified by the function object's fields that are instantiated upon creation. The
 * colors are also specified which there can only be two, that alternate in the image.
 */
public class CreateCheckerboard implements ICreator {

  private final int squareSize;
  private final int numSquares;
  private final List<Color> colors;

  /**
   * Creates an instance of a create checkerboard function object given the size of squares in
   * pixels, the number of squares and a list of colors to be included. Throws an exception if the
   * square size or number of squares is less than 1 or if the list of colors is null.
   *
   * @param squareSize the size of each checkerboard square in pixels
   * @param numSquares the number of squares in every row/column of a checkerboard
   * @param colors     a list of colors to be included
   */
  public CreateCheckerboard(int squareSize, int numSquares, List<Color> colors) {
    if (squareSize <= 0 || numSquares <= 0) {
      throw new IllegalArgumentException("Square size and number of squares must be greater than "
          + "0.");
    } else if (colors == null) {
      throw new IllegalArgumentException("The list of colors cannot be null.");
    } else if (colors.size() != 2) {
      throw new IllegalArgumentException("The list of colors must be of length 2.");
    }
    this.squareSize = squareSize;
    this.numSquares = numSquares;
    this.colors = colors;
  }

  /**
   * Generates an image of a checkerboard, where each square shares its color with the square to its
   * bottom right and its squares to the left and right are different color. The number of squares,
   * the square size and the possible colors are specified by the client.
   *
   * @return the checkerboard image
   */
  public IGrid apply() {
    int dimension = squareSize * numSquares;
    Pixel[][] pixelGrid = new Pixel[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        int rowIndex = (i / squareSize) % 2;
        int colIndex = (j / numSquares) % 2;
        if (rowIndex == colIndex) {
          pixelGrid[i][j] = new Pixel(colors.get(0));
        } else {
          pixelGrid[i][j] = new Pixel(colors.get(1));
        }
      }
    }
    return new ImageGrid(pixelGrid, dimension, dimension);
  }
}

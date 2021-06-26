package model.operations;

import static model.ImageProcessingUtils.checkNotNull;

import model.IGrid;
import model.TransformationType;

/**
 * Represents a function object for transforming the color of an image. For each pixel, a vector of
 * its RGB values are multiplied by the transformation matrix to determine the resulting RGB
 * values.
 */
public class ColorTransformation implements IOperation {

  private final TransformationType type;

  /**
   * Constructs an instance of a color transformation function object given a transformation type.
   *
   * @param type the type of transformation to apply to the image
   */
  public ColorTransformation(TransformationType type) {
    checkNotNull(type, "Invalid transformation type.");
    this.type = type;
  }

  @Override
  public IGrid apply(IGrid grid) {
    checkNotNull(grid, "The image grid cannot be null.");
    return grid.colorTransform(type);
  }
}

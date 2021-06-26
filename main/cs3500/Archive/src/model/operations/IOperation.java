package model.operations;

import model.IGrid;

/**
 * Represents different function objects for image transformations. All function objects contain
 * apply methods that performs the transformation on the given shape.
 */
public interface IOperation {

  /**
   * Applies the transformation to the given image. Makes a copy of the given image than returns the
   * mutated copy with the applied transformation to it.
   *
   * @param image the image we are performing the operation on.
   * @return the transformed image
   */
  IGrid apply(IGrid image);
}

package model;

import model.creators.ICreator;
import model.operations.IOperation;

/**
 * Represents different image processing classes, dependent on the file type. Allows for images to
 * be filtered, transformed and created.
 */
public interface IProcessingImageModel {

  /**
   * Returns a copy of the image at the specified index.
   *
   * @param index the index of the image
   * @return the image at the index
   */
  IGrid getImageAt(int index);

  /**
   * Performs the image operation on the image specified by the index in the list of images in the
   * model.
   *
   * @param op the function object of the operation
   * @return the resulting image
   */
  IGrid operate(IOperation op);

  /**
   * Creates an image according to the given creator function object.
   *
   * @param cr the function object of the creator
   * @return the resulting image
   */
  IGrid create(ICreator cr);

  /**
   * Returns the number of images within the model.
   *
   * @return the number of images
   */
  int getImagesSize();

  /**
   * Adds the given image to the end of the model's list of images.
   *
   * @param image the image to be added
   */
  void add(IGrid image);
}

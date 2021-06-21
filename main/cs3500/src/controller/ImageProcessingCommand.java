package controller;

import model.IProcessingImageModel;

/**
 * Represents an interface of commands that can be passed in to our image processing program.
 */
public interface ImageProcessingCommand {


  /**
   * Performs an operation on the given image processing model, which include loading/saving,
   * filtering and transforming images.
   *
   * @param m the image processing model to be operated on
   */
  void execute(IProcessingImageModel m, IProcessingController controller);
}

package controller;

import model.ImageProcessingUtils;
import model.TransformationType;
import model.IProcessingImageModel;
import model.operations.ColorTransformation;

/**
 * Represents a command to transform an image to sepia.
 */
class TransformSepia implements ImageProcessingCommand {

  @Override
  public void execute(IProcessingImageModel m ) {
    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");
    m.operate(new ColorTransformation(TransformationType.SEPIA));
  }
}

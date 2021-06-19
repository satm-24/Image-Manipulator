package controller;

import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import model.TransformationType;
import model.operations.ColorTransformation;

/**
 * Represents a command to transform the model to greyscale.
 */
 class TransformGreyscale implements ImageProcessingCommand {

  @Override
  public void execute(IProcessingImageModel m ) {
    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");
    m.operate(new ColorTransformation(TransformationType.GREYSCALE));
  }
}

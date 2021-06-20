package controller;

import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import model.TransformationType;
import model.operations.ColorTransformation;
import view.Layer;
import view.ILayer;

/**
 * Represents a command to transform the model to greyscale.
 */
 class TransformGreyscale implements ImageProcessingCommand {

  @Override
  public void execute(IProcessingImageModel m, ILayer current) {
    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");
    current = new Layer(current.getVisibility(), m.operate(new ColorTransformation(TransformationType.GREYSCALE)),
        current.getName());
  }
}

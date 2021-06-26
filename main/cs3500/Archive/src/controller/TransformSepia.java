package controller;

import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import model.TransformationType;
import model.operations.ColorTransformation;
import view.ILayer;
import view.Layer;

/**
 * Represents a command to transform an image to sepia.
 */
class TransformSepia implements ImageProcessingCommand {

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {
    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");
    ImageProcessingUtils.checkNotNull(controller, "Controller cannot be null.");
    ImageProcessingUtils.checkNotNull(controller.getCurrent(), "Current cannot be null.");
    ILayer current = new Layer(controller.getCurrent().getVisibility(),
        m.operate(new ColorTransformation(TransformationType.SEPIA)), controller.getCurrent()
        .getName());
    controller.setCurrentInLayers(current);
    controller.setCurrent(current);
  }
}

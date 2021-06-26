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

    if (controller.checkNullCurrent()) {
      return;
    }

    if (controller.getCurrent().getFileLocation().equals("")) {
      controller.renderMessageToView("There are no image to transform."
          + " Please populate a layer. "
          + "\n");
      return;
    }

    m.add(controller.getCurrent().getImage());

    ILayer current = new Layer(controller.getCurrent().getVisibility(),
        m.operate(new ColorTransformation(TransformationType.SEPIA)), controller.getCurrent()
        .getName());

    current.setFileLocation(controller.getCurrent().getFileLocation());

    controller.setCurrentInLayers(current);
    controller.setCurrent(current);

    controller.renderMessageToView("Applied sepia filter to layer: \"" + current.getName() +
        "\" \n");

  }
}

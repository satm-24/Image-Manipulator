package controller;

import model.IProcessingImageModel;
import model.ImageProcessingUtils;

/**
 * Represents a command to remove a layer.
 */
public class RemoveLayer implements ImageProcessingCommand {

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");
    ImageProcessingUtils.checkNotNull(controller, "Controller cannot be null.");
    ImageProcessingUtils.checkNotNull(controller.getCurrent(), "Current cannot be null.");

    if (controller.getLayers().size() == 0) {
      throw new IllegalArgumentException("The list of layers is empty!");
    }

    controller.removeCurrent();
    m.remove(controller.getCurrent().getImage());
  }
}

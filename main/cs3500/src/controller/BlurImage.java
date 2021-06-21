package controller;

import model.FilterType;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import model.operations.Filter;
import view.ILayer;
import view.Layer;

/**
 * Represents a command to blur an image using the default blur kernel.
 */
public class BlurImage implements ImageProcessingCommand {

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");
    ImageProcessingUtils.checkNotNull(controller, "Controller cannot be null.");
    ImageProcessingUtils.checkNotNull(controller.getCurrent(), "Current cannot be null.");

    if (controller.getCurrent().getFileLocation().equals("")) {
      controller.tryToRenderError("There are no layers to blur.");
      return;
    }

    ILayer current = new Layer(controller.getCurrent().getVisibility(),
        m.operate(new Filter(FilterType.BLUR)), controller.getCurrent().getName());

    controller.setCurrentInLayers(current);
    controller.setCurrent(current);
  }
}

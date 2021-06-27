package controller;

import model.FilterType;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import model.operations.Filter;
import view.ILayer;
import view.Layer;

/**
 * Represents a command to sharpen an image using the default sharpen kernel.
 */
public class SharpenImage implements ImageProcessingCommand {

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {
    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");
    ImageProcessingUtils.checkNotNull(controller, "Controller cannot be null.");
    ImageProcessingUtils.checkNotNull(controller.getCurrent(), "Current cannot be null.");
    ILayer current = new Layer(controller.getCurrent().getVisibility(),
        m.operate(new Filter(FilterType.SHARPEN)), controller.getCurrent().getName());
    controller.setCurrentInLayers(current);
    controller.setCurrent(current);
  }
}

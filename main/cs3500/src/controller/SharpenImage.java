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

    if (controller.checkNullCurrent()) {
      return;
    }

    if (controller.getCurrent().getFileLocation().equals("")) {
      controller
          .renderMessageToView("There are no image to sharpen." + " Please populate a layer. \n");
      return;
    }

    m.add(controller.getCurrent().getImage());

    ILayer current = new Layer(controller.getCurrent().getVisibility(),
        m.operate(new Filter(FilterType.SHARPEN)), controller.getCurrent().getName());

    current.setFileLocation(controller.getCurrent().getFileLocation());

    controller.setCurrent(current);

    controller.renderMessageToView("Sharpened layer: \"" + current.getName() + "\" \n");

  }
}

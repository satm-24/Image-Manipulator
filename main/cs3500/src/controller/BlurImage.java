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

    if (controller.checkNullCurrent()) {
      return;
    }

    if (controller.getCurrent().getFileLocation().equals("")) {
      controller
          .renderMessageToView("There are no image to blur." + " Please populate a layer. \n");
      return;
    }

    m.add(controller.getCurrent().getImage());

    ILayer current = new Layer(controller.getCurrent().getVisibility(),
        m.operate(new Filter(FilterType.BLUR)), controller.getCurrent().getName());

    current.setFileLocation(controller.getCurrent().getFileLocation());

    controller.setCurrentInLayers(current);
    controller.setCurrent(current);

    controller.renderMessageToView("Blurred layer: \"" + current.getName() + "\" \n");


  }
}

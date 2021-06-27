package controller;

import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import model.TransformationType;
import model.operations.ColorTransformation;
import view.ILayer;
import view.Layer;

/**
 * Represents a command to transform the model to greyscale.
 */
class TransformGreyscale implements ImageProcessingCommand {

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    if (checkNullsAndEmptyFiles(m, controller)) {
      return;
    }

    m.add(controller.getCurrent().getImage());

    ILayer current = new Layer(controller.getCurrent().getVisibility(),
        m.operate(new ColorTransformation(TransformationType.GREYSCALE)),
        controller.getCurrent().getName());

    current.setFileLocation(controller.getCurrent().getFileLocation());

    controller.setCurrent(current);

    controller
        .renderMessageToView("Applied greyscale filter to layer: \"" + current.getName() + "\" \n");

  }


  /**
   * Checks for nulls or an empty file loc and quits the execute method.
   *
   * @param m          given model
   * @param controller the given controller
   * @return whether we want to exit or not
   */
  protected static boolean checkNullsAndEmptyFiles(IProcessingImageModel m,
      IProcessingController controller) {
    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");
    ImageProcessingUtils.checkNotNull(controller, "Controller cannot be null.");

    if (controller.checkNullCurrent()) {
      return true;
    }

    if (controller.getCurrent().getFileLocation().equals("")) {
      controller.renderMessageToView(
          "There are no image to transform." + " Please populate a layer. " + "\n");
      return true;
    }
    return false;
  }
}

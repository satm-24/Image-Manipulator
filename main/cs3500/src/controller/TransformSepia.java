package controller;

import static controller.TransformGreyscale.checkNullsAndEmptyFiles;

import model.IProcessingImageModel;
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

    if (checkNullsAndEmptyFiles(m, controller)) {
      return;
    }

    m.add(controller.getCurrent().getImage());

    ILayer current = new Layer(controller.getCurrent().getVisibility(),
        m.operate(new ColorTransformation(TransformationType.SEPIA)),
        controller.getCurrent().getName());

    current.setFileLocation(controller.getCurrent().getFileLocation());

    controller.setCurrent(current);

    controller
        .renderMessageToView("Applied sepia filter to layer: \"" + current.getName() + "\" \n");

  }

}

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
  public void execute(IProcessingImageModel m, ILayer current) {

    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");

    current = new Layer(current.getVisibility(), m.operate(new Filter(FilterType.SHARPEN)),
        current.getName(), fileLocation);
  }
}

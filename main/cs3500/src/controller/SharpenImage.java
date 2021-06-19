package controller;

import model.FilterType;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import model.operations.Filter;

/**
 * Represents a command to sharpen an image using the default sharpen kernel.
 */
public class SharpenImage implements ImageProcessingCommand {

  @Override
  public void execute(IProcessingImageModel m) {

    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");

    m.operate(new Filter(FilterType.SHARPEN));
  }
}

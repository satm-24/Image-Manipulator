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
  public void execute(IProcessingImageModel m, ILayer current) {
    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");

    current = new Layer(current.getVisibility(), m.operate(new Filter(FilterType.BLUR)),
            current.getName());
  }
}

package controller;

import java.io.File;
import java.util.List;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import view.ILayer;

public class LoadImage implements ImageProcessingCommand {

  private File location;
  private List<ILayer> layers;

  /**
   * Constructs an object of loadimage.
   * @param location the file location we are loading an image from.
   * @param layers represents this program's list of layers
   */
  public LoadImage(File location, List<ILayer> layers) {
    ImageProcessingUtils.checkNotNull(layers, "List of layers cannot be null.");
    ImageProcessingUtils.checkNotNull(location, "File loc cannot be null.");

    this.location = location;
    this.layers = layers;
  }

  @Override
  public void execute(IProcessingImageModel m) {

  }
}

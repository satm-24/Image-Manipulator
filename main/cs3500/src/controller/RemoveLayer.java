package controller;

import java.util.List;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import view.ILayer;

/**
 * Represents a command to remove a layer.
 */
public class RemoveLayer implements ImageProcessingCommand {

  private String name;
  private List<ILayer> layers;

  /**
   * Constructs an RemoveLayer object that is named.
   * @param name the same of the layer we are removing
   * @param layers represents this program's list of layers
   */
  public RemoveLayer(String name, List<ILayer> layers) {

    ImageProcessingUtils.checkNotNull(layers, "List of layers cannot be null.");
    ImageProcessingUtils.checkNotNull(name, "Name cannot be null.");

    this.name = name;
    this.layers = layers;
  }


  @Override
  public void execute(IProcessingImageModel m) {

    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");

    if (this.layers.size() == 0) {
      throw new IllegalArgumentException("The list of layers is empty!");
    }

    this.layers.remove(this.layers.size() - 1);
  }
}

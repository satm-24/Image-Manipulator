package controller;

import java.util.List;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import view.ILayer;

/**
 * Represents a command to remove a layer.
 */
public class RemoveLayer implements ImageProcessingCommand {

  private final List<ILayer> layers;

  /**
   * Constructs an RemoveLayer object that is named.
   * @param layers represents this program's list of layers
   */
  public RemoveLayer( List<ILayer> layers) {

    ImageProcessingUtils.checkNotNull(layers, "List of layers cannot be null.");

    this.layers = layers;
  }


  @Override
  public void execute(IProcessingImageModel m, ILayer current) {

    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");

    if (this.layers.size() == 0) {
      throw new IllegalArgumentException("The list of layers is empty!");
    }

    this.layers.remove(current);
  }
}

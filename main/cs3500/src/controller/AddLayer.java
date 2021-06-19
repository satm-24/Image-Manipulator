package controller;

import java.util.List;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import view.ILayer;
import view.Layer;

/**
 * Represents a command to add a layer.
 */
public class AddLayer implements ImageProcessingCommand {

  private String name;
  private List<ILayer> layers;

  /**
   * Constructs an AddLayer object that is named.
   *
   * @param name the same of the layer we are creating
   * @param layers represents this program's list of layers
   */
  public AddLayer(String name, List<ILayer> layers) {

    ImageProcessingUtils.checkNotNull(layers, "List of layers cannnot be null.");
    ImageProcessingUtils.checkNotNull(name, "Name cannnot be null.");

    this.name = name;
    this.layers = layers;
  }


  @Override
  public void execute(IProcessingImageModel m) {



    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");

    this.layers.add(new Layer(true, this.name));
  }
}

package controller;

import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import view.ILayer;
import view.Layer;

/**
 * Represents a command to add a layer.
 */
public class AddLayer implements ImageProcessingCommand {

  private String name;

  /**
   * Constructs an AddLayer object that is named.
   *
   * @param name the same of the layer we are creating
   */
  public AddLayer(String name) {
    ImageProcessingUtils.checkNotNull(name, "Name cannnot be null.");
    this.name = name;
  }


  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    ImageProcessingUtils.checkNotNull(m, "Model cannot be null.");

    ILayer newLayer = new Layer(true, this.name);

    for (ILayer layer : controller.getLayers()) {
      if (layer.getName().equals(this.name)) {
        controller.renderMessageToView("Sorry, there is already a layer with this name!\n");
        return;
      }
    }

    controller.getLayers().add(newLayer);
    controller.setCurrent(newLayer);

    m.add(newLayer.getImage());

    controller.renderMessageToView("Created new layer: \"" + newLayer.getName() + "\" \n");
  }
}

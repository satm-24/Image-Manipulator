package controller;

import java.util.List;
import view.ILayer;

/**
 * Represents an interface for controllers in our image processing software.
 */
public interface IProcessingController {

  /**
   * Parses user input and directs the controller accordingly.
   */
  void parseInput();

  /**
   * Gets this controller's list of layers.
   *
   * @return the list of layers
   */
  List<ILayer> getLayers();

  /**
   * Gets the current layer.
   *
   * @return the current layer
   */
  ILayer getCurrent();

  /**
   *
   * @return
   */
  int getHeight();

  /**
   *
   * @return
   */
  int getWidth();

  /**
   * Sets the current layer to the given layer.
   *
   * @param current the layer to be set to current
   */
  void setCurrent(ILayer current);

  /**
   * Sets the current layer in the list of layers.
   */
  void setCurrentInLayers(ILayer layer);

  /**
   *
   * @param newLayer
   */
  void setLayer(ILayer newLayer);

  /**
   * Removes the current layer in the list of layers.
   */
  void removeCurrent();


  /**
   * Delegates error message to view.
   *
   * @param msg error msg.
   */
  void renderMessageToView(String msg);

  /**
   *
   * @return
   */
  boolean checkNullCurrent();
}

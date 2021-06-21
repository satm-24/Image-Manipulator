package controller;

import java.util.List;
import java.util.Optional;
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
   *
   * @return
   */
  ILayer getCurrent();

  /**
   *
   * @param current
   */
  void setCurrent(ILayer current);

  /**
   * Sets the current layer in the list of layers.
   */
  void setCurrentInLayers(ILayer layer);

  /**
   * Removes the current layer in the list of layers.
   */
  void removeCurrent();


  /**
   * Delegates error message to view.
   *
   * @param msg error msg.
   */
  void tryToRenderError(String msg);

}

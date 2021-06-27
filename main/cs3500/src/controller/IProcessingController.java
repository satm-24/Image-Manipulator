package controller;

import java.util.List;
import view.ILayer;
import view.IProcessingImageView;

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
   * Gets the height of the first image which all other images should conform to.
   *
   * @return common height
   */
  int getHeight();

  /**
   * Gets the width of the first image which all other images should conform to.
   *
   * @return common width
   */
  int getWidth();

  /**
   * Sets the height.
   *
   * @param height the height we want to set to
   */
  void setHeight(int height);

  /**
   * Sets the width.
   *
   * @param width width we want to set to
   */
  void setWidth(int width);

  /**
   * Sets the current layer to the given layer.
   *
   * @param current the layer to be set to current
   */
  void setCurrent(ILayer current);

  /**
   * Sets the view.
   * @param view the view we want to set to
   */
  void setView(IProcessingImageView view);


  /**
   * Sets the layer with the same name as the given layer to the given layer.
   *
   * @param newLayer the layer to set to
   */
  void setLayerWithSameName(ILayer newLayer);

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
   * Checks if the current layer is null.
   *
   * @return if the current layer is null
   */
  boolean checkNullCurrent();

  /**
   * Returns the last layer that is visible, if any.
   *
   * @return the last visible layer
   */
  ILayer getLastVisible();
}

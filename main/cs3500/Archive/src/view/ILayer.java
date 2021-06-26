package view;

import model.IGrid;

/**
 * Represents different types of layers in an image processing program.
 */
public interface ILayer {

  /**
   * Returns a new blank layer in the form of an entirely white IGrid.
   *
   * @return the empty layer
   */
  IGrid createBlankLayer();

  /**
   * Gets the visibility status of this layer.
   *
   * @return boolean representing the visibility
   */
  boolean getVisibility();

  /**
   * Gets this layer's image grid.
   *
   * @return grid with this layer's pixels
   */
  IGrid getImage();

  /**
   * Gets the name of the layer.
   *
   * @return the name
   */
  String getName();

  /**
   * Gets the location of the file.
   *
   * @return the file location
   */
  String getFileLocation();

  /**
   * Sets the file location to the given location.
   *
   * @param fileLocation the file location
   */
  void setFileLocation(String fileLocation);

  /**
   * Sets the visibility of the layer to the given boolean.
   *
   * @param visible whether or not the layer is visible
   */
  void setVisibility(boolean visible);
}

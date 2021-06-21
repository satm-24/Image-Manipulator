package view;

import java.io.File;
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
   *
   * @return
   */
  String getName();

  /**
   *
   * @return
   */
  String getFileLocation();

  /**
   *
   * @param fileLocation
   */
  void setFileLocation(String fileLocation);

  /**
   * Sets the visibility of the layer to the given boolean.
   *
   * @param visible whether or not the layer is visible
   */
  void setVisibility(boolean visible);
}

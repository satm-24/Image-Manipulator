package view;

import java.io.File;

/**
 * Represents the different types of event listeners for view.
 */
public interface IViewListener {

  /**
   * Handles the loading of an image for the image viewer.
   *
   * @param f the file to be loaded
   */
  void handleLoad(File f);

  /**
   * Handles the saving of an image for the image viewer.
   *
   * @param f file to be saved
   */
  void handleSave(File f);

  /**
   * Handles the blurring of an image for the image viewer.
   */
  void handleBlur();

  /**
   * Handles the sharpening of an image for the image viewer.
   */
  void handleSharpen();

  /**
   * Handles the sepia transformation of an image for the image viewer.
   */
  void handleSepia();

  /**
   * Handles the greyscale transformation of an image for the image viewer.
   */
  void handleGreyscale();

  /**
   * Handles the addition of a layer of a given name.
   *
   * @param name the name of the new layer
   */
  void handleAdd(String name);

  /**
   * Handles the removal of the current layer.
   */
  void handleRemove();

  /**
   * Handles the setting of a current layer to layer with the given string, if it exist.
   */
  void handleSetCurrent(String name);
}

package model.creators;

import model.IGrid;

/**
 * Represents function objects for programmatically creating objects. All function objects contain
 * the apply which creates an image of a corresponding file type and returns it.
 */
public interface ICreator {

  /**
   * Creates an instance of an image according to the function object type.
   *
   * @return the created image
   */
  IGrid apply();
}

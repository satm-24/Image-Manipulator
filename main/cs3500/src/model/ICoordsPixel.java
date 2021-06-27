package model;

/**
 * Represents pixels with coordinates. Assignment 7: This class was added for our 2 new commands to
 * keep track of pixel coordinates, as they originally only had a color.
 */
public interface ICoordsPixel extends IPixel {

  /**
   * Gets the Y coord.
   *
   * @return the y coordinate
   */
  int getY();

  /**
   * Gets the X coord.
   *
   * @return the x coordinate
   */
  int getX();

  /**
   * Gets the pixel.
   *
   * @return the pixel
   */
  IPixel getPixel();


}

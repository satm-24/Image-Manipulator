package model;

/**
 * Represents different representations of pixels of images and their respective methods.
 */
public interface IPixel {

  /**
   * Gets this pixel's color as a 3x1 vector of double values.
   *
   * @return the double vector
   */
  double[][] getClrAsDoubleVector();

  /**
   * Converts this pixel's color to a string of it's rgb values.
   *
   * @return the string of rgb values
   */
  String convertRGBToStr();

  /**
   * Returns the color of this pixel.
   *
   * @return the color of this pixel
   */
  Color getClr();

  /**
   * Creates a duplicate copy of the pixel, with a deep copy of the color.
   *
   * @return the duplicate copy
   */
  Pixel copy();

  // added this method for mosiacing

  /**
   *
   * @param clr
   */
  void setClr(Color clr);
}

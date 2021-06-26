package model;

/**
 * Represents different representations of colors and their respective methods.
 */
public interface IColor {

  /**
   * Converts this color's rgb values into a 3x1 vector in the format [[r],[g],[b]].
   *
   * @return the rgb vector
   */
  double[][] turnIntoDblVector();

  /**
   * Converts this colors rgb values to a string, each seperated by a new line.
   *
   * @return the string of rgb values
   */
  String rgbToStr();

  /**
   * Returns a duplicate copy of the color.
   *
   * @return the duplicate color
   */
  Color copy();

  /**
   * Returns an int of this color's rgb representation.
   *
   * @return the rgb int
   */
  int getRGB();

}

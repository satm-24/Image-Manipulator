package model;

import java.util.Objects;

/**
 * Represents a pixel in our image with a certain color.
 */
public class Pixel implements IPixel {


  private Color clr;

  /**
   * Default constructor for pixels that initializes the pixel to black.
   */
  public Pixel() {
    this.clr = new Color(0, 0, 0);
  }

  /**
   * Constructs an instance of a pixel given its color.
   *
   * @param clr the pixel's color
   */
  public Pixel(Color clr) {
    this.clr = clr;
  }

  @Override
  public String convertRGBToStr() {
    return this.clr.rgbToStr();
  }

  @Override
  public double[][] getClrAsDoubleVector() {
    return this.clr.turnIntoDblVector();
  }

  @Override
  public Color getClr() {
    return clr;
  }

  @Override
  public String toString() {
    return clr.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Pixel)) {
      return false;
    }
    Pixel p = (Pixel) o;
    return this.clr.equals(p.clr);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.clr);
  }

  @Override
  public Pixel copy() {
    return new Pixel(this.clr.copy());
  }

  @Override
  public void setClr(Color clr) {
    this.clr = clr;
  }
}



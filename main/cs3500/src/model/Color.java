package model;

import model.*;

import java.util.Objects;


/**
 * Represents a color with RGB values from 0-255, inclusive, with a greater value meaning brighter.
 */
public class Color implements IColor {

  private final int red;
  private final int green;
  private final int blue;

  /**
   * Constructs an object of color with R,G,B values.
   *
   * @param red   color's red value
   * @param green color's green value
   * @param blue  color's blue value
   */
  public Color(int red, int green, int blue) {
    if (red < 0) {
      this.red = 0;
    } else if (red > 255) {
      this.red = 255;
    } else {
      this.red = red;
    }

    if (green < 0) {
      this.green = 0;
    } else if (green > 255) {
      this.green = 255;
    } else {
      this.green = green;
    }

    if (blue < 0) {
      this.blue = 0;
    } else if (blue > 255) {
      this.blue = 255;
    } else {
      this.blue = blue;
    }
  }

  @Override
  public double[][] turnIntoDblVector() {
    return new double[][]{{this.red}, {this.green}, {this.blue}};
  }

  @Override
  public String rgbToStr() {
    StringBuilder res = new StringBuilder();

    res.append(this.red).append("\n");
    res.append(this.green).append("\n");
    res.append(this.blue).append("\n");

    return res.toString();
  }

  @Override
  public String toString() {
    return "Color{" + "red=" + red + ", green=" + green + ", blue=" + blue + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Color)) {
      return false;
    }
    Color that = (Color) o;
    return this.red == that.red
        && this.green == that.green
        && this.blue == that.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

  @Override
  public Color copy() {
    return new Color(this.red, this.green, this.blue);
  }

  @Override
  public int getRGB() {
    int rgb = 65536 * red + 256 * green + blue;
    return rgb;
  }
}

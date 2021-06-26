package model;

import java.util.Objects;

/**
 *
 */
public class PixelWithCoords extends Pixel implements ICoordsPixel {

  private final Pixel pixel;
  private final int x;
  private final int y;


  public Pixel getPixel() {
    return pixel;
  }

  @Override
  public String toString() {
    return "PixelWithCoords{" + "pixel=" + pixel.toString() + ", x=" + x + ", y=" + y + '}';
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  /**
   *
   * @param pixel
   * @param x
   * @param y
   */
  public PixelWithCoords(Pixel pixel, int x, int y) {
    this.pixel = pixel;
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PixelWithCoords pixel1 = (PixelWithCoords) o;
    return x == pixel1.x && y == pixel1.y && Objects.equals(pixel, pixel1.pixel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), pixel, x, y);
  }
}

package view;

import model.Color;
import model.IGrid;
import model.ImageGrid;
import model.ImageProcessingUtils;
import model.Pixel;
import java.util.Objects;

/**
 * Represents a single layer in an image processing program, which can either be empty or a copy of
 * another layer. This layer can be modified with different image processing effects.
 */
public class Layer implements ILayer {

  private boolean visible = true;
  private final IGrid image;
  private final String name;

  /**
   * Constructs a Layer object that is not a copy of another, but a blank layer.
   *
   * @param visible whether this layer is visible or not
   * @param name    the layer name
   * @throws IllegalArgumentException if either parameter is null
   */
  public Layer(boolean visible, String name) {

    ImageProcessingUtils.checkNotNull(name, "Name cannot be null!");

    this.visible = visible;
    this.name = name;
    this.image = createBlankLayer();
  }


  /**
   * Constructs a layer object given an image to make a copy of and a boolean representing whether
   * this layer is visible or transparent.
   *
   * @param visible is this layer visible?
   * @param image   the image this layer is a copy of
   * @param name    represents the layer name
   * @throws IllegalArgumentException if either parameter is null
   */
  public Layer(boolean visible, IGrid image, String name) {

    ImageProcessingUtils.checkNotNull(image, "Image cannot be null!");
    ImageProcessingUtils.checkNotNull(name, "Name cannot be null!");

    this.visible = visible;
    this.image = image;
    this.name = name;
  }

  @Override
  public IGrid createBlankLayer() {

    int height;
    int width;

    if (image == null) {
      height = 100;
      width = 100;
    } else {
      height = image.getPixels().length;
      width = image.getPixels()[0].length;
    }

    Pixel[][] pxls = new Pixel[height][width];

    for (Pixel[] row : pxls) {
      for (Pixel p : row) {
        p = new Pixel(new Color(255, 255, 255));
      }
    }

    return new ImageGrid(pxls, width, height);
  }

  @Override
  public IGrid getImage() {
    return image;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean getVisibility() {
    return this.visible;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    else if (!(o instanceof Layer)) {
      return false;
    }
    Layer l = (Layer) o;
    return this.visible == l.visible
        && this.name.equals(l.name);
  }

  @Override
  public String toString() {
    return "Layer{" +
        "visible=" + visible +
        ", name='" + name + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.visible, this.name);
  }
}



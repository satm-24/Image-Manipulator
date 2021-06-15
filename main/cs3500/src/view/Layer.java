package view;

import model.IGrid;

/**
 * Represents a single layer in an image processing program, which can either be empty or a copy
 * of another layer. This layer can be modified with different image processing effects.
 */
public class Layer {

  private boolean visible;
  private IGrid image;

  /**
   * Constructs a blank layer that is either visible or transparent.
   */
  public Layer(boolean visible) {
    this.visible = visible;
    this.image = createBlankLayer();
  }



  /**
   * Constructs a layer object given an image to make a copy of and a boolean representing
   * whether this layer is visible or transparent.
   * @param visible is this layer visible?
   * @param image the image this layer is a copy of
   */
  public Layer(boolean visible, IGrid image) {
    this.visible = visible;
    this.image = image;
  }

  /**
   * Creates a blank layer that is all white.
   * @return
   */
  private IGrid createBlankLayer() {
  }

}

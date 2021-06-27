package view;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * Represents an interface for views in our image processing software.
 */
public interface IProcessingImageView {

  /**
   * Renders the given message the view.
   *
   * @param message the message
   */
  void renderMessage(String message);

  /**
   * Sets the view's image to the given image.
   *
   * @param image the image to be shown in the view
   */
  void setImage(ImageIcon image);

  /**
   * Adds the action listener for the viewer.
   *
   * @param listener
   */
  void addViewEventListener(IViewListener listener);

  /**
   * Directs the view's focus back to the frame.
   */
  void requestViewFocus();

  /**
   * Adds the given name to the layers that can be selected.
   *
   * @param name the name of the layer
   */
  void setUpLayerSelection(String name);

  /**
   * Clears the layers in the layer selection panel.
   */
  void clearLayerSelection();
}

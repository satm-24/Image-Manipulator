package view;

import java.io.IOException;
import javax.swing.ImageIcon;

/**
 * Represents a basic view for our image processing software, which prints out errors to the
 * console.
 */
public class TextView implements IProcessingImageView {

  private final Appendable out;

  /**
   * Constructs an object of our view with a model and an appendable object to add all our desired
   * view messages to.
   *
   * @param out the appendable object that we are appending our messages to
   */
  public TextView(Appendable out) {
    this.out = out;
  }

  @Override
  public void renderMessage(String message) {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("could not append to appendable");
    }
  }

  @Override
  public void setImage(ImageIcon image) {
  }

  @Override
  public void requestViewFocus() {
  }

  @Override
  public void setUpLayerSelection(String name) {
  }

  @Override
  public void addViewEventListener(IViewListener listener) {

  }

  @Override
  public void clearLayerSelection() {

  }
}
package view;

import java.io.IOException;

/**
 * Represents a basic view for our image processing software, which prints out errors to the
 * console.
 */
public class SimpleProcessingImageView implements IProcessingImageView {

  private final Appendable out;

  /**
   * Constructs an object of our view with a model and an appendable object to add all our desired
   * view messages to.
   *
   * @param out the appendable object that we are appending our messages to
   */
  public SimpleProcessingImageView(Appendable out) {
    this.out = out;
  }

  @Override
  public void renderError(String message) throws IOException {
    this.out.append(message);
  }
}

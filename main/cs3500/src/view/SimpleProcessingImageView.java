package view;

import java.io.IOException;
import model.IProcessingImageModel;

/**
 * Represents a basic view for our image processing software, which prints out errors to the
 * console
 */
public class SimpleProcessingImageView implements IProcessingImageView {

  private final IProcessingImageModel model;
  private final Appendable out;

  /**
   * Constructs an object of our view with a model and an appendable object to add all our desired
   * view messages to.
   *
   * @param model represents the model that corresponds to this view
   * @param out the appendable object that we are appending our messages to
   */
  public SimpleProcessingImageView(IProcessingImageModel model, Appendable out) {
    this.model = model;
    this.out = out;
  }


  @Override
  public void renderError(String message) throws IOException {
    this.out.append(message);
  }


}

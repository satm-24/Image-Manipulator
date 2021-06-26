package view;

import java.io.IOException;

/**
 * Represents an interface for views in our image processing software.
 */
public interface IProcessingImageView {

  /**
   * Renders errors throws by our controller to the view.
   *
   * @param message the error message
   */
  void renderError(String message) throws IOException;

}

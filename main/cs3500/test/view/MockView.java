package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

/**
 * A mock view used for testing.
 */
public class MockView implements IProcessingImageView {

  private final List<IViewListener> listeners;
  Appendable out;

  /**
   * Constructs a mock view with a given listener
   *
   * @param out the appendable
   */
  public MockView(Appendable out) {
    this.listeners = new ArrayList<>();
    this.out = out;
  }

  @Override
  public void renderMessage(String message) {
    try {
      out.append("message rendered");
    } catch (IOException e) {
      throw new IllegalArgumentException("could not append");
    }
  }

  @Override
  public void setImage(ImageIcon image) {
    try {
      out.append("image set");
    } catch (IOException e) {
      throw new IllegalArgumentException("could not append");
    }
  }

  @Override
  public void addViewEventListener(IViewListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public void requestViewFocus() {
    try {
      out.append("focus requested");
    } catch (IOException e) {
      throw new IllegalArgumentException("could not append");
    }
  }

  @Override
  public void setUpLayerSelection(String name) {
    try {
      out.append("layer selection set up");
    } catch (IOException e) {
      throw new IllegalArgumentException("could not append");
    }
  }

  @Override
  public void clearLayerSelection() {
    try {
      out.append("layer selection cleared");
    } catch (IOException e) {
      throw new IllegalArgumentException("could not append");
    }
  }

  /**
   * Handles all load events.
   */
  public void emitLoadEvent() {
    File f = new File("test");
    for (IViewListener listener: listeners) {
      listener.handleLoad(f);
    }
  }

  /**
   * Handles all save events.
   */
  public void emitSaveEvent() {
    File f = new File("test");
    for (IViewListener listener: listeners) {
      listener.handleSave(f);
    }
  }

  /**
   * Handles all blur events.
   */
  public void emitBlurEvent() {
    for (IViewListener listener: listeners) {
      listener.handleBlur();
    }
  }

  /**
   * Handles all sharpen events.
   */
  public void emitSharpenEvent() {
    for (IViewListener listener: listeners) {
      listener.handleSharpen();
    }
  }

  /**
   * Handles all sepia events.
   */
  public void emitSepiaEvent() {
    for (IViewListener listener: listeners) {
      listener.handleSepia();
    }
  }

  /**
   * Handles all greyscale events.
   */
  public void emitGreyscaleEvent() {
    for (IViewListener listener: listeners) {
      listener.handleGreyscale();
    }
  }

  /**
   * Handles all add layers events.
   */
  public void emitAddEvent() {
    String s = "layer name";
    for (IViewListener listener: listeners) {
      listener.handleAdd(s);
    }
  }

  /**
   * Handles all remove layers events.
   */
  public void emitRemoveEvent() {
    for (IViewListener listener: listeners) {
      listener.handleRemove();
    }
  }
}

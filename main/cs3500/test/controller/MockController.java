package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import view.ILayer;
import view.IProcessingImageView;
import view.IViewListener;

/**
 * Represents a mock controller used for testing.
 */
public class MockController implements IProcessingController, IViewListener {

  private final Appendable out;

  /**
   * Constructs a mock controller with a given appendable.
   *
   * @param out the appendable
   */
  public MockController(Appendable out) {
    this.out = out;
  }

  @Override
  public void parseInput() {
    try {
      out.append("input parsed");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public List<ILayer> getLayers() {
    try {
      out.append("layers");
      return null;
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public ILayer getCurrent() {
    try {
      out.append("current layer");
      return null;
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public int getHeight() {
    return 0;
  }

  @Override
  public int getWidth() {
    return 0;
  }

  @Override
  public void setHeight(int height) {
    // do nothing
  }

  @Override
  public void setWidth(int width) {
    // do nothing
  }

  @Override
  public void setCurrent(ILayer current) {
    try {
      out.append("current set");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void setView(IProcessingImageView view) {
    // do nothing
  }

  @Override
  public void setLayerWithSameName(ILayer newLayer) {

  }

  @Override
  public void removeCurrent() {
    try {
      out.append("current removed");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void renderMessageToView(String msg) {
    try {
      out.append("message rendered");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public boolean checkNullCurrent() {
    try {
      out.append("current checked");
      return true;
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public ILayer getLastVisible() {
    try {
      out.append("last visible");
      return null;
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void handleLoad(File f) {
    try {
      out.append("loaded");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void handleSave(File f) {
    try {
      out.append("saved");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void handleBlur() {
    try {
      out.append("blurred");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void handleSharpen() {
    try {
      out.append("sharpened");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void handleSepia() {
    try {
      out.append("sepia");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void handleGreyscale() {
    try {
      out.append("greyscale");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void handleAdd(String name) {
    try {
      out.append("added name");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void handleRemove() {
    try {
      out.append("removed");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }

  @Override
  public void handleSetCurrent(String name) {
    try {
      out.append("current set");
    } catch (IOException e) {
      throw new IllegalStateException("Could not append");
    }
  }
}

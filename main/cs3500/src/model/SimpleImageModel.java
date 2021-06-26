package model;

import java.util.ArrayList;
import java.util.List;
import model.creators.ICreator;
import model.operations.IOperation;

/**
 * Represents image processing functionality for PPM files. Contains methods that filter, transform
 * and create images in a PPM format.
 */
public class SimpleImageModel implements IProcessingImageModel {

  public final List<IGrid> images;

  /**
   * Constructs an instance of a PPMImage class, initializing the list of images to an empty list.
   */
  public SimpleImageModel() {
    this.images = new ArrayList<IGrid>();
  }

  @Override
  public IGrid getImageAt(int index) {
    if (this.images.size() < index) {
      throw new IllegalArgumentException("Index out of bounds.");
    }
    return this.images.get(index).copyGrid();
  }

  @Override
  public IGrid operate(IOperation op) {
    ImageProcessingUtils.checkNotNull(op, "The operation cannot be null.");
    if (this.images.size() == 0) {
      throw new IllegalArgumentException("The list of images is empty.");
    }
    IGrid result = op.apply(this.images.get(this.getImagesSize() - 1));
    this.images.add(result);
    return result;
  }

  @Override
  public IGrid create(ICreator cr) {
    ImageProcessingUtils.checkNotNull(cr, "The creator cannot be null.");
    IGrid result = cr.apply();
    this.images.add(result);
    return result;
  }

  @Override
  public int getImagesSize() {
    return this.images.size();
  }

  @Override
  public void add(IGrid image) {
    ImageProcessingUtils.checkNotNull(image, "The image cannot be null.");
    this.images.add(image);
  }

  @Override
  public void remove(IGrid image) {
    ImageProcessingUtils.checkNotNull(image, "The image cannot be null.");
    this.images.remove(image);
  }

}


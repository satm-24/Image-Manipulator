package model.operations;

import model.FilterType;
import model.IGrid;
import model.ImageProcessingUtils;

/**
 * Represents a function object that performs a filter transformation on an image, using a kernel
 * matrix.
 */
public class Filter implements IOperation {

  private final FilterType ft;


  /**
   * Constructs an instance of a filter function object given filter type representing what type of
   * filter to apply to the image.
   *
   * @param ft the filter type
   */
  public Filter(FilterType ft) {
    ImageProcessingUtils.checkNotNull(ft, "Filter type cannot be null.");

    this.ft = ft;
  }

  @Override
  public IGrid apply(IGrid source) {
    ImageProcessingUtils.checkNotNull(source, "Image cannot be null.");

    return source.filter(this.ft);
  }
}

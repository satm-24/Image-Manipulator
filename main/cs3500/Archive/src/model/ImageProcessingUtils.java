package model;

/**
 * ImageProcessingUtils class for methods such as error checks that are used throughout the World.
 */
public final class ImageProcessingUtils {

  private ImageProcessingUtils() {
  }

  /**
   * Determines if an object is null, and if so, throws an IllegalArgumentException.
   *
   * @param o   the object to be tested
   * @param msg the message to be included in the exception
   * @throws IllegalArgumentException if the object is null
   */
  public static void checkNotNull(Object o, String msg) throws IllegalArgumentException {
    if (o == null) {
      throw new IllegalArgumentException(msg);
    }
  }

}

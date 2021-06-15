import model.ImageProcessingUtils;
import org.junit.Test;

/**
 * Tests for the ImageProcessingUtils class.
 */
public class ImageProcessingUtilsTest {


  @Test(expected = IllegalArgumentException.class)
  public void testChecNotkNull() {
    ImageProcessingUtils.checkNotNull(null, "abc");
  }


}

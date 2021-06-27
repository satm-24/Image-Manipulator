import static org.junit.Assert.assertEquals;

import model.Color;
import model.ImageGrid;
import model.ImageUtil;
import model.Pixel;
import org.junit.Test;

/**
 * Tests for image utils.
 */
public class ImageUtilTest {

  @Test
  public void testReadToPPM() {

    String file = "main/cs3500/src/testImages/koalaTest.ppm";

    ImageGrid g = ImageUtil.readPPM(file);

    assertEquals(768, g.getPixels().length);

    assertEquals(1024, g.getPixels()[0].length);

    assertEquals(new Pixel(new Color(101, 90, 58)), g.getPixels()[0][0]);

  }

  @Test
  public void testWriteToPPM() {

    Pixel[][] p = new Pixel[2][2];

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        p[i][j] = new Pixel();
      }
    }

    ImageGrid g = new ImageGrid(p, 2, 2);

    ImageUtil.writeToPPM(g, "testPPM1.ppm");

    String file = new String("testPPM1.ppm");

    ImageGrid g2 = ImageUtil.readPPM(file);

    assertEquals(2, g2.getPixels().length);

    assertEquals(2, g2.getPixels()[0].length);

    assertEquals(new Pixel(new Color(0, 0, 0)), g.getPixels()[0][0]);

  }


}

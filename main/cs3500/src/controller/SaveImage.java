package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import model.IGrid;
import model.IProcessingImageModel;
import model.ImageGrid;
import model.ImageProcessingUtils;
import model.ImageUtil;
import model.Pixel;
import view.ILayer;

/**
 * Represents a command to save an image.
 */
public class SaveImage implements ImageProcessingCommand {

  private final String saveLocation;
  private final Enum<FileType> fileType;
  private final List<ILayer> layers;

  /**
   * Constructs an object of the save image command, with a location the file is saved to.
   *
   * @param saveLocation the path of the file location to be saved to
   * @param fileType     the file type that the user has specified
   * @param layers       represents this program's list of layers
   */
  public SaveImage(String saveLocation, Enum<FileType> fileType, List<ILayer> layers) {

    ImageProcessingUtils.checkNotNull(saveLocation, "File loc cannot be null.");
    ImageProcessingUtils.checkNotNull(layers, "list of layers cannot be null.");
    ImageProcessingUtils.checkNotNull(fileType, "filetype cannot be null.");

    this.saveLocation = saveLocation;
    this.fileType = fileType;
    this.layers = layers;
  }

  @Override
  public void execute(IProcessingImageModel m, ILayer current) {

    Pixel[][] pixels;

    IGrid gridToSave = new ImageGrid(new Pixel[100][100], 0, 0);

    for (int i = this.layers.size() - 1; i >= 0; i--) {

      if (this.layers.get(i).getVisibility()) {
        gridToSave = this.layers.get(i).getImage();
        break;
      }
    }

    int height = gridToSave.getPixels().length;
    int width = gridToSave.getPixels()[0].length;

    pixels = new Pixel[height][width];

    if (fileType != FileType.PPM) {

      if (fileType == FileType.JPEG) {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        setPixelsInBufferedImage(pixels, height, width, bufferedImage);

        try {
          ImageIO.write(bufferedImage, "JPEG", new File(saveLocation));
        } catch (IOException e) {
          System.out.println("Image could not be saved!");
        }



      } else if (this.fileType == FileType.PNG) {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        setPixelsInBufferedImage(pixels, height, width, bufferedImage);

        try {
          ImageIO.write(bufferedImage, "PNG", new File(saveLocation));
        } catch (IOException e) {
          System.out.println("Image could not be saved!");
        }

      }
    } else {
      ImageUtil.writeToPPM(new ImageGrid(pixels, width, height), saveLocation);
    }
  }

  /**
   * Sets the given buffered image to have the rgb values of the given pixel array.
   *
   * @param pixels        the pixel array we are getting rgb values from
   * @param height        the height of the image
   * @param width         the width of the image
   * @param bufferedImage the image we are setting the pixels of
   */
  private void setPixelsInBufferedImage(Pixel[][] pixels, int height, int width,
      BufferedImage bufferedImage) {
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        bufferedImage.setRGB(x, y, pixels[0][0].getClr().getRGB());

      }
    }

  }

}
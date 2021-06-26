package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.IGrid;
import model.IProcessingImageModel;
import model.ImageGrid;
import model.ImageProcessingUtils;
import model.ImageUtil;
import model.Pixel;

/**
 * Represents a command to save an image.
 */
public class SaveImage implements ImageProcessingCommand {

  private final String saveLocation;
  private final Enum<FileType> fileType;

  /**
   * Constructs an object of the save image command, with a location the file is saved to.
   *
   * @param saveLocation the path of the file location to be saved to
   * @param fileType     the file type that the user has specified
   */
  public SaveImage(String saveLocation, Enum<FileType> fileType) {

    ImageProcessingUtils.checkNotNull(saveLocation, "File loc cannot be null.");
    ImageProcessingUtils.checkNotNull(fileType, "filetype cannot be null.");

    this.saveLocation = saveLocation;

    this.fileType = fileType;
  }

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    if (!new File(saveLocation).exists()) {
      controller.renderMessageToView("Invalid save file location!");
      return;
    }

    if (controller.getLayers().size() < 1) {
      controller.renderMessageToView("There are no layers to save!");
      return;
    }

    Pixel[][] pixels = new Pixel[controller.getHeight()][controller.getWidth()];

    IGrid gridToSave = new ImageGrid(pixels, pixels[0].length, pixels.length);

    for (int i = controller.getLayers().size() - 1; i >= 0; i--) {

      if (controller.getLayers().get(i).getVisibility()) {
        gridToSave = controller.getLayers().get(i).getImage();
        break;
      }
    }

    int height = gridToSave.getPixels().length;
    int width = gridToSave.getPixels()[0].length;

    pixels = gridToSave.getPixels();

    if (fileType == FileType.JPEG) {

      createAndWriteBI(pixels, height, width, BufferedImage.TYPE_INT_RGB, "jpg");

    } else if (this.fileType == FileType.PNG) {

      createAndWriteBI(pixels, height, width, BufferedImage.TYPE_INT_ARGB, "png");

    } else if (this.fileType == FileType.PPM) {
      ImageUtil.writeToPPM(new ImageGrid(pixels, width, height), saveLocation);
    }
  }

  /**
   * Creates a bufferedImage with the given dimensions and writes it to a file.
   *
   * @param pixels   the pixels we want the BI ot have
   * @param height   image height
   * @param width    image width
   * @param typeInt  typeInt of bufferedImage
   * @param fileType type of file we are writing to
   */
  private void createAndWriteBI(Pixel[][] pixels, int height, int width, int typeInt,
      String fileType) {

    BufferedImage bufferedImage = setPixelsInBufferedImage(pixels, height, width, typeInt);

    writeBIToFile(bufferedImage, fileType);
  }

  /**
   * Writes a bufferedimage to a file at the given loc.
   *
   * @param bufferedImage BI to write
   * @param fileType      type of file we are writing
   */
  private void writeBIToFile(BufferedImage bufferedImage, String fileType) {
    try {

      ImageIO.write(bufferedImage, fileType, new File(saveLocation));
      System.out.println("file saved");
      System.out.println("width: " + bufferedImage.getWidth());
      System.out.println("height: " + bufferedImage.getHeight());


    } catch (IOException e) {
      System.out.println("Image could not be saved!");
    }
  }

  /**
   * @param pixels
   * @param height
   * @param width
   * @param typeInt
   * @return
   */
  private BufferedImage setPixelsInBufferedImage(Pixel[][] pixels, int height, int width,
      int typeInt) {

    BufferedImage bufferedImage = new BufferedImage(width, height, typeInt);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        bufferedImage.setRGB(x, y, pixels[y][x].getClr().getRGB());
      }
    }
    return bufferedImage;
  }

}
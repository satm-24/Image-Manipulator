package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import model.IGrid;
import model.IProcessingImageModel;
import model.ImageGrid;
import model.ImageProcessingUtils;
import model.Pixel;
import view.ILayer;

/**
 * Represents a command to save an image.
 */
public class SaveImage implements ImageProcessingCommand {

  private String saveLocation;
  private Enum<FileType> fileType;
  private List<ILayer> layers;

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

    this.saveLocation = saveLocation;
    this.fileType = fileType;
    this.layers = layers;
  }

  @Override
  public void execute(IProcessingImageModel m) {

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

    if (fileType != FileType.PPM) {

      if (fileType == FileType.JPEG) {

        BufferedImage bufferedImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
          for (int y = 0; y < height; y++) {
            bufferedImage.setRGB( x, y , pixels[0][0].getClr().getRGB());

//        ImageIO.write(new BufferedImage(50, 50, ),
//            "JPEG",
//            new File("/Users/satwikmisra/Desktop"
//                + "/smallCat "
//                + "copy.jpeg"));
      }


    }


    if (this.fileType == FileType.JPEG) {

    } else if (this.fileType == FileType.PNG) {
//      ImageIO.write(new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB),
//          "PNG",
//          new File("/Users"
//          + "/satwikmisra/Desktop"
//          + "/smallCat "
//          + "copy.jpeg"));
    } else if (this.fileType == FileType.PPM) {

    }


  }
}

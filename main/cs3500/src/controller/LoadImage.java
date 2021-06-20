package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import model.IProcessingImageModel;
import model.ImageGrid;
import model.ImageProcessingUtils;
import model.ImageUtil;
import view.ILayer;
import view.Layer;

/**
 * Represents a command for loading an image from a particular file location.
 */
public class LoadImage implements ImageProcessingCommand {

  private String location;
  private List<ILayer> layers;

  /**
   * Constructs an object of LoadImage.
   *
   * @param location the file location we are loading an image from.
   */
  public LoadImage(String location) {

    ImageProcessingUtils.checkNotNull(location, "File loc cannot be null.");

    if (new File(location).exists()) {
      this.location = location;
    } else {
      throw new IllegalArgumentException("Invalid load file location!");
    }

  }

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    try {

      BufferedImage img = ImageIO.read(new File(location));

      ImageGrid grid = ImageUtil.convertToGrid(img, img.getWidth(), img.getHeight());

      ILayer current = controller.getCurrent();

      current = new Layer(true, grid, controller.getCurrent().getName());

      current.setFileLocation(location);


    } catch (IOException e) {
      System.out.println("image could not be loaded");
    }


  }
}

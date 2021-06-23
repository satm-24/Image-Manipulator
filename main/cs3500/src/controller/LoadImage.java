package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.IProcessingImageModel;
import model.ImageGrid;
import model.ImageProcessingUtils;
import model.ImageUtil;
import view.Layer;

/**
 * Represents a command for loading an image from a particular file location.
 */
public class LoadImage implements ImageProcessingCommand {

  private final String location;

  /**
   * Constructs an object of LoadImage.
   *
   * @param location the file location we are loading an image from.
   */
  public LoadImage(String location) {

    ImageProcessingUtils.checkNotNull(location, "File loc cannot be null.");
    this.location = location;


  }

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    if (controller.getCurrent() == null) {
      controller.renderMessageToView("Current ");
    }

    if (new File(location).exists()) {

      try {

        BufferedImage img = ImageIO.read(new File(location));

        ImageGrid grid = ImageUtil.convertToGrid(img, img.getWidth(), img.getHeight());



        controller.setCurrent(new Layer(true, grid, controller.getCurrent().getName()));

        controller.getCurrent().setFileLocation(location);

        controller.renderMessageToView(
            "Loaded image at: " + location + " to current layer " +
                controller.getCurrent().getName() + "\n");


      } catch (IOException e) {
        System.out.println("image could not be loaded \n");
      }
    } else {
      controller.renderMessageToView("Invalid load file location! \n");
    }

  }
}

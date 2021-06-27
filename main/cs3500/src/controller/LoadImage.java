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
      controller.renderMessageToView("Current layer is null. Please create a layer, set it to "
          + "current, and try again. \n");
      return;
    }

    if (new File(location).exists() && validFile(location)) {

      try {

        BufferedImage img = ImageIO.read(new File(location));

        ImageGrid grid = ImageUtil.convertImgToGrid(img, img.getWidth(), img.getHeight());

        if (controller.getLayers().size() != 0 && !controller.getLayers().get(0).getFileLocation()
            .equals("")) {

          if (grid.getPixels().length != controller.getHeight()
              && grid.getPixels()[0].length != controller.getWidth()) {
            controller.renderMessageToView(
                "All images must be the same dimensions, with a height " + "of " + controller
                    .getHeight() + " and a width of " + controller.getWidth() + "\n");
            return;
          }

        }

        if (controller.getLayers().size() == 1) {
          controller.setWidth(grid.getPixels()[0].length);
          controller.setHeight(grid.getPixels().length);
        }

        controller.setCurrent(new Layer(true, grid, controller.getCurrent().getName()));

        controller.getCurrent().setFileLocation(location);

        controller.renderMessageToView(
            "Loaded image at: " + location + " to current layer " + controller.getCurrent()
                .getName() + "\n");


      } catch (IOException e) {
        controller.renderMessageToView("Image could not be loaded \n");
      }
    } else {
      controller.renderMessageToView("Invalid load file location! \n");
    }

  }

  /**
   * Checks if the given file location has a valid ending.
   *
   * @param location given file location
   * @return whether it is valid or not
   */
  private boolean validFile(String location) {

    if (location.endsWith("jpeg") || location.endsWith("jpg") || location.endsWith("png")
        || location.endsWith("ppm")) {
      return true;
    }
    return false;
  }
}

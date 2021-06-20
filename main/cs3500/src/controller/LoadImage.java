package controller;

import java.awt.Image;
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
   * @param layers   represents this program's list of layers
   */
  public LoadImage(String location, List<ILayer> layers) {

    ImageProcessingUtils.checkNotNull(layers, "List of layers cannot be null.");
    ImageProcessingUtils.checkNotNull(location, "File loc cannot be null.");

    this.location = location;
    this.layers = layers;
  }

  @Override
  public void execute(IProcessingImageModel m, ILayer current) {

    try {
     BufferedImage img = ImageIO.read(new File(location));

     ImageGrid grid = ImageUtil.convertToGrid(img, img.getWidth(), img.getHeight());

      current = new Layer(true, grid, current.getName());

    } catch (IOException e) {
      System.out.println("image could not be loaded");
    }



  }
}

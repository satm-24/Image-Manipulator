package controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.IProcessingImageModel;
import model.ImageGrid;
import model.ImageUtil;
import view.Layer;

/**
 * Loads many files at once.
 */
public class LoadMany implements ImageProcessingCommand {

  private final String fileLocation;

  /**
   * Constructs a load many command at a certain file location.
   *
   * @param fileLocation file location
   */
  public LoadMany(String fileLocation) {
    this.fileLocation = fileLocation;
  }

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    if (controller.getCurrent() == null) {
      controller.renderMessageToView("Current layer is null. Please set a layer to "
          + "current and try again. \n");
      return;
    }

    loadImagesToLayers(controller);

  }

  /**
   * @param controller
   */
  private void loadImagesToLayers(IProcessingController controller) {

    if (new File(fileLocation).exists()) {

      int numLines = countNumLinesInFile(new File(fileLocation), controller);

      if (numLines != controller.getLayers().size()) {
        controller.renderMessageToView(
            "The number of layers " + "(" + controller.getLayers().size() + ")"
                + " adoes not match the number of files you wish to load " + "(" + numLines + "). "
                + "Please adjust the number of "
                + "layers and try again. \n");
      } else {
        try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
          String line;

          int layerIdx = 0;

          while ((line = br.readLine()) != null && layerIdx < controller.getLayers().size()) {

            if (new File(line).exists()) {

              try {

                BufferedImage img = ImageIO.read(new File(line));

                ImageGrid grid = ImageUtil.convertImgToGrid(img, img.getWidth(), img.getHeight());

                controller.setLayer(
                    new Layer(true, grid, controller.getLayers().get(layerIdx).getName()));

                controller.getLayers().get(layerIdx).setFileLocation(line);

                controller.renderMessageToView(
                    "Loaded image at: " + line + " to layer \"" +
                        controller.getLayers().get(layerIdx).getName() + "\" \n");

                layerIdx++;


              } catch (IOException e) {
                controller.renderMessageToView("image could not be loaded \n");
              }
            } else {
              controller.renderMessageToView("File " + line + " does not exist! \n");
            }
          }

        } catch (FileNotFoundException e) {
          controller.renderMessageToView("File was not found. \n");
        } catch (IOException e) {
          controller.renderMessageToView("File could not be read. \n");
        }
      }


    } else {
      controller.renderMessageToView("Invalid load file location! \n");
    }


  }

  private int countNumLinesInFile(File file, IProcessingController controller) {

    int numLines = 0;

    System.out.println(file.getAbsolutePath());

    try (BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
      String line;

      while ((line = br.readLine()) != null) {
        numLines++;
      }

    } catch (FileNotFoundException e) {
      controller.renderMessageToView("File not found. \n");
    } catch (IOException e) {
      controller.renderMessageToView("Could not read line. \n");
    }

    return numLines;
  }

}

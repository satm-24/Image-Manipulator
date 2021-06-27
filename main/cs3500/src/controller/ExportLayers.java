package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.IProcessingImageModel;
import view.ILayer;

/**
 * Represents a command to export multiple layers of an image.
 */
public class ExportLayers implements ImageProcessingCommand {

  private final String exportLocation;

  /**
   * Constructs a command of export layers, given the destination location.
   *
   * @param exportLocation the destination to be exported to
   */
  public ExportLayers(String exportLocation) {
    this.exportLocation = exportLocation;
  }

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    if (controller.getCurrent() == null) {
      controller.renderMessageToView(
          "Current layer is null. Please set a layer to " + "current and try again. \n");
      return;
    }

    if (new File(exportLocation).exists()) {

      try {

        FileWriter writeIndividualFiles = new FileWriter(exportLocation);

        for (ILayer layer : controller.getLayers()) {

          if (layer.getFileLocation().equals("")) {
            controller.renderMessageToView(
                "There is no image file at layer" + " \"" + layer.getName() + "\".");
          } else {
            writeIndividualFiles.write(layer.getFileLocation() + "\n");
          }
        }

        writeIndividualFiles.close();

        controller.renderMessageToView("File exported: " + exportLocation + "\n");

      } catch (IOException e) {
        controller.renderMessageToView("images could not be exported \n");
      }

    } else {
      controller.renderMessageToView("Invalid export file location! \n");
    }


  }
}

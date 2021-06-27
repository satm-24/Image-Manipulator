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

    try {

      File exportedLayers = new File(exportLocation);

      if (exportedLayers.createNewFile()) {
        FileWriter writeIndividualFiles = new FileWriter(exportLocation);

        for (ILayer layer : controller.getLayers()) {
          writeIndividualFiles.write(layer.getFileLocation() + "\n");
        }

        controller.renderMessageToView("File created: " + exportedLayers.getName() + "\n");
        writeIndividualFiles.close();

      } else {
        controller.renderMessageToView("File already exists\n");

      }
    } catch (IOException e) {
      controller.renderMessageToView("Could not create file.\n")
    }


  }
}

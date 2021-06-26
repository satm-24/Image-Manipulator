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

        writeIndividualFiles.close();

        System.out.println("File created: " + exportedLayers.getName());
      } else {
        System.out.println("File already exists.");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }


  }
}

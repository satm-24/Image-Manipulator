package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import model.IProcessingImageModel;
import view.ILayer;

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
    if (new File(fileLocation).exists()) {
      this.fileLocation = fileLocation;
    } else {
      throw new IllegalArgumentException("Invalid load file location!");
    }
  }

  @Override
  public void execute(IProcessingImageModel m, IProcessingController controller) {

    File output = new File(fileLocation);
    FileWriter writer;

    try {
      writer = new FileWriter(fileLocation);
    } catch (IOException e) {
      controller.tryToRenderError("Could not write file");
      throw new IllegalArgumentException("Could not write to file");
    }

    for (ILayer layer : controller.getLayers()) {
      try {
        writer.write(layer.getFileLocation() + "\n");
      } catch (IOException e) {
        controller.tryToRenderError("Could not write file");
        throw new IllegalArgumentException("Could not write to file");
      }
    }

    try {
      writer.close();
    } catch (IOException e) {
      controller.tryToRenderError("Could not close");
      throw new IllegalArgumentException("Could not close writer");
    }

  }
}

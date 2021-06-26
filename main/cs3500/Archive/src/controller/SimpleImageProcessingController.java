package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import model.SimpleImageModel;
import view.ILayer;
import view.IProcessingImageView;
import view.SimpleProcessingImageView;

/**
 * Represents a controller that takes in commands for our image processing software.
 */
public class SimpleImageProcessingController implements IProcessingController {

  /**
   * Runs the main method for the image processing program.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {

    SimpleImageProcessingController controller =
        new SimpleImageProcessingController(new SimpleImageModel(), new ArrayList<>(),
            new InputStreamReader(System.in), System.out);

    controller.parseInput();

  }

  private final Map<String, Function<Scanner, ImageProcessingCommand>> knownCommands;
  private final IProcessingImageModel model;
  private final List<ILayer> layers;
  private ILayer current;
  private final Readable rd;
  private final IProcessingImageView view;


  /**
   * Constructs an object of our controller with the list of layers.
   *
   * @param model  represents the model the controller acts on
   * @param layers the list of layers
   * @param rd     represents the Readable object used to take user input
   * @param app    the appendable
   * @throws IllegalArgumentException if either argument is null
   */
  public SimpleImageProcessingController(IProcessingImageModel model, List<ILayer> layers,
      Readable rd, Appendable app) {

    ImageProcessingUtils.checkNotNull(layers, "The list of layers cannot be null.");
    ImageProcessingUtils.checkNotNull(model, "The model cannot be null.");
    ImageProcessingUtils.checkNotNull(rd, "The readable cannot be null.");
    ImageProcessingUtils.checkNotNull(app, "The view cannot be null.");

    this.model = model;
    this.rd = rd;
    this.view = new SimpleProcessingImageView(app);

    ImageProcessingUtils.checkNotNull(layers, "The list of layers cannot be null.");

    this.knownCommands = new HashMap<>();

    this.layers = layers;

    knownCommands.put("save", s -> new SaveImage(s.next(), convertToFileType(s.next())));
    knownCommands.put("load", s -> new LoadImage(s.next()));
    knownCommands.put("blur", s -> new BlurImage());
    knownCommands.put("sharpen", s -> new SharpenImage());
    knownCommands.put("sepia", s -> new TransformSepia());
    knownCommands.put("greyscale", s -> new TransformGreyscale());
    knownCommands.put("add", s -> new AddLayer(s.next()));
    knownCommands.put("export", s -> new ExportLayers(s.next()));
    knownCommands.put("loadmany", s -> new LoadMany(s.next()));

    if (this.layers.size() >= 1) {
      this.current = this.layers.get(this.layers.size() - 1);
    } else {
      this.current = null;
    }
  }

  /**
   * Converts a string to a file type enum.
   *
   * @param next the String we are converting
   * @return the FileType representing what file type the string is.
   * @throw IllegalArgumentException if the string is not a valid file type
   */
  private Enum<FileType> convertToFileType(String next) {

    while (true) {

      switch (next.toUpperCase()) {
        case "JPEG":
          return FileType.JPEG;
        case "PNG":
          return FileType.PNG;
        case "PPM":
          return FileType.PPM;

        default:
          tryToRenderError("String is not a valid file type. Try again");
      }

    }

  }


  @Override
  public void parseInput() {
    System.out.println("Welcome to Simp, an free Photoshop alternative. Create a layer to begin. "
        + "\n");

    Scanner scan = new Scanner(this.rd);

    while (scan.hasNext()) {
      ImageProcessingCommand c;

      String in = scan.next();

      if (in.equals("invisible")) {
        determineAndSetVisibility(scan, in, "invisible", false);
        in = scan.next();
      }

      if (in.equals("visible")) {
        determineAndSetVisibility(scan, in, "visible", true);
        in = scan.next();
      }

      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        try {
          this.view.renderError("Program has quit.");
          return;
        } catch (IOException e) {
          throw new IllegalArgumentException("Message could not be appended.");
        }
      }

      if (in.equalsIgnoreCase("current")) {

        String layerName = scan.next();

        System.out.println(layerName);

        if (isValidLayerName(layerName)) {

          this.current = findLayer(layerName);

          in = scan.next();

          System.out.println(this.current.getName());


        } else {
          tryToRenderError("invalid input");
          in = scan.next();
        }

      }

      Function<Scanner, ImageProcessingCommand> cmd =
          knownCommands.getOrDefault(in, null);

      if (cmd == null) {
        tryToRenderError("Invalid command, or the command does not exist.");
      } else {
        c = cmd.apply(scan);

        c.execute(model, this);
      }

    }

    return;

  }

  /**
   * Sets the layer to the given visibility.
   *
   * @param scan      the scanner
   * @param in        the visibility type
   * @param invisible the visibility string
   * @param b         whether the visility type is true or false
   */
  private void determineAndSetVisibility(Scanner scan, String in, String invisible, boolean b) {
    if (in.equalsIgnoreCase(invisible)) {

      String layerName = scan.next();

      if (isValidLayerName(layerName)) {
        for (ILayer l : this.layers) {
          if (l.getName().equals(layerName)) {
            l.setVisibility(b);
          }
        }
      } else {
        tryToRenderError("Invalid layer name.");
      }
    }
  }

  @Override
  public void tryToRenderError(String msg) {
    try {
      this.view.renderError(msg);
    } catch (IOException e) {
      throw new IllegalArgumentException("Message could not be appended.");
    }
  }

  /**
   * Finds the layer with the given name.
   *
   * @param next the name
   * @return the layer with the name
   * @throws IllegalArgumentException if no such layer is found
   */
  private ILayer findLayer(String next) throws IllegalArgumentException {

    for (ILayer layer : this.layers) {
      if (layer.getName().equals(next)) {
        return layer;
      }
    }

    // we are throwing an error here but technically it should never reach this point because we
    // are already checking if the layer name is valid, this is just a double check
    throw new IllegalArgumentException("No layer with the given name was found.");

  }

  /**
   * Determines if a layer with the given name exists within the list of layers.
   *
   * @param next the name of the layer
   * @return whether the layer exists
   */
  private boolean isValidLayerName(String next) {

    for (ILayer layer : this.layers) {
      if (layer.getName().equals(next)) {
        return true;
      }
    }

    return false;

  }

  @Override
  public ILayer getCurrent() {
    return this.current;
  }

  @Override
  public List<ILayer> getLayers() {
    return this.layers;
  }

  @Override
  public void setCurrent(ILayer current) {
    if (this.current == null) {
      this.current = current;
    } else {
      this.layers.remove(current);
      this.current = current;
      this.layers.add(current);

    }

  }

  /**
   * Adds a command to our known commands.
   *
   * @param key  the name of the command
   * @param func a function that takes in input and applies a corresponding command.
   */
  protected void addToKnownCommands(String key, Function<Scanner, ImageProcessingCommand> func) {
    this.knownCommands.put(key, func);
  }

  @Override
  public void removeCurrent() {
    if (this.isValidLayerName(this.current.getName())) {
      for (ILayer layer : this.layers) {
        if (layer.getName().equals(current.getName())) {
          layers.remove(layer);
          break;
        }
      }
    }
  }

  @Override
  public void setCurrentInLayers(ILayer layer) {
    for (int i = 0; i < this.layers.size(); i++) {
      if (this.layers.get(i).getName().equals(this.current.getName())) {
        this.layers.set(i, layer);
        break;
      }
    }
  }
}

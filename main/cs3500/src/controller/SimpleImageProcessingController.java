package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import model.IProcessingImageModel;
import model.ImageProcessingUtils;
import view.ILayer;

/**
 * Represents a controller that takes in commands for our image processing software.
 */
public class SimpleImageProcessingController implements IProcessingController {

  private final Map<String, Function<Scanner, ImageProcessingCommand>> knownCommands;
  private final IProcessingImageModel model;
  private final List<ILayer> layers;
  private ILayer current;
  private final Readable rd;
  private final Appendable ap;


  /**
   * Constructs an object of our controller with the list of layers.
   *
   * @param model  represents the model the controller acts on
   * @param layers the list of layers
   * @param rd     represents the Readable object used to take user input
   * @param ap     the appendable object used to transmit the output, connected to view
   * @throws IllegalArgumentException if either argument is null
   */
  public SimpleImageProcessingController(IProcessingImageModel model, List<ILayer> layers,
      Readable rd, Appendable ap) {

    ImageProcessingUtils.checkNotNull(layers, "The list of layers cannot be null.");
    ImageProcessingUtils.checkNotNull(model, "The model cannot be null.");
    ImageProcessingUtils.checkNotNull(rd, "The readable cannot be null.");
    ImageProcessingUtils.checkNotNull(ap, "The appendable cannot be null.");

    this.model = model;
    this.rd = rd;
    this.ap = ap;

    ImageProcessingUtils.checkNotNull(layers, "The list of layers cannot be null.");

    this.knownCommands = new HashMap<>();

    this.layers = layers;

    knownCommands.put("save", s -> new SaveImage(s.next(), convertToFileType(s.next())));
    knownCommands.put("load", s -> new LoadImage(s.next(), this.layers));
    knownCommands.put("blur", s -> new BlurImage());
    knownCommands.put("sharpen", s -> new SharpenImage());
    knownCommands.put("sepia", s -> new TransformSepia());
    knownCommands.put("greyscale", s -> new TransformGreyscale());
    knownCommands.put("add", s -> new AddLayer(s.next(), this.layers));
    knownCommands.put("remove", s -> new RemoveLayer(this.layers));

    if (this.layers.size() >= 1) {
      this.current = this.layers.get(this.layers.size() - 1);
    } else {
      this.current = null;
    }
  }

  private ILayer getCurrentIfPresent() {
    if (this.current == null) {
      throw new IllegalArgumentException("Current layer is not present.");
    }
    return this.current;
  }

  /**
   * Converts a string to a file type enum.
   *
   * @param next the String we are converting
   * @return the Enum<FileType> representing what file type the string is.
   * @throw IllegalArgumentException if the string is not a valid file type
   */
  private Enum<FileType> convertToFileType(String next) {

    switch (next.toUpperCase()) {
      case "JPEG":
        return FileType.JPEG;
      case "PNG":
        return FileType.PNG;
      case "PPM":
        return FileType.PPM;

      default:
        throw new IllegalArgumentException("String is not a valid file type");
    }

  }

  /**
   * Checks if the given string is a valid name of a layer in our list of layers, and sets the
   * current layer to that layer if it is.
   *
   * @param next the name of the layer that we want to set current to
   * @return an optional object containing the desired layer
   * @throws IllegalArgumentException if the String is not a valid layer
   */
  private Optional<ILayer> checkAndSetCurrent(String next) {
    return Optional.empty();
  }


  /**
   * Takes in command inputs for the user and executes them.
   */
  @Override
  public void parseInput() {
    Scanner scan = new Scanner(this.rd);

    while (scan.hasNext()) {
      ImageProcessingCommand c;

      String in = scan.next();

      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        return;
      }

      // add invisible/visible command

      if (in.equalsIgnoreCase("current")) {

        if (isValidLayerName(scan.next())) {
          this.current = findLayer(scan.next());
        }
        else {
          throw new IllegalArgumentException("invalid input");
        }

      }

      Function<Scanner, ImageProcessingCommand> cmd =
          knownCommands.getOrDefault(in, null);

      if (cmd == null) {
        throw new IllegalArgumentException("Invalid command, or the command does not exist.");
      } else {
        c = cmd.apply(scan);

        if (this.current != null) {
          c.execute(model, this);
        } else {
          throw new IllegalStateException("Current is null!");
        }
      }

    }

  }

  /**
   *
   * @param next
   * @return
   * @throws IllegalArgumentException
   */
  private ILayer findLayer(String next) throws IllegalArgumentException {

    for (ILayer layer : this.layers) {
      if (layer.getName().equals(next)) {
        return layer;
      }
    }

    throw new IllegalArgumentException("No layer with the given name was found");

  }

  /**
   *
   * @param next
   * @return
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
    this.current = current;
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

}

package controller;

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

    SimpleImageProcessingController controller = new SimpleImageProcessingController(
        new SimpleImageModel(), new ArrayList<>(), new InputStreamReader(System.in), System.out);

    controller.parseInput();

  }

  private final Map<String, Function<Scanner, ImageProcessingCommand>> knownCommands;
  private final IProcessingImageModel model;
  private final IProcessingImageView view;
  private final List<ILayer> layers;
  private ILayer current;
  private final Readable rd;
  private int width;
  private int height;


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

    // default height and width when the program starts, changed when we add an image to the
    // first layer
    this.width = 250;
    this.height = 250;

    knownCommands.put("save", s -> new SaveImage(s.next(), convertToFileType(s.next(), s)));
    knownCommands.put("load", s -> new LoadImage(s.next()));
    knownCommands.put("blur", s -> new BlurImage());
    knownCommands.put("sharpen", s -> new SharpenImage());
    knownCommands.put("sepia", s -> new TransformSepia());
    knownCommands.put("greyscale", s -> new TransformGreyscale());
    knownCommands.put("add", s -> new AddLayer(s.next()));
    knownCommands.put("remove", s -> new RemoveLayer());
    knownCommands.put("export", s -> new ExportLayers(s.next()));
    knownCommands.put("loadmany", s -> new LoadMany(s.next()));
    // added these two commands
    knownCommands.put("downsize", s -> new Downsize(s.next(), s.next()));
    knownCommands.put("mosaic", s -> new Mosaic(covertToValidNumSeeds(s.next(), s)));

    if (this.layers.size() >= 1) {
      this.current = this.layers.get(this.layers.size() - 1);
    } else {
      this.current = null;
    }
  }

  /**
   * Parses a string input into a valid seed number for the mosiac command.
   *
   * @param str string to parse to integer
   * @param s   scanner to get input from
   * @return number of seeds
   */
  private int covertToValidNumSeeds(String str, Scanner s) {

    while (true) {
      try {

        int res = Integer.parseInt(str);

        if (res < 0) {
          this.renderMessageToView("Invalid number of seeds! \n");
        } else {
          return res;
        }
      } catch (NumberFormatException e) {
        this.renderMessageToView("Invalid number of seeds! \n");
      }

      str = s.next();
    }


  }

  /**
   * Converts string inputs to a file type enum.
   *
   * @param next the String we are converting
   * @param s    the scanner we're taking input from
   * @return the FileType representing what file type the string is.
   */
  private Enum<FileType> convertToFileType(String next, Scanner s) {

    while (true) {
      switch (next.toUpperCase()) {
        case "JPEG":
        case "JPG":
          return FileType.JPEG;
        case "PNG":
          return FileType.PNG;
        case "PPM":
          return FileType.PPM;

        default:
          break;
      }

      renderMessageToView("Save was not given a valid file type. Try again \n");
      next = s.next();
    }


  }


  @Override
  public void parseInput() {
    this.renderMessageToView(
        "Welcome to Simp, an free Photoshop alternative. Create a layer to begin. " + "\n");

    Scanner scan = new Scanner(this.rd);

    while (scan.hasNext()) {
      ImageProcessingCommand c;

      String in = scan.next();

      processVisibilityInput(scan, in);


      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        this.view.renderMessage("Program has quit.");
        return;
      }

      if(processCurrentLayerInput(scan, in)) in = scan.next();




      Function<Scanner, ImageProcessingCommand> cmd = knownCommands.getOrDefault(in, null);

      if (cmd == null) {
        renderMessageToView("Invalid command, or the command does not exist.\n");
      } else {
        c = cmd.apply(scan);

        c.execute(model, this);
      }

      if (this.layers.size() == 1) {
        this.width = this.layers.get(0).getImage().getPixels()[0].length;
        this.height = this.layers.get(0).getImage().getPixels().length;
      }

    }

  }

  @Override
  public boolean checkNullCurrent() {
    if (this.current == null) {
      renderMessageToView(
          "The current layer is null. Please set a layer as " + "\"current\" and try again. \n");

      return true;
    }

    return false;
  }

  /**
   * @param scan
   * @param in
   */
  private boolean processCurrentLayerInput(Scanner scan, String in) {
    while (in.equalsIgnoreCase("current")) {

      String layerName = scan.next();

      if (isValidLayerName(layerName)) {

        this.current = findLayer(layerName);

        this.view.renderMessage("Current layer is now: \"" + this.current.getName() + "\" \n");
//        in = scan.next();
        return true;

      } else {
        renderMessageToView("Could not set current, invalid layer name. \n");
        in = scan.next();
      }
    }

    return false;

  }

  /**
   * @param scan
   * @param in
   */
  private void processVisibilityInput(Scanner scan, String in) {

    while (in.equalsIgnoreCase("invisible") || in.equalsIgnoreCase("visible")) {

      if (in.equals("invisible")) {

        String layerName = scan.next();

        determineAndSetVisibility(layerName, false);
        this.view.renderMessage("Layer \"" + layerName + "\" was set to invisible.\n");

        in = scan.next();

      }

      if (in.equals("visible")) {

        String layerName = scan.next();

        determineAndSetVisibility(layerName, true);
        this.view.renderMessage("Layer \"" + layerName + "\" was set to visible.\n");

        in = scan.next();


      }
    }
  }


  /**
   * Sets the layer to the given visibility.
   *
   * @param layerName name of the layer we are searching for
   * @param b         whether the visility type is true or false
   */
  private void determineAndSetVisibility(String layerName, boolean b) {

    if (isValidLayerName(layerName)) {
      for (ILayer l : this.layers) {
        if (l.getName().equals(layerName)) {
          l.setVisibility(b);
        }
      }
    } else {
      renderMessageToView("Invalid layer name. + \n");
    }
  }

  @Override
  public void renderMessageToView(String msg) {
    this.view.renderMessage(msg);
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
  public int getHeight() {
    return this.height;
  }

  @Override
  public int getWidth() {
    return this.width;
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
      this.layers.remove(this.current);
      this.current = current;
      this.layers.add(current);

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

  @Override
  public void setLayer(ILayer newLayer) {

    for (int i = 0; i < this.layers.size(); i++) {
      if (this.layers.get(i).getName().equals(newLayer.getName())) {
        this.layers.set(i, newLayer);
        break;
      }

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


}

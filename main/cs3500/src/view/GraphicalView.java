package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Represents a graphical view for the image processing program, with the image displayed in the
 * middle, load/save buttons above and editing buttons below.
 */
public class GraphicalView extends JFrame implements IProcessingImageView, ActionListener,
    ListSelectionListener {

  private final JPanel north;
  private final JPanel center;
  private final JLabel centerImage;
  private final JPanel south;

  private final JPanel importExportPanel;
  private final JPanel layerPanel;

  private final JPanel southButtons;
  private final JPanel extraButtons;
  private final JLabel textMessage;

  private final JScrollPane imageScroll;

  private final JButton loadButton;
  private final JButton saveButton;

  private final JButton addLayerButton;
  private final JButton removeLayerButton;

  private final JLabel layersLabel;

  private final JList<String> listOfLayers;
  DefaultListModel<String> dataForListOfStrings;

  private final JButton downsizeButton;
  private final JButton mosaicButton;

  private final JButton sharpenButton;
  private final JButton blurButton;
  private final JButton sepiaButton;
  private final JButton greyscaleButton;

  private final List<IViewListener> listeners;

  public GraphicalView() {
    super();
    setSize(new Dimension(600, 600));
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // initialize panels
    north = new JPanel();
    north.setLayout(new BoxLayout(north, BoxLayout.PAGE_AXIS));
    add(north, BorderLayout.NORTH);

    center = new JPanel();

    south = new JPanel();
    south.setLayout(new BoxLayout(south, BoxLayout.PAGE_AXIS));
    add(south, BorderLayout.SOUTH);

    // center image with scroll
    centerImage = new JLabel();
    imageScroll = new JScrollPane(centerImage);
    center.add(imageScroll);
    add(imageScroll);
    add(centerImage, BorderLayout.CENTER);

    // load button
    importExportPanel = new JPanel();
    north.add(importExportPanel);

    loadButton = new JButton("Load");
    loadButton.setActionCommand("load");
    loadButton.addActionListener(this);
    importExportPanel.add(loadButton);

    // save button
    saveButton = new JButton("Save");
    saveButton.setActionCommand("save");
    saveButton.addActionListener(this);
    importExportPanel.add(saveButton);

    // add layer button
    layerPanel = new JPanel();
    north.add(layerPanel);

    addLayerButton = new JButton("Add Layer");
    addLayerButton.setActionCommand("add layer");
    addLayerButton.addActionListener(this);
    layerPanel.add(addLayerButton);

    // remove layer button
    removeLayerButton = new JButton("Remove Layer");
    removeLayerButton.setActionCommand("remove");
    removeLayerButton.addActionListener(this);
    layerPanel.add(removeLayerButton);

    // layer selection label
    layersLabel = new JLabel("Layers: select one to set as current");
    north.add(layersLabel);

    // layer selection panel
    dataForListOfStrings = new DefaultListModel<>();

    listOfLayers = new JList<>(dataForListOfStrings);
    listOfLayers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    listOfLayers.addListSelectionListener(this);
    north.add(listOfLayers);

    // sharpen button
    southButtons = new JPanel();
    south.add(southButtons);
    southButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

    sharpenButton = new JButton("Sharpen");
    sharpenButton.setActionCommand("sharpen");
    sharpenButton.addActionListener(this);
    southButtons.add(sharpenButton);

    // message
    textMessage = new JLabel("Welcome to SIMP (Simple Image Manipulation Program");
    south.add(textMessage);
    textMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

    // blur button
    blurButton = new JButton("Blur");
    blurButton.setActionCommand("blur");
    blurButton.addActionListener(this);
    southButtons.add(blurButton);

    // sepia button
    sepiaButton = new JButton("Sepia");
    sepiaButton.setActionCommand("sepia");
    sepiaButton.addActionListener(this);
    southButtons.add(sepiaButton);

    // greyscale button
    greyscaleButton = new JButton("Greyscale");
    greyscaleButton.setActionCommand("greyscale");
    greyscaleButton.addActionListener(this);
    southButtons.add(greyscaleButton);

    // downsize button
    extraButtons = new JPanel();
    south.add(extraButtons);
    south.setAlignmentX(Component.CENTER_ALIGNMENT);

    downsizeButton = new JButton("Downsize");
    downsizeButton.setActionCommand("downsize");
    downsizeButton.addActionListener(this);
    extraButtons.add(downsizeButton);

    // mosaic button
    mosaicButton = new JButton("Mosaic");
    mosaicButton.setActionCommand("mosaic");
    mosaicButton.addActionListener(this);
    extraButtons.add(mosaicButton);

    // listeners
    listeners = new ArrayList<>();

    this.setFocusable(true);
    this.requestFocus();

    this.setVisible(true);
  }

  @Override
  public void renderMessage(String message) {
    textMessage.setText(message);
  }

  @Override
  public void setImage(ImageIcon image) {
    centerImage.setIcon(image);
  }

  @Override
  public void requestViewFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void addViewEventListener(IViewListener listener) {
    this.listeners.add(listener);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    switch (actionCommand) {
      case "load":
        emitLoadEvent();
        break;
      case "save":
        emitSaveEvent();
        break;
      case "sharpen":
        emitSharpenEvent();
        break;
      case "blur":
        emitBlurEvent();
        break;
      case "sepia":
        emitSepiaEvent();
        break;
      case "greyscale":
        emitGreyscaleEvent();
        break;
      case "add layer":
        emitAddEvent();
        break;
      case "remove":
        emitRemoveEvent();
//      case "mosaic":
//        emitMosaicEvent();
//      case "downsize":
//        emitDownSizeEvent();
      default:
        renderMessage("unknown action command");
    }
    requestViewFocus();
  }

  /**
   * Handles all load events.
   */
  protected void emitLoadEvent() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JPG & GIF Images", "jpg", "gif");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      for (IViewListener listener: listeners) {
        listener.handleLoad(f);
      }
    }
  }

  /**
   * Handles all save events.
   */
  protected void emitSaveEvent() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      for (IViewListener listener: listeners) {
        listener.handleSave(f);
      }
    }
  }

  /**
   * Handles all blur events.
   */
  protected void emitBlurEvent() {
    for (IViewListener listener: listeners) {
      listener.handleBlur();
    }
  }

  /**
   * Handles all sharpen events.
   */
  protected void emitSharpenEvent() {
    for (IViewListener listener: listeners) {
      listener.handleSharpen();
    }
  }

  /**
   * Handles all sepia events.
   */
  protected void emitSepiaEvent() {
    for (IViewListener listener: listeners) {
      listener.handleSepia();
    }
  }

  /**
   * Handles all greyscale events.
   */
  protected void emitGreyscaleEvent() {
    for (IViewListener listener: listeners) {
      listener.handleGreyscale();
    }
  }

  /**
   * Handles all add layers events.
   */
  protected void emitAddEvent() {
    String s = JOptionPane.showInputDialog("Please enter the name of the new layer");
    for (IViewListener listener: listeners) {
      listener.handleAdd(s);
    }
    renderMessage("Added layer successfully");
  }

  /**
   * Handles all remove layers events.
   */
  protected void emitRemoveEvent() {
    for (IViewListener listener: listeners) {
      listener.handleRemove();
    }
    renderMessage("Removed layer successfully");
  }

  @Override
  public void valueChanged(ListSelectionEvent e) {
    for (IViewListener listener: listeners) {
      listener.handleSetCurrent(listOfLayers.getSelectedValue());
    }
  }

  @Override
  public void setUpLayerSelection(String name) {
    dataForListOfStrings.addElement(name);
  }

  @Override
  public void clearLayerSelection() {
    dataForListOfStrings.clear();
  }
}

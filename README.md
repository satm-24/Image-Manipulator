Java Image Processing Designed by: Satwik Misra and Steven Ngo

This project is an image processing program for different types of image formats. Currently, the
first version only supports PPM, PNG and JPG, with the only modification being filtering and color
transformation. This project also supports programmatically creating images, though the only
implementation thus far is to create an image in a checkerboard pattern. We utilized IntelliJ IDEA
to develop the code. In the future, we hope to implement support for other file types, as well as
other transformation types such as adjusting saturation and brightness. The current filter only
supports sharpening and blurring, and the color transformation supports sepia and greyscale, as well
as any custom matrix. The program takes in commands from a script, which can be further elaborated
on in the USEME.

Update v2.0: We've added a controller with all the relevant classes for supported commands
(AddLayer, BlurImage, SaveImage, etc) which are described below. The controller has a parseInput
method which takes care of user input and is used to run the program. We also have a simple view
that displays error messages and general information about command effects. (Ex:
image has been blurred). We also support exporting and loading multiple images.

#### Update v3.0: We've updated our list of commands to include Mosaics and Downsizing images. We've also created a new class, PixelWithCoords, which is an extension of the Pixel class but also has acoordinate location. We also now have a full-fledged GUI that can be used to operate our program,with specifics described in the USEME.
####


####Justifications for changes (v3.0):

- setters were needed because we had to do a lot of pixel and layer changing
  - we added a setHeight() and setWidth() because in the past we didnt enforce
  dimensions correctly
- aside from adding some various helper methods and the addition of our GUI, our design 
did not change. We were able to have a text-based and interactive GUI while keeping the same
  controller, which was nice.

#### What changed to implement Mosaics and Downsizing? ####

Downsizing:

- we added a new class Downsize using the command pattern which took in a width and a height, and 
  used its execute()
method to actually mutate the given controller and carry out the command. We also had
  to add downsizing to our list of commands (hashmap). We did have to introduce a setter,
  setImage(), in order to change a layer's image to be the downsized image. Aside from that,
  because we used the command patter not much changed. However, we did have to add a button to 
  the GUI as well.
  
Mosaics:

- we added a new class, Mosaic, which took in an int number of seeds. We represented seeds
as random pixels in our grid. Implementing this was a bit more complicated, because we had to
  create a new class, PixelWithCoords, which was a pixel with x and y coordinates. Before, our 
  pixel class only had a color, and we didn't have a need for particular indices. Having pixels
  with coordinates made the whole mosaic process a lot easier. We also implemented a setColor() 
  method which changed the color of each pixel to the cluster average. Like Downsize, we had to add
  a button to our GUI but aside from adding a command to our list of commands this was pretty 
  straightforward and didn't change much.

Model Overview:

Interfaces:

- ICreator: encompasses different function objects for programmatically creating images;
  additionally, decouples the model from the need for modification when implementing new methods of
  creation
- IOperation: encompasses different function objects for editing images; additionally, decouples the
  model from the need for modification when implementing new editing functionality
- IColor: represents the different types of implementations of colors, such as in a base 10 rgb
  format, as well as functionality necessary to edit colors
- IGrid: represents the different types of image representations through a grid (2d array)
- IPixel: represents different types of pixels, which are single instances of color of an image and
  holds functionality for support in editing pixels
- IProcessingImageModel: represents models for the image processing program, dependent on which file
  type is to be supported
- ImageProcessingCommand: represents all different types of commands in the image processing program
- IProcessingController: represents types of controllers for the image processing program
- ILayer: represents types of layers of images
- IProcessingImageView: represents types of views for the image processing program
  #### New Editions in v3.0
- ILocation: represents various location types
- ICoordsPixel: represents various pixels with coordinates

Classes:

- CreateCheckerboard: represents a function object that programmatically creates a checkerboard
  pattern
- ColorTransformation: represents a function object that transforms an image based on a
  predetermined filter type
- Filter: represents a function object that filters an image using a predetermined kernel
- Color: a representation of color through decimal RGB values
- ImageProcessingUtils: holds functionality for methods that can be called globally; i.e. utility
  methods for images processing
- ImageUtil: handles the importing and exporting of images
- Pixel: a pixel representation with a color represented by rgb values
- PPMImageGrid: an image grid for PPM files; handles all operations on the pixels of an image during
  transformations
- PPMImageModel: a model for the image processing program, designed specifically for PPM file types:
  handles operations on the images held by the model and enforces constraints for the client Enums:
- FilterType: represents the different predefined filter types, such as sharpening and blurring;
  holds methods that returns the kernel for a given type
- TransformationType: represents the different predefined transformation types, such as sepia and
  greyscale, as well as any future ones to be added
- AddLayer: command for adding a layer
- BlurImage: command for blurring an image
- ExportImage: command for exporting an image
- LoadImage: command for loading an image
- RemoveLayer: command for removing a layer
- SaveImage: command for saving an image
- SharpenImage: command for sharpening an image
- SimpleImageProcessingController: represents a simple image processing controller that handles
  inputs and outputs to process an image
- TransformGreyscale: command for transforming an image to greyscale
- TransformSepia: command for transforming an image to sepia
- Layer: represents a single layer of an image
- SimpleProcessingImageView: represents a simple viewer for the image processing program that
  outputs errors
  #### New Editions in v3.0
- Location: represents a point with an x and y coord
- Mosaic: command for turning an image into a mosaic
- Downsize: command for downsizing images
- PixelWithCoords: extends pixel, represents a pixel with an x and y coordinate

Citations:
Both blackbuck.ascii.ppm and snail.ascii.ppm were PPM images found at
https://people.sc.fsu.edu/~jburkardt/data/ppma/ppma.html. Though no photographer could be named,
they both were displayed as sample images on a site by the Department of Scientific Computer at
Florida State University. Doing more research into the origin of these images, they were .png files
that were then converted to .ppm (https://people.sc.fsu.edu/~jburkardt/data/png/png.html).
Jellyfish, Koala, and Penguins were all given to us by the Assigment (NEU CCIS). 
The mario png was takes for free from pngimg.com (https://pngimg.com/image/30530).
and was cropped to fit our dimensions. The small cat image was taken for free from unslpash.com
(https://unsplash.com/photos/gKXKBY-C-Dk) and cropped. Lastly, the red/black square image
was made by us on an image editor.

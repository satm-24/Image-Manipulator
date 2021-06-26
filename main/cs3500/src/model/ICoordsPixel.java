package model;

import model.IPixel;
import model.Pixel;

public interface ICoordsPixel extends IPixel {

  /**
   *
   * @return
   */
  int getY();

  /**
   *
   * @return
   */
  int getX();

  /**
   *
   * @return
   */
  Pixel getPixel();


}

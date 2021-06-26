package controller;

/** Represents a 2-dimensional position */
class Location implements ILocation {
  public final double x;
  public final double y;

  /**
   *
   * @param x
   * @param y
   */
  public Location(double x, double y){
    this.x = x;
    this.y = y;
  }

  @Override
  public double getX() {
    return x;
  }

  @Override
  public double getY() {
    return y;
  }
}
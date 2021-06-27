package controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for Locations.
 */
public class LocationTest {

  @Test
  public void testGetY() {

    Location l = new Location(5.0, 6.0);

    assertEquals(6.0, l.getY(), 0.001);

  }

  @Test
  public void testGetX() {

    Location l = new Location(5.0, 6.0);

    assertEquals(5.0, l.getX(), 0.001);

  }

}

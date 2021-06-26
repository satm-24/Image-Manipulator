package view;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Represents tests for layers.
 */
public class LayerTest {

  @Test
  public void testGetVisibility() {
    ILayer visible = new Layer(true, "one");
    ILayer invisible = new Layer(false, "two");
    assertEquals(true, visible.getVisibility());
    assertEquals(false, invisible.getVisibility());
  }

  @Test
  public void testGetName() {
    ILayer testLayer = new Layer(true, "one");
    assertEquals("one", testLayer.getName());
  }

  @Test
  public void testGetFileLocation() {
    ILayer test = new Layer(true, "qwer");
    assertEquals("", test.getFileLocation());
  }

  @Test
  public void testSetFileLocation() {
    ILayer test = new Layer(true, "test");
    assertEquals("", test.getFileLocation());
    test.setFileLocation("downloads");
    assertEquals("downloads", test.getFileLocation());
  }

  @Test
  public void testSetVisibility() {
    ILayer visible = new Layer(true, "one");
    ILayer invisible = new Layer(false, "two");
    assertEquals(true, visible.getVisibility());
    assertEquals(false, invisible.getVisibility());
    visible.setVisibility(false);
    invisible.setVisibility(true);
    assertEquals(false, visible.getVisibility());
    assertEquals(true, invisible.getVisibility());
  }
}
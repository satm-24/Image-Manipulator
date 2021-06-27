package view;

import static org.junit.Assert.assertEquals;

import controller.MockController;
import org.junit.Test;

/**
 * Test class for different parts of the image processing gui.
 */
public class GraphicalViewTest {
  @Test
  public void testSaveButtonClick() {
    Appendable vOut = new StringBuilder();
    Appendable cOut = new StringBuilder();
    MockView mockView = new MockView(vOut);
    MockController mockController = new MockController(cOut);
    mockView.addViewEventListener(mockController);

    mockView.emitSaveEvent();

    assertEquals("saved", cOut.toString());
  }

  @Test
  public void testLoadButtonClick() {
    Appendable vOut = new StringBuilder();
    Appendable cOut = new StringBuilder();
    MockView mockView = new MockView(vOut);
    MockController mockController = new MockController(cOut);
    mockView.addViewEventListener(mockController);

    mockView.emitLoadEvent();

    assertEquals("loaded", cOut.toString());
  }

  @Test
  public void testAddButtonClick() {
    Appendable vOut = new StringBuilder();
    Appendable cOut = new StringBuilder();
    MockView mockView = new MockView(vOut);
    MockController mockController = new MockController(cOut);
    mockView.addViewEventListener(mockController);

    mockView.emitAddEvent();

    assertEquals("added name", cOut.toString());
  }

  @Test
  public void testRemoveButtonClick() {
    Appendable vOut = new StringBuilder();
    Appendable cOut = new StringBuilder();
    MockView mockView = new MockView(vOut);
    MockController mockController = new MockController(cOut);
    mockView.addViewEventListener(mockController);

    mockView.emitRemoveEvent();

    assertEquals("removed", cOut.toString());
  }

  @Test
  public void testSharpenButtonClick() {
    Appendable vOut = new StringBuilder();
    Appendable cOut = new StringBuilder();
    MockView mockView = new MockView(vOut);
    MockController mockController = new MockController(cOut);
    mockView.addViewEventListener(mockController);

    mockView.emitSharpenEvent();

    assertEquals("sharpened", cOut.toString());
  }

  @Test
  public void testBlurButtonClick() {
    Appendable vOut = new StringBuilder();
    Appendable cOut = new StringBuilder();
    MockView mockView = new MockView(vOut);
    MockController mockController = new MockController(cOut);
    mockView.addViewEventListener(mockController);

    mockView.emitBlurEvent();

    assertEquals("blurred", cOut.toString());
  }

  @Test
  public void testSepiaButtonClick() {
    Appendable vOut = new StringBuilder();
    Appendable cOut = new StringBuilder();
    MockView mockView = new MockView(vOut);
    MockController mockController = new MockController(cOut);
    mockView.addViewEventListener(mockController);

    mockView.emitSepiaEvent();

    assertEquals("sepia", cOut.toString());
  }

  @Test
  public void testGreyscaleButtonClick() {
    Appendable vOut = new StringBuilder();
    Appendable cOut = new StringBuilder();
    MockView mockView = new MockView(vOut);
    MockController mockController = new MockController(cOut);
    mockView.addViewEventListener(mockController);

    mockView.emitGreyscaleEvent();

    assertEquals("greyscale", cOut.toString());
  }
}

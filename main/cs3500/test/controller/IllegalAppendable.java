package controller;

import java.io.IOException;

/**
 * Represents an appendable class that throws an IllegalArgumentException when called to append a
 * given value.
 */
public class IllegalAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("IllegalAppendable cannot append.");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("IllegalAppendable cannot append.");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("IllegalAppendable cannot append.");
  }
}

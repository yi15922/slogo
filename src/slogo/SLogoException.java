package slogo;

import java.io.Serial;

/**
 * This class represents what might go wrong when using SLogo.
 *
 * @author Robert C. Duvall
 * @author Patrick Liu
 */
public class SLogoException extends RuntimeException {
  // for serialization
  @Serial
  private static final long serialVersionUID = 1L;

  /**
   * Create an exception based on an issue in our code.
   */
  public SLogoException (String message, Object ... values) {
    super(String.format(message, values));
  }

  /**
   * Create an exception based on a caught exception with a different message.
   */
  public SLogoException (Throwable cause, String message, Object ... values) {
    super(String.format(message, values), cause);
  }

  /**
   * Create an exception based on a caught exception, with no additional message.
   */
  public SLogoException (Throwable cause) {
    super(cause);
  }
}


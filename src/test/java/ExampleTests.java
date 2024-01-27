import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Note: These example tests do not test any real requirements in the project!
 */
public class ExampleTests {

  /**
   * Tests requirement: Example.1
   */
  @Test
  @Disabled("Example test")
  public void testAssertionsEquals() {
    int result = Math.max(1, 10);

    Assertions.assertEquals(10, result);
  }

  /**
   * Tests requirement: Example.2
   */
  @Test
  @Disabled("Example test")
  public void testAssertionsThrows() {
    // this has to throw specified exception to pass the test
    Assertions.assertThrows(ArithmeticException.class, () -> {
      int result = 100 / 0;
    });
  }

}

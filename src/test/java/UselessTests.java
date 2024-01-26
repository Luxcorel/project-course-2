import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UselessTests {

  @Test
  public void testAssertionsEquals() {
    int result = Math.max(1, 10);

    Assertions.assertEquals(10, result);
  }

  @Test
  public void testAssertionsThrows() {
    // this has to throw specified exception to pass the test
    Assertions.assertThrows(ArithmeticException.class, () -> {
      int result = 100 / 0;
    });
  }

}

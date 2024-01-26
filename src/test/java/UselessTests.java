import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UselessTests {

  @Test
  public void testTest() {
    int result = Math.max(1, 10);

    Assertions.assertEquals(10, result);
  }
}

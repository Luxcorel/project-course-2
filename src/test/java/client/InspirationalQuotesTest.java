package client;

import client.external.InspirationalQuotes;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InspirationalQuotesTest {

  /**
   * Test case ID: TC-49.
   * Requirements: F30.
   */

  @Test
  public void testGetRandomQuote() {
    InspirationalQuotes quotes = new InspirationalQuotes();

    JsonObject quote = quotes.getRandomQuote();

    assertNotNull(quote, "Quote should not be null");
    assertTrue(quote.has("q"), "Quote should have a 'q' property");
    assertTrue(quote.has("a"), "Quote should have an 'a' property");
  }
}

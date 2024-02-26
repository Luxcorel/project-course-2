package client.external;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The InspirationalQuotes class provides a method to fetch inspirational quotes from the ZenQuotes API.
 */
public class InspirationalQuotes {
  private static final String API_URL = "https://zenquotes.io/api/random";

  /**
   * Fetches a random inspirational quote from the ZenQuotes API.
   *
   * @return A JsonObject representing the quote. The object includes details such as the quote text and author.
   *         Returns null if an exception occurs during the API request.
   */
  public JsonObject getRandomQuote() {
    StringBuilder result = new StringBuilder();

    try {
      URI uri = new URI(API_URL);
      URL url = uri.toURL();
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line;
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }
      rd.close();

      // Convert string to JSON object using Gson
      JsonElement je = JsonParser.parseString(result.toString());
      JsonArray jsonArray = je.getAsJsonArray();
      JsonObject json = jsonArray.get(0).getAsJsonObject();
      return json;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
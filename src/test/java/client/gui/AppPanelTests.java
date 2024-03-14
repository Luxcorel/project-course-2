package client.gui;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import client.ActivityManager;
import client.ClientController;
import client.external.InspirationalQuotes;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Timer;
import javax.swing.JDialog;
import org.junit.jupiter.api.Test;

public class AppPanelTests {

  /**
   * Test case ID: TC-55.
   * Requirements: F30.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void inspirationalQuoteDisplaysOnApplicationStartup() {
    Timer timerMock = mock(Timer.class);
    JDialog dialogMock = mock(JDialog.class);
    InspirationalQuotes quotesMock = mock(InspirationalQuotes.class);
    JsonObject dummyQuote = new JsonObject();
    dummyQuote.add("q", new JsonPrimitive("Test quote"));
    dummyQuote.add("a", new JsonPrimitive("Test author"));
    when(quotesMock.getRandomQuote()).thenReturn(dummyQuote);

    WelcomeMessageUI welcomeMessageUI = new WelcomeMessageUI(timerMock, quotesMock, dialogMock);
    welcomeMessageUI.showWelcomeMessage();

    verify(dialogMock).setVisible(true);
  }

}

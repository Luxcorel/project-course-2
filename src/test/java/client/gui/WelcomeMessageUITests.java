package client.gui;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import client.external.InspirationalQuotes;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JDialog;
import org.junit.jupiter.api.Test;

public class WelcomeMessageUITests {

  /**
   * Test case ID: TC-52.
   * Requirements: F035.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void inspirationalQuoteWindowScheduledToCloseAfter5Seconds() {
    Timer timerMock = mock(Timer.class);
    JDialog dialogMock = mock(JDialog.class);
    InspirationalQuotes quotesMock = mock(InspirationalQuotes.class);
    JsonObject dummyQuote = new JsonObject();
    dummyQuote.add("q", new JsonPrimitive("Test quote"));
    dummyQuote.add("a", new JsonPrimitive("Test author"));
    when(quotesMock.getRandomQuote()).thenReturn(dummyQuote);

    WelcomeMessageUI welcomeMessageUI = new WelcomeMessageUI(timerMock, quotesMock, dialogMock);
    welcomeMessageUI.showWelcomeMessage();

    verify(timerMock).schedule(any(TimerTask.class), eq(5000L));
  }

  /**
   * Test case ID: TC-53.
   * Requirements: F035.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void inspirationalQuoteAutoCloseTaskClosesWindow() {
    Timer timerMock = mock(Timer.class);
    JDialog dialogMock = mock(JDialog.class);
    InspirationalQuotes quotesMock = mock(InspirationalQuotes.class);
    JsonObject dummyQuote = new JsonObject();
    dummyQuote.add("q", new JsonPrimitive("Test quote"));
    dummyQuote.add("a", new JsonPrimitive("Test author"));
    when(quotesMock.getRandomQuote()).thenReturn(dummyQuote);

    WelcomeMessageUI welcomeMessageUI = new WelcomeMessageUI(timerMock, quotesMock, dialogMock);
    welcomeMessageUI.showWelcomeMessage();

    doAnswer(invocation -> {
      TimerTask task = invocation.getArgument(0);
      task.run();
      return null;
    }).when(timerMock).schedule(any(TimerTask.class), eq(5000L));

    welcomeMessageUI.showWelcomeMessage();

    verify(dialogMock).dispose();
  }

  /**
   * Test case ID: TC-54.
   * Requirements: F035.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void inspirationalQuoteFetchFailure_shouldNotShowQuoteWindow() {
    Timer timerMock = mock(Timer.class);
    JDialog dialogMock = mock(JDialog.class);
    InspirationalQuotes quotesMock = mock(InspirationalQuotes.class);
    when(quotesMock.getRandomQuote()).thenReturn(null);

    WelcomeMessageUI welcomeMessageUI = new WelcomeMessageUI(timerMock, quotesMock, dialogMock);
    welcomeMessageUI.showWelcomeMessage();

    verify(dialogMock, never()).setVisible(true);
  }

}
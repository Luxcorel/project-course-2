package client.gui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import client.Activity;
import client.ClientController;
import client.external.InspirationalQuotes;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Optional;
import java.util.Timer;
import javax.swing.JDialog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

  /**
   * Test case ID: TC-62.
   * Requirements: F010a.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void activityNotificationShouldShowAfterTimerReachesZero_TimerSetTo0Minutes() {
    ClientController clientControllerMock = mock(ClientController.class);
    MainPanel mainPanelMock = mock(MainPanel.class);
    WelcomeMessageUI welcomeMessageMock = mock(WelcomeMessageUI.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    appPanel.startTimer(0);

    verify(appPanel, timeout(1000)).showNotification(randomActivity);
  }

  /**
   * Test case ID: TC-63.
   * Requirements: F010a.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void activityNotificationShouldShowAfterTimerReachesZero_TimerSetTo1Minute() {
    ClientController clientControllerMock = mock(ClientController.class);
    MainPanel mainPanelMock = mock(MainPanel.class);
    WelcomeMessageUI welcomeMessageMock = mock(WelcomeMessageUI.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    appPanel.startTimer(1);

    verify(appPanel, after(61_000)).showNotification(randomActivity);
  }

  /**
   * Test case ID: TC-64.
   * Requirements: F010a.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void activityNotificationShouldGrabRandomActivity() {
    ClientController clientControllerMock = mock(ClientController.class);
    MainPanel mainPanelMock = mock(MainPanel.class);
    WelcomeMessageUI welcomeMessageMock = mock(WelcomeMessageUI.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    appPanel.startTimer(0);

    verify(clientControllerMock, timeout(1000)).getActivity();
  }

  /**
   * Test case ID: TC-65.
   * Requirements: F006.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void startActivityTimerWithNegativeNumber_ShouldThrowError() {
    ClientController clientControllerMock = mock(ClientController.class);
    MainPanel mainPanelMock = mock(MainPanel.class);
    WelcomeMessageUI welcomeMessageMock = mock(WelcomeMessageUI.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    assertThrows(IllegalArgumentException.class, () -> appPanel.startTimer(-1));
  }

  /**
   * Test case ID: TC-66.
   * Requirements: F006.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void restartActivityTimer_1MinuteTo0Minutes() {
    ClientController clientControllerMock = mock(ClientController.class);
    MainPanel mainPanelMock = mock(MainPanel.class);
    WelcomeMessageUI welcomeMessageMock = mock(WelcomeMessageUI.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    appPanel.startTimer(1);
    appPanel.startTimer(0);

    verify(appPanel, timeout(1000)).showNotification(randomActivity);
  }

  /**
   * Test case ID: TC-67.
   * Requirements: F006.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void setTimerIntervalToNegativeNumber_ShouldThrowError() {
    ClientController clientControllerMock = mock(ClientController.class);
    MainPanel mainPanelMock = mock(MainPanel.class);
    WelcomeMessageUI welcomeMessageMock = mock(WelcomeMessageUI.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    assertThrows(IllegalArgumentException.class, () -> appPanel.setTimerInterval(-1));
  }

  /**
   * Test case ID: TC-68.
   * Requirements: F006.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void setTimerIntervalTo1Minute_ShouldUpdateWindowTitle() {
    ClientController clientControllerMock = mock(ClientController.class);
    MainPanel mainPanelMock = mock(MainPanel.class);
    WelcomeMessageUI welcomeMessageMock = mock(WelcomeMessageUI.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    appPanel.setTimerInterval(1);

    verify(clientControllerMock).setTitle(anyString());
  }

  /**
   * Test case ID: TC-69.
   * Requirements: F006.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void setTimerIntervalTo2Minutes_ShouldUpdateWindowTitle() {
    ClientController clientControllerMock = mock(ClientController.class);
    MainPanel mainPanelMock = mock(MainPanel.class);
    WelcomeMessageUI welcomeMessageMock = mock(WelcomeMessageUI.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    appPanel.setTimerInterval(2);

    verify(clientControllerMock).setTitle(anyString());
  }

}

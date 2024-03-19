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
import client.ActivityTimer;
import client.ClientController;
import client.IActivityTimer;
import client.external.InspirationalQuotes;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.awt.Component;
import java.util.Optional;
import java.util.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
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
    IMessageProvider messageProviderMock = new TestMessageProvider() {
      @Override
      public int showOptionDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType, Icon icon, Object[] options, Object initialValue) {
        return -1;
      }
    };
    IActivityTimer activityTimer = spy(ActivityTimer.class);
    SoundPlayer soundPlayerMock = mock(SoundPlayer.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock, messageProviderMock, activityTimer, soundPlayerMock));
    activityTimer.setCallback(appPanel);

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    activityTimer.setTimerInterval(0);
    activityTimer.startTimer();

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
    IMessageProvider messageProviderMock = new TestMessageProvider() {
      @Override
      public int showOptionDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType, Icon icon, Object[] options, Object initialValue) {
        return -1;
      }
    };
    IActivityTimer activityTimer = spy(ActivityTimer.class);
    SoundPlayer soundPlayerMock = mock(SoundPlayer.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock, messageProviderMock, activityTimer, soundPlayerMock));
    activityTimer.setCallback(appPanel);

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    activityTimer.setTimerInterval(1);
    activityTimer.startTimer();

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
    IMessageProvider messageProviderMock = new TestMessageProvider();
    IActivityTimer activityTimer = spy(ActivityTimer.class);
    SoundPlayer soundPlayerMock = mock(SoundPlayer.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock, messageProviderMock, activityTimer, soundPlayerMock));
    activityTimer.setCallback(appPanel);

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    activityTimer.setTimerInterval(0);
    activityTimer.startTimer();

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
    IMessageProvider messageProviderMock = new TestMessageProvider();
    IActivityTimer activityTimer = spy(ActivityTimer.class);
    SoundPlayer soundPlayerMock = mock(SoundPlayer.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock, messageProviderMock, activityTimer, soundPlayerMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    assertThrows(IllegalArgumentException.class, () -> activityTimer.setTimerInterval(-1));
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
    IMessageProvider messageProviderMock = new TestMessageProvider();
    IActivityTimer activityTimer = spy(ActivityTimer.class);
    SoundPlayer soundPlayerMock = mock(SoundPlayer.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock, messageProviderMock,
        activityTimer, soundPlayerMock));
    activityTimer.setCallback(appPanel);

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    activityTimer.setTimerInterval(1);
    activityTimer.startTimer();
    activityTimer.setTimerInterval(0);
    activityTimer.startTimer();

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
    IMessageProvider messageProviderMock = new TestMessageProvider();
    IActivityTimer activityTimerMock = mock(ActivityTimer.class);
    SoundPlayer soundPlayerMock = mock(SoundPlayer.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock, messageProviderMock, activityTimerMock, soundPlayerMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    assertThrows(IllegalArgumentException.class, () -> appPanel.setTitleToInterval(-1));
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
    IMessageProvider messageProviderMock = new TestMessageProvider();
    IActivityTimer activityTimerMock = mock(ActivityTimer.class);
    SoundPlayer soundPlayerMock = mock(SoundPlayer.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock, messageProviderMock, activityTimerMock, soundPlayerMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    appPanel.setTitleToInterval(1);

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
    IMessageProvider messageProviderMock = new TestMessageProvider();
    IActivityTimer activityTimerMock = mock(ActivityTimer.class);
    SoundPlayer soundPlayerMock = mock(SoundPlayer.class);

    AppPanel appPanel = spy(new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock, messageProviderMock, activityTimerMock, soundPlayerMock));

    Activity randomActivity = new Activity();
    randomActivity.setActivityName("Test activity");
    randomActivity.setActivityInstruction("Test instruction");
    randomActivity.setActivityInfo("Test info");
    when(clientControllerMock.getActivity()).thenReturn(Optional.of(randomActivity));

    doAnswer(args -> null)
        .when(appPanel)
        .showNotification(randomActivity);

    appPanel.setTitleToInterval(2);

    verify(clientControllerMock).setTitle(anyString());
  }

  /**
   * Test case ID: TC-70.
   * Requirements: F034.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void activityNotificationPlaysNotificationSound() {
    ClientController clientControllerMock = mock(ClientController.class);
    MainPanel mainPanelMock = mock(MainPanel.class);
    WelcomeMessageUI welcomeMessageMock = mock(WelcomeMessageUI.class);
    IActivityTimer activityTimerMock = mock(ActivityTimer.class);
    SoundPlayer soundPlayerMock = mock(SoundPlayer.class);
    IMessageProvider messageProviderMock = new TestMessageProvider() {
      @Override
      public int showOptionDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType, Icon icon, Object[] options, Object initialValue) {
        return -1;
      }
    };

    Activity activity = new Activity();
    activity.setActivityName("Test activity");
    activity.setActivityInstruction("Test instruction");
    activity.setActivityInfo("Test info");

    AppPanel appPanel = new AppPanel(mainPanelMock, clientControllerMock, welcomeMessageMock, messageProviderMock, activityTimerMock, soundPlayerMock);
    appPanel.showNotification(activity);

    verify(soundPlayerMock).play();
  }

}

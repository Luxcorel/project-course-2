package client;

import client.notifications.WindowsNotification;
import org.junit.jupiter.api.Test;
import java.awt.TrayIcon;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

public class WindowsNotificationTest {

  /**
   * Test case ID: TC-50.
   * Requirements: F29.
   */

  @Test
  public void testDisplayNotification() throws Exception {
    TrayIcon trayIconMock = mock(TrayIcon.class);

    WindowsNotification windowsNotification = new WindowsNotification();

    Field trayIconField = WindowsNotification.class.getDeclaredField("trayIcon");
    trayIconField.setAccessible(true);
    trayIconField.set(windowsNotification, trayIconMock);

    windowsNotification.displayNotification("Test Title", "Test Message");

    verify(trayIconMock, times(1)).displayMessage("Test Title", "Test Message", TrayIcon.MessageType.INFO);
  }
}

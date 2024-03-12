package client;

import client.OSDetection.OS;
import client.notifications.WindowsNotification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledOnOs;
import java.awt.GraphicsEnvironment;
import java.awt.TrayIcon;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

public class WindowsNotificationTest {

  /**
   * Test case ID: TC-50.
   * Requirements: F29.
   */

  public static boolean isHeadless() {
    return GraphicsEnvironment.isHeadless();
  }

  @DisabledIf("isHeadless")
  @EnabledOnOs(org.junit.jupiter.api.condition.OS.WINDOWS)
  @Test
  public void testDisplayNotification() throws Exception {
    TrayIcon trayIconMock = mock(TrayIcon.class);

    WindowsNotification windowsNotification = new WindowsNotification();

    Field trayIconField = WindowsNotification.class.getDeclaredField("trayIcon");
    trayIconField.setAccessible(true);
    trayIconField.set(windowsNotification, trayIconMock);

    windowsNotification.displayNotification("Test Title", "Test Message");

    verify(trayIconMock, times(1)).displayMessage("Test Title", "Test Message",
        TrayIcon.MessageType.INFO);
  }
}

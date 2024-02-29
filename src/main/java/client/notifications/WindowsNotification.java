package client.notifications;

import client.gui.MainFrame;
import javax.swing.JFrame;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;

/**
 * This class is used to create and display Windows notifications
 */
public class WindowsNotification {
  private TrayIcon trayIcon;
  private Image image;

  /**
   * Initializes the tray icon and adds it to the system tray
   * Also sets an action listener to the tray icon
   */
  public WindowsNotification() {
    SystemTray tray = SystemTray.getSystemTray();
    image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    trayIcon = new TrayIcon(image, "EDIM");

    try {
      tray.add(trayIcon);
    } catch (AWTException e) {
      throw new RuntimeException(e);
    }

    trayIcon.addActionListener(e -> {
      JFrame mainFrame = MainFrame.getInstance();

      mainFrame.setAlwaysOnTop(true);
      mainFrame.setAlwaysOnTop(false);
    });
  }

  /**
   * Displays a notification with the given title and message.
   *
   * @param title The title of the notification.
   * @param message The message of the notification.
   */
  public void displayNotification(String title, String message) {
    trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
  }
}
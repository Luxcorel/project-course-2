package client;

import client.gui.AppPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityTimer implements IActivityTimer{
  private final ClientController clientController;
  private Timer timer;
  private int timeLeftInSeconds;

  public ActivityTimer(ClientController clientController) {
    this.clientController = clientController;
  }

  /**
   * Starts a timer with the given number of minutes.
   * If a timer is already running, it is canceled and a new one is started.
   * The timer will show a notification when it reaches 0.
   *
   * @param minutes the number of minutes to start the timer with
   * @author Johannes Rosengren, Samuel Carlsson
   */
  @Override
  public void startTimer(int minutes, AppPanel appPanel) {
    if (timer != null) {
      timer.cancel();
    }

    timeLeftInSeconds = (minutes * 60);

    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        int minutes = timeLeftInSeconds / 60;
        int seconds = timeLeftInSeconds % 60;

        if (timeLeftInSeconds == 0) {
          String time = String.format("Timer: %d:%02d", minutes, seconds);
          appPanel.setTimeLeftText(time);
          SwingUtilities.invokeLater(
              () -> {
                Optional<Activity> activity = clientController.getActivity();
                if (activity.isEmpty()) {
                  JOptionPane.showMessageDialog(appPanel,
                      "Could not find any saved activities! Add a new activity before you start the timer.",
                      "No Activities Found", JOptionPane.ERROR_MESSAGE);

                  appPanel.setStartTimerText("Start Timer");
                  return;
                }

                appPanel.showNotification(activity.get());
              });
          timer.cancel();
        }

        timeLeftInSeconds--;

        String time = String.format("Timer: %d:%02d", minutes, seconds);
        appPanel.setTimeLeftText(time);
      }
    }, 0, 1000);
  }
}

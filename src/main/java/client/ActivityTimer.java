package client;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is responsible for the timer that counts down from a chosen interval.
 *
 * @author Samuel Carlsson
 * @version 1.0
 */
public class ActivityTimer implements IActivityTimer{
  private IActivityTimerCallback callback;
  private Timer timer;
  private int timeLeftInSeconds;
  private int chosenMinuteInterval;

  @Override
  public void startTimer() {
    if(callback == null) {
      throw new IllegalStateException("Callback not set");
    }

    if (timer != null) {
      timer.cancel();
    }

    timeLeftInSeconds = (chosenMinuteInterval * 60);

    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        int minutes = timeLeftInSeconds / 60;
        int seconds = timeLeftInSeconds % 60;

        timeLeftInSeconds--;

        callback.timeRemainingCallback(minutes, seconds);

        if (minutes == 0 && seconds == 0) {
          callback.timesUpCallback();
        };
      }
    }, 0, 1000);
  }

  @Override
  public void stopTimer() {
    if (timer != null) {
      timer.cancel();
    }
  }

  public void setCallback(IActivityTimerCallback callback) {
    this.callback = callback;
  }

  @Override
  public void setTimerInterval(int minutes) {
    chosenMinuteInterval = minutes;
  }

  @Override
  public int getTimerInterval() {
    return chosenMinuteInterval;
  }
}

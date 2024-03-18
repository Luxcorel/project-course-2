package client;

/**
 * This interface is the callback interface that is implemented by AppPanel.
 *
 * @author Samuel Carlsson
 * @version 1.0
 */
public interface IActivityTimerCallback {
  void timeRemainingCallback(int minutesRemaining, int secondsRemaining);
  void timesUpCallback();
}

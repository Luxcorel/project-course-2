package client;

/**
 * This interface is responsible for the timer that counts down from a chosen interval.
 *
 * @author Samuel Carlsson
 * @version 1.0
 */
public interface IActivityTimer {
  void startTimer();
  void stopTimer();
  void setTimerInterval(int minutes);
  int getTimerInterval();
  void setCallback(IActivityTimerCallback callback);
}

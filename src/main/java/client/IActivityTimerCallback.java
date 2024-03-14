package client;

public interface IActivityTimerCallback {
  void timeRemainingCallback(int minutesRemaining, int secondsRemaining);
  void timesUpCallback();
}

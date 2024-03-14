package client;

public interface IActivityTimer {
  void startTimer();
  void stopTimer();
  void setTimerInterval(int minutes);
  int getTimerInterval();
}

package client;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is used to represent a completed activity in the activity history list in
 * {@link client.gui.AppPanel}. It contains the completed activity and a time of completion.
 *
 * @author Johannes Rosengren
 */
public record ActivityListItem(Activity completedActivity, LocalDateTime timeOfCompletion) {

  /**
   * @return the activity name and the time of completion as a string.
   */
  @Override
  public String toString() {
    return completedActivity.getActivityName() + " " + timeOfCompletion
        .format(DateTimeFormatter.ofPattern("H:mm"));
  }

}

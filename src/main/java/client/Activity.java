package client;

import java.io.Serializable;
import java.util.Objects;
import javax.swing.ImageIcon;

/**
 * This class handles the information about an Activity object.
 *
 * @author Carolin Nordstrom, Oscar Kareld, Chanon Borgstrom, Sofia Hallberg, Edvin Topalovic.
 * @version 1.1
 */

public class Activity implements Serializable {

  private static final long serialVersionUID = 200428L;
  private String activityName;
  private String activityInstruction;
  private String activityInfo;
  private boolean isCompleted = false;
  private transient ImageIcon activityImage;
  private String imagePath;
  private String activityID;

  public Activity(String activityName) {
    this.activityName = activityName;
  }

  public Activity() {}

  public String getActivityInfo() {
    return activityInfo;
  }

  public void setActivityInfo(String activityInfo) {
    this.activityInfo = activityInfo;
  }

  public String getActivityName() {
    return activityName;
  }

  public void setActivityName(String activityName) {
    this.activityName = activityName;
  }

  public String getActivityInstruction() {
    return activityInstruction;
  }

  public void setActivityInstruction(String activityInstruction) {
    this.activityInstruction = activityInstruction;
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public void setCompleted(boolean completed) {
    isCompleted = completed;
  }

  public ImageIcon getActivityImage() {
    return activityImage;
  }

  public void setActivityImage(String imagePath) {
    if (imagePath == null) {
      imagePath = "";
      return;
    }
    if (imagePath.isEmpty()) {
      imagePath = "";
      return;
    }

    this.imagePath = imagePath;
    activityImage = new ImageIcon(imagePath);
  }

  public String getImagePath() {
    return imagePath;
  }

  public void createActivityImage(String fileName) {
    activityImage = new ImageIcon(fileName);
  }

  public void setActivityID(String activityID) {
    this.activityID = activityID;
  }

  public String getActivityID() {
    return activityID;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Activity activity = (Activity) o;

    if (isCompleted != activity.isCompleted) {
      return false;
    }
    if (!Objects.equals(activityName, activity.activityName)) {
      return false;
    }
    if (!Objects.equals(activityInstruction, activity.activityInstruction)) {
      return false;
    }
    if (!Objects.equals(activityInfo, activity.activityInfo)) {
      return false;
    }
    if (!Objects.equals(imagePath, activity.imagePath)) {
      return false;
    }
    return Objects.equals(activityID, activity.activityID);
  }

  @Override
  public int hashCode() {
    int result = activityName != null ? activityName.hashCode() : 0;
    result = 31 * result + (activityInstruction != null ? activityInstruction.hashCode() : 0);
    result = 31 * result + (activityInfo != null ? activityInfo.hashCode() : 0);
    result = 31 * result + (isCompleted ? 1 : 0);
    result = 31 * result + (imagePath != null ? imagePath.hashCode() : 0);
    result = 31 * result + (activityID != null ? activityID.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Activity{" +
           "activityName='" + activityName + '\'' +
           ", activityInstruction='" + activityInstruction + '\'' +
           ", activityInfo='" + activityInfo + '\'' +
           ", isCompleted=" + isCompleted +
           ", activityImage=" + activityImage +
           ", imagePath='" + imagePath + '\'' +
           ", activityID='" + activityID + '\'' +
           '}';
  }
}



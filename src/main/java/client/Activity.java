package client;

import java.io.Serializable;
import java.util.Calendar;
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
  private String activityUser;
  private ImageIcon activityImage;
  private int activityID;

  public Activity(String activityName) {
    this.activityName = activityName;
  }

  public Activity() {}

  public String getTime() {
    Calendar cal = Calendar.getInstance();
    String datum = cal.getTime().getHours() + ":" + cal.getTime().getMinutes();
    return datum;

  }

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

  public String getActivityUser() {
    return activityUser;
  }

  public void setActivityUser(String activityUser) {
    this.activityUser = activityUser;
  }

  public ImageIcon getActivityImage() {
    return activityImage;
  }

  public void setActivityImage(ImageIcon icon) {
    activityImage = icon;
  }

  public void createActivityImage(String fileName) {
    activityImage = new ImageIcon(fileName);
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public int getActivityID() {
    return activityID;
  }
}



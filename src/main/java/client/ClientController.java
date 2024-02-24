package client;

import client.gui.MainFrame;

/**
 * This class manages the logic for the Client and controls the data flow.
 *
 * @author Carolin Nordstrom, Oscar Kareld, Chanon Borgstrom, Sofia Hallberg.
 * @author Johannes Rosengren
 * @version 1.0
 */

public class ClientController {

  private final String programDataPath;
  private MainFrame mainFrame;
  private final ActivityManager activityManager;

  /**
   * Constructor for the ClientController.
   *
   * @param programDataFolder The path to the program data folder excluding "/". Example: "files"
   */
  public ClientController(String programDataFolder) {
    this.programDataPath = programDataFolder;
    activityManager = new ActivityManager(
        programDataFolder + "/activities.json");
    mainFrame = new MainFrame(this);
  }

  public void logOut() {
    System.exit(0);
  }

  public ActivityManager getActivityManager() {
    return activityManager;
  }

  /**
   * @param activityName        The name of the activity.
   * @param activityInstruction The instruction for the activity.
   * @param activityInfo        The information for the activity.
   * @return The {@link Activity} that was added.
   */
  public Activity addActivity(String activityName, String activityInstruction,
      String activityInfo) {
    return activityManager.createActivity(activityName, activityInstruction, activityInfo);
  }

  /**
   * @param activityName        The name of the activity.
   * @param activityInstruction The instruction for the activity.
   * @param activityInfo        The information for the activity.
   * @param imagePath           The path to the image for the activity.
   * @return The {@link Activity} that was added.
   */
  public Activity addActivity(String activityName, String activityInstruction,
      String activityInfo, String imagePath) {
    return activityManager.createActivity(activityName, activityInstruction, activityInfo,
        imagePath);
  }

}

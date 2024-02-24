package client;

import client.gui.MainFrame;
import java.util.Optional;

/**
 * This class manages the logic for the Client and controls the data flow.
 *
 * @author Carolin Nordstrom, Oscar Kareld, Chanon Borgstrom, Sofia Hallberg.
 * @author Johannes Rosengren
 * @version 1.0
 */

public class ClientController {

  private final String programDataPath;
  private final MainFrame mainFrame;
  private final ActivityManager activityManager;

  /**
   * Constructor for the ClientController.
   *
   * @param programDataFolder The path to the program data folder excluding "/". Example: "files"
   */
  public ClientController(String programDataFolder) {
    this.programDataPath = programDataFolder;
    activityManager = new ActivityManager(
        programDataPath + "/activities.json");
    mainFrame = new MainFrame(this);
  }

  public void logOut() {
    System.exit(0);
  }

  /**
   * Returns a random activity from the activity list.
   *
   * @return A random {@link Activity}
   */
  public Optional<Activity> getActivity() {
    return activityManager.getActivity();
  }

  public Optional<Activity> getActivity(String id) {
    return activityManager.getActivity(id);
  }

  /**
   * Adds an activity to the activity queue.
   *
   * @param activity The activity to be queued.
   */
  public void enqueueActivity(Activity activity) {
    activityManager.enqueueActivity(activity);
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

  /**
   * Changes the main frame's title.
   *
   * @param title The new title of the main frame.
   */
  public void setTitle(String title) {
    mainFrame.setTitle(title);
  }

}

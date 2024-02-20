package client;

import client.gui.MainFrame;

/**
 * This class manages the logic for the Client and controls the data flow.
 *
 * @author Carolin Nordstrom, Oscar Kareld, Chanon Borgstrom, Sofia Hallberg.
 * @version 1.0
 */

public class ClientController {

  private MainFrame mainFrame;
  private ActivityManager activityManager;

  /**
   * Constructs a MainFrame and a ClientCommunicationController object. Then calls the method
   * createUser.
   */
  public ClientController() {
    mainFrame = new MainFrame(this);
    activityManager = new ActivityManager(
        "files/activities.txt"); // TODO: environment variable needed instead of this local one!
  }

  /**
   * Sets the UserType to LOGOUT and sends the user object to ClientCommunicationController.
   */
  public void logOut() {
    System.exit(0);
  }

  public ActivityManager getActivityManager() {
    return activityManager;
  }

}

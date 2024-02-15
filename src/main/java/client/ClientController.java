package client;

import client.gui.AppPanel;
import client.gui.AppPanel.welcomePane;
import client.gui.MainFrame;
import client.gui.MainPanel;
import server.Activity;
import server.User;
import server.UserType;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * This class manages the logic for the Client and controls the data flow.
 *
 * @author Carolin Nordstrom, Oscar Kareld, Chanon Borgstrom, Sofia Hallberg.
 * @version 1.0
 */

public class ClientController extends Component {

  private MainFrame mainFrame;
  private MainPanel mainPanel;
  private AppPanel appPanel;
  private User user;
  private String className = "Class: ClientController ";

  /**
   * Constructs a MainFrame and a ClientCommunicationController object. Then calls the method
   * createUser.
   */
  public ClientController() {
    mainFrame = new MainFrame(this);
  }

  /**
   * Receives a String and creates a new User object and calls the logIn method.
   *
   * @param userName
   */
  public void createUser(String userName) {
    user = new User(userName);
    logIn();
    ImageIcon welcomeIcon = new ImageIcon("imagesClient/exercise.png");
    Image image = welcomeIcon.getImage();
    Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

    JOptionPane.showMessageDialog(null, "Välkommen " + userName + "!"
                                        + "\nEDIM kommer skicka notiser till dig med jämna mellanrum,\n"
                                        +
                                        "med en fysisk aktivitet som ska utföras.\n" +
                                        "Hur ofta du vill ha dessa notiser kan du ställa in själv.",
        "Välkommen till Edim ", 2, new ImageIcon(newImg));
  }

  /**
   * Receives an Activity Object and sends it forth to the ClientCommunicationController.
   *
   * @param activity the received object.
   */
  public void sendActivityToCCC(Activity activity) {
    user.addActivityToList(activity);
  }

  /**
   * Sets the UserType to LOGIN and sends the user object to ClientCommunicationController.
   */
  public void logIn() {
    user.setUserType(UserType.LOGIN);

  }

  /**
   * Sets the UserType to LOGOUT and sends the user object to ClientCommunicationController.
   */
  public void logOut() {
    user.setUserType(UserType.LOGOUT);
  }

  /**
   * Receives an Activity object an sends it forth to MainFrame.
   *
   * @param activity the received object.
   */
  public void receiveNotificationFromCCC(Activity activity) {
    mainFrame.showNotification(activity);
  }

  /**
   * Replaces the temporary user object with the already existing object from the server. If it's a
   * new user, a welcome message is sent.
   *
   * @param user the received object.
   */
  public void receiveUser(User user) {
    UserType userType = user.getUserType();
    this.user = user;
    if (userType == UserType.SENDWELCOME) {
     // mainFrame.sendWelcomeMessage();
    }
  }

  /**
   * Sets a users interval and the UserType to SENDINTERVAL and sends it to
   * {@link ClientCommunicationController}.
   *
   * @param interval the notification interval.
   */
  public void setInterval(int interval) {
    user.setNotificationInterval(interval);
    user.setUserType(UserType.SENDINTERVAL);
  }

}

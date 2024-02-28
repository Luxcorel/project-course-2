package client.gui;

import client.ClientController;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * This class starts the Login window and then awaits the user's input, and finally starts the main
 * GUI.
 *
 * @author Carolin Nordstrom, Oscar Kareld, Chanon Borgstrom, Sofia Hallberg, Edvin Topalovic.
 * @version 1.1
 */

public class MainFrame extends JFrame {

  private ClientController clientController;
  private MainPanel mainPanel;

  /**
   * Receives a clientController object and opens call for the method which opens a GUI window.
   *
   * @param clientController The received ClientController object.
   */
  public MainFrame(ClientController clientController) {
    this.clientController = clientController;

    //Oscars test:
    try {
      UIManager.setLookAndFeel(new NimbusLookAndFeel());
    } catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }

    setBounds(0, 0, 819, 438);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent we) {
        clientController.logOut();
      }
    });
    setLayout(null);
    setTitle("EDIM");
    setResizable(true);
    setLocationRelativeTo(null);
    setVisible(true);
    mainPanel = new MainPanel(this, clientController);
    setContentPane(mainPanel);
  }

  /**
   * Notifies the {@link ClientController} and closes the GUI window.
   */
  public void logOut() {
    clientController.logOut();
  }

}

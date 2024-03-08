package client.gui;

import javax.swing.JOptionPane;
import java.awt.Component;

/**
 * This is the class that is used to show the windows related to adding a new custom activity.
 *
 * @author Samuel Carlsson
 * @version 1.0
 */
public class MessageProvider implements IMessageProvider{

  /**
   * Brings up a confirm dialog.
   *
   * @param parentComponent The parent component
   * @param message         The Object to display
   * @param title           The title for the dialog
   * @param optionType      An integer designating the options available on the dialog
   * @param messageType     An integer designating the kind of message this is
   * @return an integer that represents the option that the user chose.
   * @author Samuel Carlsson
   */
  @Override
  public int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
    return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType, messageType);
  }

  /**
   * Brings up a information message.
   *
   * @param parentComponent The parent component
   * @param message         The Object to display
   * @param title           The title for the dialog
   * @param messageType     An integer designating the kind of message this is
   * @author Samuel Carlsson
   */
  @Override
  public void showMessageDialog(Component parentComponent, Object message, String title, int messageType) {
    JOptionPane.showMessageDialog(parentComponent, message, title, messageType);
  }

}

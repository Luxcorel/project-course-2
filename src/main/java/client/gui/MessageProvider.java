package client.gui;

import javax.swing.Icon;
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

  /**
   * Brings up a information message.
   *
   * @param parentComponent The parent component
   * @param message         The Object to display
   * @author Samuel Carlsson
   */
  @Override
  public void showMessageDialog(Component parentComponent, Object message) {
    JOptionPane.showMessageDialog(parentComponent, message);
  }

  /**
   * Brings up an option dialog.
   *
   * @param parentComponent The parent component
   * @param message         The Object to display
   * @param title           The title for the dialog
   * @param optionType      An integer designating the options available on the dialog
   * @param messageType     An integer designating the kind of message this is
   * @param icon            An icon to display in the dialog
   * @param options         An array of objects that gives the user the buttons to choose from
   * @param initialValue    The object that represents the default selection for the dialog
   * @return an integer that represents the option that the user chose.
   * @author Samuel Carlsson
   */
  @Override
  public int showOptionDialog(Component parentComponent, Object message, String title, int optionType, int messageType, Icon icon, Object[] options, Object initialValue) {
    return JOptionPane.showOptionDialog(parentComponent, message, title, optionType, messageType, icon, options, initialValue);
  }

}

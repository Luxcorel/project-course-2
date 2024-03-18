package client.gui;

import javax.swing.Icon;
import java.awt.Component;
/**
 * This is the interface that is used to show windows related to adding a new custom activity.
 *
 * @author Samuel Carlsson
 * @version 1.0
 */
public interface IMessageProvider {
  int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType);
  void showMessageDialog(Component parentComponent, Object message, String title, int messageType);
  void showMessageDialog(Component parentComponent, Object message);
  int showOptionDialog(Component parentComponent, Object message, String title, int optionType, int messageType, Icon icon, Object[] options, Object initialValue);
}

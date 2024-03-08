package client.gui;

import javax.swing.JOptionPane;
import java.awt.Component;

public class MessageProvider implements IMessageProvider{
  @Override
  public int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType) {
    return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType, messageType);
  }

}

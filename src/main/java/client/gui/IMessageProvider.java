package client.gui;

import java.awt.Component;

public interface IMessageProvider {
  int showConfirmDialog(Component parentComponent, Object message, String title, int optionType, int messageType);
}

package client.gui;

import java.awt.Component;
import javax.swing.Icon;

public class TestMessageProvider implements IMessageProvider {

  @Override
  public int showConfirmDialog(Component parentComponent, Object message, String title,
      int optionType, int messageType) {
    throw new RuntimeException(getClass() + ": Test method not implemented!");
  }

  @Override
  public void showMessageDialog(Component parentComponent, Object message, String title,
      int messageType) {
    throw new RuntimeException(getClass() + ": Test method not implemented!");
  }

  @Override
  public void showMessageDialog(Component parentComponent, Object message) {
    throw new RuntimeException(getClass() + ": Test method not implemented!");
  }

  @Override
  public int showOptionDialog(Component parentComponent, Object message, String title,
      int optionType, int messageType, Icon icon, Object[] options, Object initialValue) {
    throw new RuntimeException(getClass() + ": Test method not implemented!");
  }
}

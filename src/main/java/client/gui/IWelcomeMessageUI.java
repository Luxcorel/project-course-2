package client.gui;

import java.awt.Dialog;

/**
 * This interface is responsible for displaying a welcome message to the user.
 * @author Samuel Carlsson
 * @version 1.0
 */
public interface IWelcomeMessageUI {
  void showWelcomeMessage();
  void setDialogVisible(Dialog dialog, boolean visible);
}

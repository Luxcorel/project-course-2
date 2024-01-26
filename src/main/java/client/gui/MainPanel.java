package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import server.Activity;

public class MainPanel extends JPanel {

  private MainFrame mainFrame;
  private AppPanel appPanel;
  private String userName;
  private Color backGroundColor;

  public MainPanel(MainFrame mainFrame, String userName) {
    this.mainFrame = mainFrame;
    this.userName = userName;
    backGroundColor = new Color(134, 144, 154, 145); //64, 87, 139
    setupPanel();
    appPanel = new AppPanel(this);
    showAppPanel();
  }

  public void setupPanel() {
    setSize(new Dimension(819, 438));
    setBackground(backGroundColor);
    setBorder(BorderFactory.createTitledBorder("Välkommen, " + userName));
  }

  public void showAppPanel() {
    add(appPanel);
  }

  public AppPanel getAppPanel() {
    return appPanel;
  }

  public void logOut() {
    mainFrame.logOut();
  }

  public void sendActivityFromGUI(Activity activity) {
    mainFrame.sendActivityFromGUI(activity);
  }

  public void sendChosenInterval(int interval) {
    mainFrame.sendChosenInterval(interval);
  }
}

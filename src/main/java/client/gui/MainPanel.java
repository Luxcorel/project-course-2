package client.gui;

import client.ClientController;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

  private MainFrame mainFrame;
  private AppPanel appPanel;
  private Color backGroundColor;

  public MainPanel(MainFrame mainFrame, ClientController clientController) {
    this.mainFrame = mainFrame;
    backGroundColor = new Color(134, 144, 154, 145); //64, 87, 139
    setSize(new Dimension(819, 438));
    setBackground(backGroundColor);
    setBorder(BorderFactory.createTitledBorder("Välkommen!"));
    appPanel = new AppPanel(this, clientController);
    add(appPanel);
  }

  public void logOut() {
    mainFrame.logOut();
  }
}

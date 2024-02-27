package client.gui;

import client.ClientController;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
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

  public void info() {
    String info = """
        Edim skickar notiser till dig med jämna mellanrum, notisen innehåller en fysisk aktivitet som ska utföras för att hålla igång blodcirkulationen i din kropp.
        Hur ofta du vill ha dessa notiser kan du ställa in själv.
        """;

    String infoMessage =
        "<html>" +
        "<body style='width: 300px'>" +
        info +
        "</body>" +
        "</html>";
    JOptionPane.showMessageDialog(null, infoMessage, "What is Edim?", JOptionPane.INFORMATION_MESSAGE);
  }
}

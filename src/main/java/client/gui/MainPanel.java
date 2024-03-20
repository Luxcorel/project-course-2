package client.gui;

import client.ActivityTimer;
import client.ClientController;
import client.external.InspirationalQuotes;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import java.util.Timer;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
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
    new Thread(() -> {
      float hue = 0.0f;
      float saturation = 1.0f;
      float brightness = 1.0f;
      float deltaHue = 0.001f;
      try {
        Thread.sleep(25);
      } catch (InterruptedException ignored) {}
      while (true) {
        try {
          Thread.sleep(25);
        } catch (InterruptedException ignored) {}

        hue = (hue + deltaHue) % 1.0f;
        Color color = Color.getHSBColor(hue, saturation, brightness);
        setBackground(color);
      }
    }).start();
    setBorder(BorderFactory.createTitledBorder("Welcome!"));
    appPanel = new AppPanel(
        this,
        clientController,
        new WelcomeMessageUI(new Timer(), new InspirationalQuotes(), new JDialog()),
        new MessageProvider(),
        new ActivityTimer(),
        new SoundPlayer("src/main/resources/NotisSound.wav"));
    add(appPanel);
  }

  public void logOut() {
    mainFrame.logOut();
  }

  /**
   * Displays an informational dialog about Edim.
   */
  public void info() {
    String info = """
        Edim will send you notifications at regular intervals, each notification contains a physical activity designed to promote healthy blood circulation in your body. 
        The frequency of these alerts can be customized according to your preference.
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

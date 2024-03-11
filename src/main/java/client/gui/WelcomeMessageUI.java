package client.gui;

import client.external.InspirationalQuotes;
import com.google.gson.JsonObject;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Dialog;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeMessageUI implements IWelcomeMessageUI{
  @Override
  public void showWelcomeMessage() {
    ImageIcon welcomeIcon = new ImageIcon("imagesClient/exercise.png");
    Image image = welcomeIcon.getImage();
    Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

    InspirationalQuotes quotes = new InspirationalQuotes();
    JsonObject quoteJson = quotes.getRandomQuote();
    if (quoteJson == null) {
      return;
    }

    String quote = quoteJson.get("q").getAsString();
    String author = quoteJson.get("a").getAsString();

    JLabel label = new JLabel(String.format("%s\n- %s", quote, author));
    label.setIcon(new ImageIcon(newImg));
    label.setHorizontalAlignment(SwingConstants.CENTER);

    JDialog dialog = new JDialog();
    dialog.setTitle("Welcome to EDIM!");
    dialog.setModal(true);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.add(label);
    dialog.pack();
    dialog.setLocationRelativeTo(null);

    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        dialog.dispose();
        timer.cancel();
      }
    }, 5000);

    setDialogVisible(dialog, true);
  }

  @Override
  public void setDialogVisible(Dialog dialog, boolean visible) {
    dialog.setVisible(visible);
  }
}

package client.gui;

import client.external.InspirationalQuotes;
import com.google.gson.JsonObject;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Dialog;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is responsible for displaying a welcome message to the user.
 * @author Samuel Carlsson
 * @version 1.0
 */
public class WelcomeMessageUI implements IWelcomeMessageUI{
  private final Timer timer;
  private final InspirationalQuotes quotes;
  private final Dialog dialog;

  public WelcomeMessageUI(Timer timer, InspirationalQuotes quotes, Dialog dialog) {
    this.timer = timer;
    this.quotes = quotes;
    this.dialog = dialog;
  }

  /**
   * Displays a welcome message that contains a random quote.
   * The message is displayed for 5 seconds.
   */
  @Override
  public void showWelcomeMessage() {
    ImageIcon welcomeIcon = new ImageIcon("imagesClient/exercise.png");
    Image image = welcomeIcon.getImage();
    Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

    JsonObject quoteJson = quotes.getRandomQuote();
    if (quoteJson == null) {
      return;
    }

    String quote = quoteJson.get("q").getAsString();
    String author = quoteJson.get("a").getAsString();

    JLabel label = new JLabel(String.format("%s\n- %s", quote, author));
    label.setIcon(new ImageIcon(newImg));
    label.setHorizontalAlignment(SwingConstants.CENTER);


    dialog.setTitle("Welcome to EDIM!");
    dialog.setModal(true);
    dialog.add(label);
    dialog.pack();
    dialog.setLocationRelativeTo(null);

    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        dialog.dispose();
        timer.cancel();
      }
    }, 5000);

    setDialogVisible(dialog, true);
  }

  /**
   * Sets the visibility of the dialog.
   * @param dialog The dialog to set visibility for.
   * @param visible True if the dialog should be visible, false otherwise.
   */
  @Override
  public void setDialogVisible(Dialog dialog, boolean visible) {
    dialog.setVisible(visible);
  }
}

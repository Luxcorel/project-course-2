package client.gui;

import client.Activity;
import client.ActivityListItem;
import client.ClientController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

/**
 * This is the panel in the frame that contains pretty much all of the components in the GUI.
 *
 * @author Oscar Kareld, Chanon Borgstrom, Carolin Nordstrom, Edvin Topalovic.
 * @author Johannes Rosengren
 * @version 1.1
 */
public class AppPanel extends JPanel {

  private final MainPanel mainPanel;
  private final ClientController clientController;

  // west or left panel and its components
  private JPanel west;
  private JLabel chosenTimerInterval;
  private JSpinner timerIntervalSelector;
  private JButton startTimer;
  private JLabel timeLeft;

  // center "panel" and its components
  private JList<ActivityListItem> activityHistory;
  private DefaultListModel<ActivityListItem> activities;

  // right panel that shows activity info for the selected activity
  private JTextArea activityInfoPanel;

  // bottom panel containing the logout button
  private JButton logOut;

  private final Color clrPanels = new Color(142, 166, 192);

  // timer thread keeping track of the time left until the next activity notification should appear
  private Timer timer;
  private int timeLeftInSeconds; // time left in seconds until the next activity notification

  public AppPanel(MainPanel mainPanel, ClientController clientController) {
    this.mainPanel = mainPanel;
    this.clientController = clientController;

    setSize(new Dimension(819, 438));
    BorderLayout borderLayout = new BorderLayout();
    setLayout(borderLayout);

    createComponents();

    showWelcomeMessage();
  }

  public void createComponents() {
    createActivityList();
    createTAActivityInfo();
    createCBTimeLimit();
    createIntervalPanel();

    logOut = new JButton("Avsluta");

    add(activityHistory, BorderLayout.CENTER);
    add(logOut, BorderLayout.SOUTH);
    add(activityInfoPanel, BorderLayout.EAST);
    add(west, BorderLayout.WEST);

    logOut.addActionListener((event) -> mainPanel.logOut());

    activityHistory.addListSelectionListener(event -> {
      ActivityListItem selectedActivity = activityHistory.getSelectedValue();
      showActivityInfo(selectedActivity.completedActivity().getActivityInfo());
    });
  }

  public void createIntervalPanel() {
    west = new JPanel();
    west.setLayout(new BorderLayout());
    west.setBackground(clrPanels);
    west.setBorder(
        BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.LIGHT_GRAY));

    chosenTimerInterval = new JLabel();
    timeLeft = new JLabel();
    JPanel centerPnl = new JPanel();
    centerPnl.setSize(new Dimension(west.getWidth(), west.getHeight()));
    centerPnl.setBackground(clrPanels);
    updateLblInterval();
    centerPnl.add(timerIntervalSelector);

    startTimer = new JButton("Starta timer");
    startTimer.addActionListener((event) -> {
      startTimer.setText("Ändra intervall");

      int intervalToUse = (int) timerIntervalSelector.getValue(); // TODO: store this somewhere
      updateLblInterval();

      startTimer(intervalToUse);
    });
    centerPnl.add(startTimer, BorderLayout.SOUTH);

    west.add(chosenTimerInterval, BorderLayout.NORTH);
    west.add(centerPnl, BorderLayout.CENTER);
    west.add(timeLeft, BorderLayout.SOUTH);
  }

  public void updateLblInterval() {
    int interval = (int) timerIntervalSelector.getValue();
    chosenTimerInterval.setText("Aktivt tidsintervall: " + interval + " minuter");
  }

  public void createCBTimeLimit() {
    SpinnerModel spnrModel = new SpinnerNumberModel(1, 1, 60, 1);
    timerIntervalSelector = new JSpinner(spnrModel);
  }

  public void startTimer(int minutes) {
    if (timer != null) {
      timer.cancel();
    }

    timeLeftInSeconds = (minutes * 60);

    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        int minutes = timeLeftInSeconds / 60;
        int seconds = timeLeftInSeconds % 60;

        if (timeLeftInSeconds == 0) {
          String time = String.format("timer: %d:%02d", minutes, seconds);
          timeLeft.setText(time);
          SwingUtilities.invokeLater(
              () -> showNotification(clientController.getActivityManager().getActivity()));
          timer.cancel();
        }

        timeLeftInSeconds--;

        String time = String.format("timer: %d:%02d", minutes, seconds);
        timeLeft.setText(time);
      }
    }, 1000, 1000);
  }

  public void createTAActivityInfo() {
    activityInfoPanel = new JTextArea();
    activityInfoPanel.setBackground(clrPanels);
    activityInfoPanel.setPreferredSize(new Dimension(200, 80));
    activityInfoPanel.setLineWrap(true);
    activityInfoPanel.setWrapStyleWord(true);
    Font font = new Font("SansSerif", Font.PLAIN, 14); //Sarseriff
    activityInfoPanel.setFont(font);
    activityInfoPanel.setEditable(false);
  }

  public void createActivityList() {
    activities = new DefaultListModel<>();
    activityHistory = new JList<>(activities);
    activityHistory.setPreferredSize(new Dimension(400, 320));
    activityHistory.setBorder(BorderFactory.createTitledBorder("Avklarade aktiviteter"));
    activityHistory.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    Font font = new Font("SansSerif", Font.PLAIN, 14);
    activityHistory.setFont(font);
  }

  public void addToActivityHistory(Activity activity) {
    activities.addElement(new ActivityListItem(activity, LocalDateTime.now()));
  }

  public void showActivityInfo(String activityInfo) {
    activityInfoPanel.setText(activityInfo);
  }

  public ImageIcon createActivityIcon(Activity activity) {
    ImageIcon activityIcon = activity.getActivityImage();
    Image image = activityIcon.getImage();
    Image newImg = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    return new ImageIcon(newImg);
  }

  public void showNotification(Activity activity) {
    Toolkit.getDefaultToolkit().beep();
    ImageIcon activityIcon = createActivityIcon(activity);
    String[] buttons = {"Jag har gjort aktiviteten!", "Påminn mig om fem minuter",};
    String instruction = activity.getActivityInstruction();
    String[] instructions = new String[3];

    if (instruction.contains("&")) {
      instructions = instruction.split("&");
    }

    int answer = WelcomePane.showOptionDialog(null, instructions, activity.getActivityName(),
        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, activityIcon, buttons, null);

    switch (answer) {
      case JOptionPane.NO_OPTION -> {
        clientController.getActivityManager().enqueueActivity(activity);
        startTimer(5);
      }
      case JOptionPane.YES_OPTION -> {
        activity.setCompleted(true);
        addToActivityHistory(activity);

        int intervalToUse = (int) timerIntervalSelector.getValue(); // TODO: store this somewhere
        startTimer(intervalToUse);
      }
    }

  }

  public void showWelcomeMessage() {
    ImageIcon welcomeIcon = new ImageIcon("imagesClient/exercise.png");
    Image image = welcomeIcon.getImage();
    Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

    JOptionPane.showMessageDialog(null, """
            Välkommen!
            EDIM kommer skicka notiser till dig med jämna mellanrum,
            med en fysisk aktivitet som ska utföras.
            Hur ofta du vill ha dessa notiser kan du ställa in själv.
            """,
        "Välkommen till EDIM!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(newImg));
  }

  private static class WelcomePane extends JOptionPane {

    @Override
    public int getMaxCharactersPerLineCount() {
      return 10;
    }
  }

}

package client.gui;

import client.Activity;
import client.ClientController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * @version 1.1
 */
public class AppPanel extends JPanel {

  private MainPanel mainPanel;
  private ClientController clientController;

  private JLabel lblTimerInfo;
  private JTextArea taActivityInfo;
  //private JComboBox cmbTimeLimit;
  private JSpinner spnrTimeSelect;
  private LinkedList<Activity> activities;
  private JList activityList;

  private JButton btnLogOut;
  private JButton btnStartTimer;
  private JPanel intervalPnl;
  private JLabel lblInterval;

  private BorderLayout borderLayout = new BorderLayout();
  private DefaultListModel<String> listModel;

  private Color clrPanels = new Color(142, 166, 192);
  private Color clrMidPanel = new Color(127, 140, 151, 151);

  private Timer timer;
  private int timerInterval;

  public AppPanel(MainPanel mainPanel, ClientController clientController) {
    this.mainPanel = mainPanel;
    this.clientController = clientController;

    setupPanel();
    createComponents();
    activities = new LinkedList<>();
    showWelcomeMessage();
  }

  public void setupPanel() {
    setSize(new Dimension(819, 438));
  }

  public void createComponents() {
    setLayout(borderLayout);

    createActivityList();
    createTAActivityInfo();
    createCBTimeLimit();
    createIntervalPanel();

    btnLogOut = new JButton("Avsluta");

    add(activityList, BorderLayout.CENTER);
    add(btnLogOut, BorderLayout.SOUTH);
    add(taActivityInfo, BorderLayout.EAST);
    add(intervalPnl, BorderLayout.WEST);

    btnLogOut.addActionListener((event) -> mainPanel.logOut());

    addActivityListener();
  }

  public void createIntervalPanel() {
    intervalPnl = new JPanel();
    intervalPnl.setLayout(new BorderLayout());
    intervalPnl.setBackground(clrPanels);
    intervalPnl.setBorder(
        BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.LIGHT_GRAY));

    lblInterval = new JLabel();
    lblTimerInfo = new JLabel();
    JPanel centerPnl = new JPanel();
    centerPnl.setSize(new Dimension(intervalPnl.getWidth(), intervalPnl.getHeight()));
    centerPnl.setBackground(clrPanels);
    updateLblInterval();
    centerPnl.add(spnrTimeSelect);

    btnStartTimer = new JButton("Starta timer");
    btnStartTimer.addActionListener((event) -> {
      btnStartTimer.setText("Ändra intervall");

      int intervalToUse = (int) spnrTimeSelect.getValue(); // TODO: store this somewhere
      updateLblInterval();

      startTimer(intervalToUse);
    });
    centerPnl.add(btnStartTimer, BorderLayout.SOUTH);

    intervalPnl.add(lblInterval, BorderLayout.NORTH);
    intervalPnl.add(centerPnl, BorderLayout.CENTER);
    intervalPnl.add(lblTimerInfo, BorderLayout.SOUTH);
  }

  public void updateLblInterval() {
    int interval = (int) spnrTimeSelect.getValue();
    lblInterval.setText("Aktivt tidsintervall: " + interval + " minuter");
  }

  public void createCBTimeLimit() {
    SpinnerModel spnrModel = new SpinnerNumberModel(1, 1, 60, 1);
    spnrTimeSelect = new JSpinner(spnrModel);
  }

  public void startTimer(int minutes) {
    if (timer != null) {
      timer.cancel();
    }

    timerInterval = (minutes * 60);

    timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      public void run() {
        int minutes = timerInterval / 60;
        int seconds = timerInterval % 60;

        if (timerInterval == 0) {
          String time = String.format("timer: %d:%02d", minutes, seconds);
          lblTimerInfo.setText(time);
          SwingUtilities.invokeLater(
              () -> showNotification(clientController.getActivityManager().getActivity()));
          timer.cancel();
        }

        timerInterval--;

        String time = String.format("timer: %d:%02d", minutes, seconds);
        lblTimerInfo.setText(time);
      }
    }, 1000, 1000);
  }

  public void createTAActivityInfo() {
    taActivityInfo = new JTextArea();
    taActivityInfo.setBackground(clrPanels);
    taActivityInfo.setPreferredSize(new Dimension(200, 80));
    taActivityInfo.setLineWrap(true);
    taActivityInfo.setWrapStyleWord(true);
    Font font = new Font("SansSerif", Font.PLAIN, 14); //Sarseriff
    taActivityInfo.setFont(font);
    taActivityInfo.setEditable(false);
  }

  public void createActivityList() {
    listModel = new DefaultListModel<>();
    activityList = new JList<>(listModel);
    activityList.setPreferredSize(new Dimension(400, 320));
    activityList.setBorder(BorderFactory.createTitledBorder("Avklarade aktiviteter"));
    activityList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    Font font = new Font("SansSerif", Font.PLAIN, 14);
    activityList.setFont(font);
  }

  public void addActivityListener() {
    activityList.addListSelectionListener(event -> {
      String activityName = (String) activityList.getSelectedValue();
      String newActivityName = splitActivityNameAndTime(activityName);
      for (Activity activity : activities) {
        if (activity.getActivityName().equals(newActivityName)) {
          showActivityInfo(activity.getActivityInfo());
        }
      }
    });
  }

  public String splitActivityNameAndTime(String activityName) {
    activityName = activityName.replaceAll("[0-9]", "");
    activityName = activityName.replaceAll(":", "");
    activityName = activityName.replaceAll(" ", "");
    return activityName;
  }

  public void updateActivityList(Activity activity) {
    activities.add(activity);
    listModel.addElement(activity.getActivityName() + " " + activity.getTime());
    String newActivityName = splitActivityNameAndTime(activity.getActivityName());
    activity.setActivityName(newActivityName);
    updateUI();
  }

  public void showActivityInfo(String activityInfo) {
    taActivityInfo.setText(activityInfo);
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
        updateActivityList(activity);

        int intervalToUse = (int) spnrTimeSelect.getValue(); // TODO: store this somewhere
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

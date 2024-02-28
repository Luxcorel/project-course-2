package client.gui;

import client.Activity;
import client.ActivityListItem;
import client.ClientController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

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

  // left panel and its components
  private JPanel west;
  private JSpinner timerIntervalSelector;
  private JButton startTimer;
  private JLabel timeLeft;
  private JButton addCustomActivity;

  // center panel and its components
  private JList<ActivityListItem> activityHistory;
  private DefaultListModel<ActivityListItem> activities;

  // right panel that shows activity info for the selected activity
  private JTextArea activityInfoPanel;

  private final Color clrPanels = new Color(142, 166, 192);

  // timer keeping track of the time left until the next activity notification should appear
  private Timer timer;
  private int timeLeftInSeconds; // seconds left until the next activity notification should appear
  private int chosenMinuteInterval; // this value is used whenever a new timer is started.

  public AppPanel(MainPanel mainPanel, ClientController clientController) {
    this.mainPanel = mainPanel;
    this.clientController = clientController;

    setSize(new Dimension(819, 438));
    BorderLayout borderLayout = new BorderLayout();
    setLayout(borderLayout);

    createComponents();

    showWelcomeMessage();
  }

  private void createComponents() {
    createActivityHistoryList();
    createActivityInfoPanel();
    createIntervalPanel();

    JButton logOut = new JButton("Avsluta");
    logOut.addActionListener((event) -> mainPanel.logOut());

    activityHistory.addListSelectionListener(event -> {
      ActivityListItem selectedActivity = activityHistory.getSelectedValue();
      showActivityInfo(selectedActivity.completedActivity().getActivityInfo());
    });

    add(activityHistory, BorderLayout.CENTER);
    add(logOut, BorderLayout.SOUTH);
    add(activityInfoPanel, BorderLayout.EAST);
    add(west, BorderLayout.WEST);
  }

  private void createIntervalPanel() {
    SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 60, 1);
    timerIntervalSelector = new JSpinner(spinnerModel);

    west = new JPanel();
    west.setLayout(new BorderLayout());
    west.setBackground(clrPanels);
    west.setBorder(
        BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, Color.LIGHT_GRAY));

    timeLeft = new JLabel();
    JPanel centerPnl = new JPanel();
    centerPnl.setSize(new Dimension(west.getWidth(), west.getHeight()));
    centerPnl.setBackground(clrPanels);
    centerPnl.add(timerIntervalSelector);

    startTimer = new JButton("Starta timer");
    startTimer.addActionListener((event) -> {
      startTimer.setText("Ändra intervall");

      int minutes = (int) timerIntervalSelector.getValue();
      setTimerInterval(minutes);

      startTimer(chosenMinuteInterval);
    });
    centerPnl.add(startTimer, BorderLayout.SOUTH);

    JPanel customActivityPanel = new JPanel();
    customActivityPanel.setBackground(clrPanels);

    addCustomActivity = new JButton("Lägg till ny övning");
    addCustomActivity.addActionListener(event -> {
      addCustomActivity.setEnabled(false);

      Optional<Activity> activityOptional = addCustomActivity();
      if (activityOptional.isPresent()) {
        Activity activity = activityOptional.get();
        JOptionPane.showMessageDialog(this, "Ny aktivitet tillagd: " + activity.getActivityName());
      }

      addCustomActivity.setEnabled(true);
    });
    customActivityPanel.add(addCustomActivity);

    west.add(customActivityPanel, BorderLayout.PAGE_START);
    west.add(centerPnl, BorderLayout.CENTER);
    west.add(timeLeft, BorderLayout.SOUTH);
  }

  /**
   * Sets the minute interval for the timer and sets the title of the frame to the new interval.
   * This value is used whenever the timer is restarted.
   *
   * @param minutes the timer interval in minutes
   */
  public void setTimerInterval(int minutes) {
    this.chosenMinuteInterval = minutes;

    clientController.setTitle(
        chosenMinuteInterval == 1 ?
            "EDIM | Aktivt tidsintervall: " + chosenMinuteInterval + " minut"
            : "EDIM | Aktivt tidsintervall: " + chosenMinuteInterval + " minuter"
    );
  }

  /**
   * Opens a new window with a form for the user to add a new activity.
   *
   * @return an Optional containing the new activity if the user added one, or an empty Optional if
   * the user canceled the operation or if the input was invalid.
   * @author Johannes Rosengren
   * @implNote Requirements: F011, F33
   */
  public Optional<Activity> addCustomActivity() {
    JLabel nameLabel = new JLabel("Name:");
    JTextField nameInput = new JTextField(1);

    JLabel instructionLabel = new JLabel("Instructions:");
    JTextArea instructionInput = new JTextArea(5,20);
    instructionInput.setLineWrap(true);
    JScrollPane instructionInputScrollPane = new JScrollPane(instructionInput);

    JLabel infoLabel = new JLabel("Information:");
    JTextArea infoInput = new JTextArea(5, 20);
    infoInput.setLineWrap(true);
    JScrollPane infoInputScrollPane = new JScrollPane(infoInput);

    JLabel imagePathLabel = new JLabel("Image (optional):");
    JTextField imagePathInput = new JTextField();
    JLabel chosenImage = new JLabel();
    chosenImage.setPreferredSize(new Dimension(200, 200));
    chosenImage.setText("No image selected");
    chosenImage.setHorizontalAlignment(SwingConstants.CENTER);
    chosenImage.setVerticalAlignment(SwingConstants.CENTER);

    JButton imageBrowser = new JButton("Choose image");
    imageBrowser.addActionListener(event -> {
      JFileChooser fileChooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Image file", "jpg", "jpeg", "png");
      fileChooser.setFileFilter(filter);

      int option = fileChooser.showOpenDialog(this);
      if (option == JFileChooser.APPROVE_OPTION) {
        String chosenFile = fileChooser.getSelectedFile().getAbsolutePath();
        imagePathInput.setText(chosenFile);

        ImageIcon imageIcon = new ImageIcon(chosenFile);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        chosenImage.setText("");
        chosenImage.setIcon(imageIcon);
      }
    });

    JPanel addCustomActivityPanel = new JPanel(new BorderLayout());
    addCustomActivityPanel.setPreferredSize(new Dimension(500, 500));

    JPanel labels = new JPanel(new GridLayout(4, 1, 5, 5));
    labels.add(nameLabel);
    labels.add(instructionLabel);
    labels.add(infoLabel);
    labels.add(imagePathLabel);
    addCustomActivityPanel.add(labels, BorderLayout.WEST);

    JPanel inputs = new JPanel(new GridLayout(4, 1, 5, 5));
    inputs.add(nameInput);
    inputs.add(instructionInputScrollPane);
    inputs.add(infoInputScrollPane);
    inputs.add(imageBrowser);
    addCustomActivityPanel.add(inputs, BorderLayout.EAST);

    JPanel imagePreviewPanel = new JPanel();
    imagePreviewPanel.add(chosenImage);
    addCustomActivityPanel.add(imagePreviewPanel, BorderLayout.SOUTH);

    int option = JOptionPane.showConfirmDialog(this, addCustomActivityPanel, "Add new activity",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (option != JOptionPane.OK_OPTION) {
      return Optional.empty();
    }

    String activityName = nameInput.getText();
    String activityInstruction = instructionInput.getText();
    String activityInfo = infoInput.getText();
    String imagePath = imagePathInput.getText();
    while (activityName.isBlank() || activityInstruction.isBlank() || activityInfo.isBlank()) {
      LineBorder error = new LineBorder(Color.RED);
      nameInput.setBorder(activityName.isBlank() ? error : null);
      instructionInput.setBorder(activityInstruction.isBlank() ? error : null);
      infoInput.setBorder(activityInfo.isBlank() ? error : null);

      JOptionPane.showMessageDialog(this, "All text-fields are required to add a new activity!",
          "Required information missing", JOptionPane.ERROR_MESSAGE);

      option = JOptionPane.showConfirmDialog(this, addCustomActivityPanel, "Add new activity",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
      if (option != JOptionPane.OK_OPTION) {
        return Optional.empty();
      }

      activityName = nameInput.getText();
      activityInstruction = instructionInput.getText();
      activityInfo = infoInput.getText();
      imagePath = imagePathInput.getText();
    }

    if (imagePath == null || imagePath.isBlank()) {
      return Optional.of(
          clientController.addActivity(activityName, activityInstruction, activityInfo)
      );
    }

    return Optional.of(
        clientController.addActivity(activityName, activityInstruction, activityInfo, imagePath)
    );
  }

  /**
   * Starts a timer with the given number of minutes.
   * If a timer is already running, it is canceled and a new one is started.
   * The timer will show a notification when it reaches 0.
   *
   * @param minutes the number of minutes to start the timer with
   * @author Johannes Rosengren
   */
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
              () -> {
                Optional<Activity> activity = clientController.getActivity();
                if (activity.isEmpty()) {
                  JOptionPane.showMessageDialog(AppPanel.this,
                      "Hittade inga sparade aktiviteter! Lägg till en aktivitet innan du startar timern.",
                      "Inga aktiviteter hittades", JOptionPane.ERROR_MESSAGE);
                  startTimer.setText("Starta timer");
                  return;
                }

                showNotification(activity.get());
              });
          timer.cancel();
        }

        timeLeftInSeconds--;

        String time = String.format("timer: %d:%02d", minutes, seconds);
        timeLeft.setText(time);
      }
    }, 0, 1000);
  }

  private void createActivityInfoPanel() {
    activityInfoPanel = new JTextArea();
    activityInfoPanel.setBackground(clrPanels);
    activityInfoPanel.setPreferredSize(new Dimension(200, 80));
    activityInfoPanel.setLineWrap(true);
    activityInfoPanel.setWrapStyleWord(true);
    Font font = new Font("SansSerif", Font.PLAIN, 14); //Sarseriff
    activityInfoPanel.setFont(font);
    activityInfoPanel.setEditable(false);
  }

  private void createActivityHistoryList() {
    activities = new DefaultListModel<>();
    activityHistory = new JList<>(activities);
    activityHistory.setPreferredSize(new Dimension(400, 320));
    activityHistory.setBorder(BorderFactory.createTitledBorder("Avklarade aktiviteter"));
    activityHistory.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    Font font = new Font("SansSerif", Font.PLAIN, 14);
    activityHistory.setFont(font);
  }

  /**
   * Adds an activity to the activity history list.
   * This is only stored in memory and not persisted though application restarts.
   *
   * @param activity the activity to add to the activity history list
   */
  public void addToActivityHistory(Activity activity) {
    activities.addElement(new ActivityListItem(activity, LocalDateTime.now()));
  }

  /**
   * Shows activity info in the activity info panel on the right side in the GUI.
   *
   * @param activityInfo the activity info to show in the activity info panel
   */
  public void showActivityInfo(String activityInfo) {
    activityInfoPanel.setText(activityInfo);
  }

  /**
   * Creates a scaled ImageIcon of the given activity.
   *
   * @param activity the activity to create a scaled icon for
   * @return a scaled ImageIcon of the activity
   * @throws IllegalArgumentException if the activity does not have an associated image
   */
  private ImageIcon createActivityIcon(Activity activity) throws IllegalArgumentException {
    if (activity.getActivityImage().isEmpty()) {
      throw new IllegalArgumentException();
    }

    ImageIcon activityIcon = activity.getActivityImage().get();
    Image image = activityIcon.getImage();
    Image newImg = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    return new ImageIcon(newImg);
  }

  /**
   * Shows a notification with the given activity.
   *
   * @param activity the activity to show a notification for
   * @implNote Requirements: F010a
   */
  public void showNotification(Activity activity) {
    Toolkit.getDefaultToolkit().beep();
    ImageIcon activityIcon = activity.getActivityImage().isPresent() ? createActivityIcon(activity) : null;
    String[] buttons = {"Jag har gjort aktiviteten!", "Påminn mig om fem minuter",};
    String instruction = activity.getActivityInstruction();

    // HTML formatted message for line breaks in JOptionPane
    String instructionMessage =
        "<html>" +
          "<body style='width: 300px'>" +
            instruction +
          "</body>" +
        "</html>";

    int option = JOptionPane.showOptionDialog(this, instructionMessage, activity.getActivityName(),
        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, activityIcon, buttons, null);

    switch (option) {
      case JOptionPane.NO_OPTION -> {
        clientController.enqueueActivity(activity);
        startTimer(5);
      }
      case JOptionPane.YES_OPTION -> {
        activity.setCompleted(true);
        addToActivityHistory(activity);

        startTimer(chosenMinuteInterval);
      }
    }

  }

  /**
   * TODO: add option to disable this through a settings file
   */
  public void showWelcomeMessage() {
    ImageIcon welcomeIcon = new ImageIcon("imagesClient/exercise.png");
    Image image = welcomeIcon.getImage();
    Image newImg = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

    JOptionPane.showMessageDialog(null, """
            <html>
              <body style='width: 300px'>
                <h1>Välkommen!</h1>
                <p>
                  EDIM kommer skicka notiser till dig med jämna mellanrum, med en fysisk aktivitet som ska utföras.
                  Hur ofta du vill ha dessa notiser kan du ställa in själv.
                </p>
              </body>
            </html>
            """,
        "Välkommen till EDIM!", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(newImg));
  }

}

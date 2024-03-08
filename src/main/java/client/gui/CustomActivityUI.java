package client.gui;

import client.Activity;
import client.ClientController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class is responsible for creating a new window for the user to add a new activity. The
 * window contains input fields for activity name, instructions, information and an image.
 *
 * @author Johannes Rosengren
 * @implNote Requirements: F011, F33
 */
public class CustomActivityUI {

  private final ClientController clientController;
  private final Component parentComponent;
  private MessageProvider messageProvider;

  private JPanel customActivityPanel;

  private JLabel nameLabel;
  private JTextField nameInput;

  private JLabel instructionLabel;
  private JTextArea instructionInput;
  private JScrollPane instructionInputScrollPane;

  private JLabel infoLabel;
  private JTextArea infoInput;
  private JScrollPane infoInputScrollPane;

  private JLabel imagePathLabel;
  private JTextField imagePathInput;
  private JLabel chosenImagePreview;
  private JButton imageBrowser;

  /**
   * Creates a new window for the user to add a new activity.
   *
   * @param parentComponent  the parent component of the new window.
   * @param clientController the client controller to use for adding the new activity.
   */
  public CustomActivityUI(Component parentComponent, ClientController clientController, MessageProvider messageProvider) {
    this.clientController = clientController;
    this.parentComponent = parentComponent;
    this.messageProvider = messageProvider;

    initializeComponents();
    setupUI();
  }

  /**
   * Creates a new window for the user to add a new activity.
   *
   * @param clientController the client controller to use for adding the new activity.
   */
  public CustomActivityUI(ClientController clientController) {
    this.clientController = clientController;
    this.parentComponent = null;

    initializeComponents();
    setupUI();
  }

  /**
   * Initializes the components of the new window.
   *
   * @author Johannes Rosengren
   */
  private void initializeComponents() {
    nameLabel = new JLabel("Name:");
    nameInput = new JTextField(1);

    instructionLabel = new JLabel("Instructions:");
    instructionInput = new JTextArea(5, 20);
    instructionInput.setLineWrap(true);
    instructionInputScrollPane = new JScrollPane(instructionInput);

    infoLabel = new JLabel("Information:");
    infoInput = new JTextArea(5, 20);
    infoInput.setLineWrap(true);
    infoInputScrollPane = new JScrollPane(infoInput);

    imagePathLabel = new JLabel("Image (optional):");
    imagePathInput = new JTextField();

    chosenImagePreview = new JLabel();
    chosenImagePreview.setPreferredSize(new Dimension(200, 200));
    chosenImagePreview.setText("No Image Selected");
    chosenImagePreview.setHorizontalAlignment(SwingConstants.CENTER);
    chosenImagePreview.setVerticalAlignment(SwingConstants.CENTER);

    imageBrowser = new JButton("Choose Image");
    imageBrowser.addActionListener(event -> chooseImage());
  }

  /**
   * Sets up the UI for the new window.
   *
   * @author Johannes Rosengren
   */
  private void setupUI() {
    customActivityPanel = new JPanel(new BorderLayout());
    customActivityPanel.setPreferredSize(new Dimension(500, 500));

    JPanel labels = new JPanel(new GridLayout(4, 1, 5, 5));
    labels.add(nameLabel);
    labels.add(instructionLabel);
    labels.add(infoLabel);
    labels.add(imagePathLabel);
    customActivityPanel.add(labels, BorderLayout.WEST);

    JPanel inputs = new JPanel(new GridLayout(4, 1, 5, 5));
    inputs.add(nameInput);
    inputs.add(instructionInputScrollPane);
    inputs.add(infoInputScrollPane);
    inputs.add(imageBrowser);
    customActivityPanel.add(inputs, BorderLayout.EAST);

    JPanel imagePreviewPanel = new JPanel();
    imagePreviewPanel.add(chosenImagePreview);
    customActivityPanel.add(imagePreviewPanel, BorderLayout.SOUTH);
  }

  /**
   * Opens a new window with a form for the user to add a new activity. Adds the new activity using
   * the client controller.
   *
   * @return an Optional containing the new activity if the user submitted the form with all
   * required values, or an empty Optional if the user canceled the operation or if the input was
   * invalid.
   * @author Johannes Rosengren
   * @implNote Requirements: F011, F33
   */
  public Optional<Activity> addCustomActivity() {
    int option = messageProvider.showConfirmDialog(parentComponent, customActivityPanel,
        "Add New Activity",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (option != JOptionPane.OK_OPTION) {
      return Optional.empty();
    }

    String activityName = nameInput.getText();
    String activityInstruction = instructionInput.getText();
    String activityInfo = infoInput.getText();
    String imagePath = imagePathInput.getText();

    while (!isValidInput(activityName, activityInstruction, activityInfo)) {
      LineBorder error = new LineBorder(Color.RED);
      nameInput.setBorder(nameInput.getText().isBlank() ? error : null);
      instructionInput.setBorder(instructionInput.getText().isBlank() ? error : null);
      infoInput.setBorder(infoInput.getText().isBlank() ? error : null);

      JOptionPane.showMessageDialog(parentComponent,
          "All text fields are required to add a new activity!", "Missing Required Information",
          JOptionPane.ERROR_MESSAGE);

      option = JOptionPane.showConfirmDialog(parentComponent, customActivityPanel,
          "Add new activity",
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
          clientController.addActivity(activityName, activityInstruction, activityInfo));
    }

    return Optional.of(
        clientController.addActivity(activityName, activityInstruction, activityInfo, imagePath));
  }

  /**
   * Opens a new window with a form for the user to create a new activity. Returns the new activity
   * when the user submits the form.
   *
   * @return an Optional containing the new activity if the user submitted the form with all
   * required values, or an empty Optional if the user canceled.
   * @author Johannes Rosengren
   * @implNote Requirements: F011, F33
   */
  public Optional<Activity> getCustomActivity() {
    int option = JOptionPane.showConfirmDialog(parentComponent, customActivityPanel,
        "Add new activity",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (option != JOptionPane.OK_OPTION) {
      return Optional.empty();
    }

    String activityName = nameInput.getText();
    String activityInstruction = instructionInput.getText();
    String activityInfo = infoInput.getText();
    String imagePath = imagePathInput.getText();

    while (!isValidInput(activityName, activityInstruction, activityInfo)) {
      LineBorder error = new LineBorder(Color.RED);
      nameInput.setBorder(nameInput.getText().isBlank() ? error : null);
      instructionInput.setBorder(instructionInput.getText().isBlank() ? error : null);
      infoInput.setBorder(infoInput.getText().isBlank() ? error : null);

      JOptionPane.showMessageDialog(parentComponent,
          "All text fields are required to add a new activity!", "Required information missing",
          JOptionPane.ERROR_MESSAGE);

      option = JOptionPane.showConfirmDialog(parentComponent, customActivityPanel,
          "Add new activity",
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
          clientController.packageActivity(activityName, activityInstruction, activityInfo));
    }

    return Optional.of(
        clientController.packageActivity(activityName, activityInstruction, activityInfo,
            imagePath));
  }

  /**
   * Opens a file chooser for the user to select an image file.
   *
   * @author Johannes Rosengren
   */
  private void chooseImage() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image file", "jpg", "jpeg",
        "png");
    fileChooser.setFileFilter(filter);

    int option = fileChooser.showOpenDialog(parentComponent);
    if (option == JFileChooser.APPROVE_OPTION) {
      String chosenFile = fileChooser.getSelectedFile().getAbsolutePath();

      setImage(chosenFile);
    } else if (option == JFileChooser.CANCEL_OPTION) {
      resetImage();
    }
  }

  /**
   * @param activityName        the name of the activity
   * @param activityInstruction the instructions for the activity
   * @param activityInfo        additional information about the activity
   * @return true if all inputs are valid, false otherwise
   * @author Johannes Rosengren
   */
  private boolean isValidInput(String activityName, String activityInstruction,
      String activityInfo) {
    return !activityName.isBlank() && !activityInstruction.isBlank() && !activityInfo.isBlank();
  }

  /**
   * @return the name of the activity entered by the user
   */
  public String getActivityName() {
    return nameInput.getText();
  }

  /**
   * @return the instructions for the activity entered by the user
   */
  public String getActivityInstruction() {
    return instructionInput.getText();
  }

  /**
   * @return the additional information about the activity entered by the user
   */
  public String getActivityInfo() {
    return infoInput.getText();
  }

  /**
   * @return the path to the image file entered by the user
   */
  public String getImagePath() {
    return imagePathInput.getText();
  }

  /**
   * @param name the name of the activity to set
   */
  public void setActivityName(String name) {
    nameInput.setText(name);
  }

  /**
   * @param instruction the instructions for the activity to set
   */
  public void setActivityInstruction(String instruction) {
    instructionInput.setText(instruction);
  }

  /**
   * @param info the information about the activity to set
   */
  public void setActivityInfo(String info) {
    infoInput.setText(info);
  }

  /**
   * Sets the image of the activity to the image at the given path. Also updates the image preview.
   *
   * @param imagePath the path to the image file to set
   */
  public void setImage(String imagePath) {
    imagePathInput.setText(imagePath);

    ImageIcon imageIcon = new ImageIcon(imagePath);
    Image image = imageIcon.getImage();
    Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
    imageIcon = new ImageIcon(scaledImage);

    chosenImagePreview.setText("");
    chosenImagePreview.setIcon(imageIcon);
  }

  /**
   * Removes the image from the activity and resets the image preview.
   */
  public void resetImage() {
    chosenImagePreview.setText("No image selected");
    chosenImagePreview.setIcon(null);
    imagePathInput.setText("");
  }

}

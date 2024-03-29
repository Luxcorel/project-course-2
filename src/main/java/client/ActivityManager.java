package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

/**
 * This class creates a register that handles Activity objects.
 *
 * @author Carolin Nordstrom, Oscar Kareld, Chanon Borgstrom, Sofia Hallberg, Edvin Topalovic.
 * @author Johannes Rosengren
 */
public class ActivityManager {
  private final String activitiesFilePath;
  private final ArrayList<Activity> activityRegister;
  private final Queue<Activity> postponedActivities;

  /**
   * The specified file path will be used to read and write activities to and from a file.
   *
   * @param activitiesFilePath The activity save-file to use.
   */
  public ActivityManager(String activitiesFilePath) {
    this.activitiesFilePath = activitiesFilePath;

    this.activityRegister = readActivitiesFromDisc(activitiesFilePath);
    postponedActivities = new LinkedList<>();
  }

  /**
   * Checks if a file exists.
   *
   * @param filePath The file path to check.
   * @return true if the file exists, otherwise false.
   * @author Johannes Rosengren
   */
  public boolean fileExists(String filePath) {
    return new File(filePath).exists();
  }

  /**
   * Creates an empty file at the specified file path if no file exists there already.
   *
   * @param filePath Where to create the file.
   * @throws IOException if the file could not be created.
   * @author Johannes Rosengren
   */
  public void createFile(String filePath) throws IOException {
    File file = new File(filePath);
    if (!file.exists()) {
      file.createNewFile();
    }
  }

  /**
   * Reads an activity save-file and returns an ArrayList of Activity objects.
   * If the file does not exist or is empty, an empty ArrayList is returned.
   *
   * @param filePath The file to read from
   * @return An ArrayList of {@link Activity} objects
   * @throws RuntimeException if the file could not be read
   * @author Johannes Rosengren
   * @implNote Requirements: F26
   */
  public ArrayList<Activity> readActivitiesFromDisc(String filePath) {
    if (!fileExists(filePath)) {
      return new ArrayList<>();
    }

    File activitiesFile = new File(filePath);
    // If an Activity save-file exists, read it and return it as an Activity Arraylist
    try (FileReader reader = new FileReader(activitiesFile, StandardCharsets.UTF_8)) {
      Gson gson = new Gson();
      ArrayList<Activity> activities = gson.fromJson(reader, new TypeToken<ArrayList<Activity>>() {
      }.getType());
      if (activities == null) {
        return new ArrayList<>();
      }

      // Java Swing ImageIcons can't be serialized,
      // so we have to reinitialize all the ImageIcons manually
      for (Activity activity : activities) {
        activity.setActivityImage(activity.getImagePath());
      }

      return activities;
    } catch (IOException e) {
      throw new RuntimeException(
          "ActivityManager.readActivitiesFromDisc(): Could not read activity file");
    }
  }

  /**
   * Reads the activity save-file and returns an ArrayList of Activity objects.
   * If the file does not exist or is empty, an empty ArrayList is returned.
   *
   * @return An ArrayList of {@link Activity} objects
   * @throws RuntimeException if the file could not be read
   * @author Johannes Rosengren
   * @implNote Requirements: F26
   */
  public ArrayList<Activity> readActivitiesFromDisc() {
    return readActivitiesFromDisc(activitiesFilePath);
  }

  /**
   * Method returns random {@link Activity}-object from {@link #activityRegister} Randomization is
   * achieved by shuffling the list.
   * If the activity list is empty, an empty Optional is returned.
   *
   * @return {@link Activity}-object
   * @author Edvin Topalovic
   * @author Johannes Rosengren
   */
  public Optional<Activity> getActivity() {
    if (activityRegister.isEmpty() && postponedActivities.isEmpty()) {
      return Optional.empty();
    }

    if (!postponedActivities.isEmpty()) {
      Activity activity = postponedActivities.remove();
      return Optional.of(activity);
    }

    Collections.shuffle(activityRegister);
    Activity activity = activityRegister.getFirst();
    return Optional.of(activity);
  }

  /**
   * Method returns {@link Activity}-object by ID from {@link #activityRegister}
   *
   * @param id ID of the activity
   * @return {@link Activity}-object
   * @author Edvin Topalovic
   */
  public Optional<Activity> getActivity(String id) {
    for (Activity activity : activityRegister) {
      if (activity.getActivityID().equals(id)) {
        return Optional.of(activity);
      }
    }

    return Optional.empty();
  }

  /**
   * Method allows user to add custom activities to {@link #activityRegister}
   *
   * @param activityName        Name of the activity
   * @param activityInstruction Instructions for the activity
   * @param activityInfo        Information about the activity
   * @return the added {@link Activity} object
   * @author Edvin Topalovic
   * @implNote Requirements: F011, F33
   */
  public Activity createActivity(String activityName, String activityInstruction,
      String activityInfo) {
    return createActivity(activityName, activityInstruction, activityInfo, null);
  }

  /**
   * Method allows user to add custom activities to {@link #activityRegister}
   *
   * @param activityName        Name of the activity
   * @param activityInstruction Instructions for the activity
   * @param activityInfo        Information about the activity
   * @param imagePath           Path to the image for the activity
   * @return the added {@link Activity} object
   * @throws RuntimeException if the activity could not be added to the activity register
   * @author Edvin Topalovic
   * @author Johannes Rosengren
   * @implNote Requirements: F011, F33
   */
  public Activity createActivity(String activityName, String activityInstruction,
      String activityInfo, String imagePath) {
    Activity activity = packageActivity(activityName, activityInstruction, activityInfo, imagePath);

    activityRegister.add(activity);
    saveActivitiesToDisc(activitiesFilePath);

    Optional<Activity> savedActivity = getActivity(activity.getActivityID());
    if (savedActivity.isEmpty()) {
      throw new RuntimeException(
          "ActivityManager.createActivity(): Could not add activity to activity register");
    }

    return savedActivity.get();
  }

  /**
   * Method packages activity attributes into an {@link Activity} object.
   *
   * @param activityName        Name of the activity
   * @param activityInstruction Instructions for the activity
   * @param activityInfo        Information about the activity
   * @param imagePath           Optionally path to the image for the activity
   * @return the packaged {@link Activity} object
   */
  public static Activity packageActivity(String activityName, String activityInstruction,
      String activityInfo, String imagePath) {
    Activity activity = new Activity();

    activity.setActivityID(UUID.randomUUID().toString());
    activity.setActivityName(activityName);
    activity.setActivityInstruction(activityInstruction);
    activity.setActivityInfo(activityInfo);
    activity.setActivityImage(imagePath);

    return activity;
  }

  /**
   * Method packages activity attributes into an {@link Activity} object.
   *
   * @param activityName        Name of the activity
   * @param activityInstruction Instructions for the activity
   * @param activityInfo        Information about the activity
   * @return the packaged {@link Activity} object
   */
  public static Activity packageActivity(String activityName, String activityInstruction,
      String activityInfo) {
    return packageActivity(activityName, activityInstruction, activityInfo, "");
  }

  /**
   * @param activity The activity to be postponed.
   * @implNote Requirements: F010b
   */
  public void enqueueActivity(Activity activity) {
    postponedActivities.add(activity);
  }

  public Queue<Activity> getPostponedActivities() {
    return postponedActivities;
  }

  /**
   * Saves activities to specified file.
   * If the file does not already exist, it will be created.
   *
   * @param filePath The file to save activities to
   * @throws RuntimeException if the file could not be created or written to
   * @author Edvin Topalovic
   * @author Johannes Rosengren
   * @implNote Requirements: F25
   */
  public void saveActivitiesToDisc(String filePath) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String encodedActivities = gson.toJson(activityRegister);

    if (!fileExists(filePath)) {
      try {
        createFile(filePath);
      } catch (IOException e) {
        throw new RuntimeException(
            "ActivityManager.saveActivitiesToDisc(): Could not create activity save-file");
      }
    }

    File activitiesFile = new File(filePath);
    try (BufferedWriter writer = new BufferedWriter(
        new FileWriter(activitiesFile, StandardCharsets.UTF_8))) {
      writer.write(encodedActivities);
    } catch (IOException e) {
      throw new RuntimeException(
          "ActivityManager.saveActivitiesToDisc(): Could not write to activity save-file");
    }
  }

  /**
   * Saves activities to the activity save-file.
   * If the file does not already exist, it will be created.
   *
   * @throws RuntimeException if the file could not be created or written to
   * @author Edvin Topalovic
   * @author Johannes Rosengren
   * @implNote Requirements: F25
   */
  public void saveActivitiesToDisc() {
    saveActivitiesToDisc(activitiesFilePath);
  }

  public ArrayList<Activity> getActivityRegister() {
    return activityRegister;
  }

  public String getActivitiesFilePath() {
    return activitiesFilePath;
  }

}

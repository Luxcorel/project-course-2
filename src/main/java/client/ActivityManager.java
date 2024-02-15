package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class creates a register that handles Activity objects.
 *
 * @author Carolin Nordstrom, Oscar Kareld, Chanon Borgstrom, Sofia Hallberg, Edvin Topalovic.
 * @version 1.1.2
 */

public class ActivityManager {

  private ArrayList<Activity> activityRegister;
  public ActivityManager(String file) {
    readRegister(file);
  }

  private void readRegister(String file) {
    activityRegister = new ArrayList<>();
    int nbrOfActivities;

    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
      String line = br.readLine();
      if (line != null) {
        nbrOfActivities = Integer.parseInt(line);
        for (int i = 0; i < nbrOfActivities; i++) {
          Activity activity = new Activity();

          // Read each attribute separately
          String idString = br.readLine();
          String name = br.readLine();
          String instruction = br.readLine();
          String info = br.readLine();
          String imageDescription = br.readLine();

          // Check for null values before parsing
          if (idString != null) {
            activity.setActivityID(Integer.parseInt(idString));
          }
          if (name != null) {
            activity.setActivityName(name);
          }
          if (instruction != null) {
            activity.setActivityInstruction(instruction);
          }
          if (info != null) {
            activity.setActivityInfo(info);
          }
          if (imageDescription != null) {
            activity.createActivityImage(imageDescription);
          }

          activityRegister.add(activity);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


//  public LinkedList<Activity> getActivityRegister() {
//    return activityRegister;
//  }

  /**
   * Method returns random {@link Activity}-object from {@link #activityRegister}
   * Randomization is achieved by shuffling the list.
   * @author Edvin Topalovic
   * @return {@link Activity}-object
   */
  public Activity getActivity() {
    Collections.shuffle(activityRegister);
    return activityRegister.get(0);
  }

  /**
   * Method returns {@link Activity}-object by ID from {@link #activityRegister}
   * @author Edvin Topalovic
   * @param id
   * @return {@link Activity}-object
   */
  public Activity getActivity(int id) {
    for (Activity activity: activityRegister) {
      if (activity.getActivityID() == id) { return activity; }
    }
    return null;
  }

  /**
   * Method allows user to add custom activities to {@link #activityRegister}
   * @author Edvin Topalovic
   * @param name, inst, info
   * @return {@link Activity}-object
   */
  public Activity createActivity(String name, String inst, String info) {
    Activity activity = new Activity();
    int newId = activityRegister.size() + 1;

    activity.setActivityID(newId);
    activity.setActivityName(name);
    activity.setActivityInstruction(inst);
    activity.setActivityInfo(info);
    //activity.createActivityImage();

    activityRegister.add(activity);

    return getActivity(newId);
  }

  /**
   * Saves activites to ./files/activities.txt
   * @author Edvin Topalovic
   */
  public void saveActivitiesToDisc(String file) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

      writer.write(String.valueOf(activityRegister.size()));  // amount of objects
      writer.newLine();

      for (Activity activity : activityRegister) {
        writer.write(activity.getActivityID() + "");
        writer.newLine();
        writer.write(activity.getActivityName());
        writer.newLine();
        writer.write(activity.getActivityInstruction());
        writer.newLine();
        writer.write(activity.getActivityInfo());
        writer.newLine();
        writer.write(activity.getActivityImage().getDescription());
        writer.newLine();
      }

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }
}

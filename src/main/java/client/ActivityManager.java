package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class creates a register that handles Activity objects.
 *
 * @author Carolin Nordstrom, Oscar Kareld, Chanon Borgstrom, Sofia Hallberg, Edvin Topalovic.
 * @version 1.1
 */

public class ActivityManager {

  private ArrayList<Activity> activityRegister;
  private final Queue<Activity> postponedActivities;

  public ActivityManager(String file) {
    readRegister(file);
    postponedActivities = new LinkedList<>();
  }

  private void readRegister(String file) {
    activityRegister = new ArrayList<>();
    int nbrOfActivities;

    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
      nbrOfActivities = Integer.parseInt(br.readLine());
      for (int i = 0; i < nbrOfActivities; i++) {

        Activity activity = new Activity();

        activity.setActivityID(Integer.parseInt(br.readLine()));
        activity.setActivityName(br.readLine());
        activity.setActivityInstruction(br.readLine());
        activity.setActivityInfo(br.readLine());
        activity.createActivityImage(br.readLine());

        activityRegister.add(activity);
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
   *
   * @return {@link Activity}-object
   * @author Edvin Topalovic
   */
  public Activity getActivity() {
    if (!postponedActivities.isEmpty()) {
      return postponedActivities.remove();
    }

    Collections.shuffle(activityRegister);
    return activityRegister.getFirst();
  }

  /**
   * Method returns {@link Activity}-object by ID from {@link #activityRegister}
   *
   * @param id
   * @return {@link Activity}-object
   * @author Edvin Topalovic
   */
  public Activity getActivity(int id) {
    for (Activity activity : activityRegister) {
      if (activity.getActivityID() == id) {
        return activity;
      }
    }
    return null;
  }

  /**
   * Method allows user to add custom activities to {@link #activityRegister}
   *
   * @param name, inst, info
   * @return {@link Activity}-object
   * @author Edvin Topalovic
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

  public void enqueueActivity(Activity activity) {
    postponedActivities.add(activity);
  }

  public Queue<Activity> getPostponedActivities() {
    return postponedActivities;
  }

}

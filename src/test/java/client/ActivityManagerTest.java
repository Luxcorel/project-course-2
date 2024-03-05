package client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityManagerTest {

  /**
   * Test case ID: TC-6.
   * Requirements: ?.
   */
  @Test
  public void testCreateActivity() {
      String name = "Test name";
      String inst = "Test instruction";
      String info = "Test info";

      ActivityManager activityManager = new ActivityManager("files/activities.txt");
      Activity activity = activityManager.createActivity(name, inst, info);

      assertEquals(name, activity.getActivityName());
      assertEquals(inst, activity.getActivityInstruction());
      assertEquals(info, activity.getActivityInfo());
  }
}
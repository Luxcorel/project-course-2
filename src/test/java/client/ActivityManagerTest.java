package client;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
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

      ActivityManager activityManager = new ActivityManager("src/test/resources/activities_test.json");
      Activity activity = activityManager.createActivity(name, inst, info);

      assertEquals(name, activity.getActivityName());
      assertEquals(inst, activity.getActivityInstruction());
      assertEquals(info, activity.getActivityInfo());
  }

  @AfterEach
  public void cleanUp() {
    File activitiesFile = new File("src/test/resources/activities_test.json");
    if (activitiesFile.exists()) {
      activitiesFile.delete();
    }
  }
}
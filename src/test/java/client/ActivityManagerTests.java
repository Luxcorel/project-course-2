package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

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
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * @author Johannes Rosengren
 */
public class ActivityManagerTests {

  // Tests WITHOUT image

  /**
   * Test case ID: TC-07.
   * Requirements: F25.
   * @author Johannes Rosengren
   */
  @Test
  public void saveFileWithoutImage() {
    File activitiesFile = new File("src/test/resources/activities_test.json");

    ActivityManager activityManager = new ActivityManager("src/test/resources/activities_test.json");

    Activity activity1Write = activityManager.createActivity("abcdefghijklmnopqrstuvwxyzåäö",
        "abcdefghijklmnopqrstuvwxyzåäö", "abcdefghijklmnopqrstuvwxyzåäö");
    Activity activity2Write = activityManager.createActivity("Test Activity 2", "Instructions 2",
        "Info 2");
    Activity activity3Write = activityManager.createActivity("Test Activity 3", "Instructions 3",
        "Info 3");

    activityManager.saveActivitiesToDisc("src/test/resources/activities_test.json");

    assertTrue(activitiesFile.exists());

    try (FileReader reader = new FileReader(activitiesFile)) {
      Gson gson = new GsonBuilder().create();

      ArrayList<Activity> activities = gson.fromJson(reader, new TypeToken<ArrayList<Activity>>() {
      }.getType());
      Activity activity1Read = activities.get(0);
      Activity activity2Read = activities.get(1);
      Activity activity3Read = activities.get(2);

      assertEquals(activity1Write, activity1Read);
      assertEquals(activity2Write, activity2Read);
      assertEquals(activity3Write, activity3Read);

    } catch (IOException e) {
      fail("ActivityManagerTests.saveFileWithoutImage(): Could not read activity input file");
    }

  }

  /**
   * Test case ID: TC-08.
   * Requirements: F26, F27.
   * @author Johannes Rosengren
   */
  @Test
  public void readFileWithoutImage() {
    Gson gson = new GsonBuilder()
        .create();

    ArrayList<Activity> activities = new ArrayList<>();

    Activity activity1 = new Activity("abcdefghijklmnopqrstuvwxyzåäö");
    activity1.setActivityID("1");
    activity1.setActivityInstruction("abcdefghijklmnopqrstuvwxyzåäö");
    activity1.setActivityInfo("abcdefghijklmnopqrstuvwxyzåäö");
    activities.add(activity1);

    Activity activity2 = new Activity("Test Activity 2");
    activity2.setActivityID("2");
    activity2.setActivityInstruction("Instructions 2");
    activity2.setActivityInfo("Info 2");
    activities.add(activity2);

    Activity activity3 = new Activity("Test Activity 3");
    activity3.setActivityID("3");
    activity3.setActivityInstruction("Instructions 3");
    activity3.setActivityInfo("Info 3");
    activities.add(activity3);

    String json = gson.toJson(activities);

    File saveFile = new File("src/test/resources/activities_test.json");
    try {
      saveFile.createNewFile();
    } catch (IOException e) {
      fail("ActivityManagerTests.readFileWithoutImage(): Could not create file");
    }

    try (BufferedWriter writer = new BufferedWriter(
        new FileWriter(saveFile, StandardCharsets.UTF_8))) {
      writer.write(json);
    } catch (IOException e) {
      fail("ActivityManagerTests.readFileWithoutImage(): Could not write to file");
    }

    ActivityManager activityManager = new ActivityManager("src/test/resources/activities_test.json");

    Activity activity1Read = activityManager.getActivity(activity1.getActivityID()).get();
    Activity activity2Read = activityManager.getActivity(activity2.getActivityID()).get();
    Activity activity3Read = activityManager.getActivity(activity3.getActivityID()).get();

    assertEquals(activity1, activity1Read);
    assertEquals(activity2, activity2Read);
    assertEquals(activity3, activity3Read);
  }

  /**
   * Test case ID: TC-09.
   * Requirements: F25, F26, F27.
   * @author Johannes Rosengren
   */
  @Test
  public void saveAndLoadFileWithoutImage() {
    ActivityManager activityManagerSave = new ActivityManager("src/test/resources/activities_test.json");

    Activity activity1Write = activityManagerSave.createActivity("abcdefghijklmnopqrstuvwxyzåäö",
        "abcdefghijklmnopqrstuvwxyzåäö", "abcdefghijklmnopqrstuvwxyzåäö");
    Activity activity2Write = activityManagerSave.createActivity("Test Activity 2",
        "Instructions 2", "Info 2");
    Activity activity3Write = activityManagerSave.createActivity("Test Activity 3",
        "Instructions 3", "Info 3");

    activityManagerSave.saveActivitiesToDisc("src/test/resources/activities_test.json");

    ActivityManager activityManagerLoad = new ActivityManager("src/test/resources/activities_test.json");

    Activity activity1Read = activityManagerLoad.getActivity(activity1Write.getActivityID()).get();
    Activity activity2Read = activityManagerLoad.getActivity(activity2Write.getActivityID()).get();
    Activity activity3Read = activityManagerLoad.getActivity(activity3Write.getActivityID()).get();

    assertEquals(activity1Write, activity1Read);
    assertEquals(activity2Write, activity2Read);
    assertEquals(activity3Write, activity3Read);
  }

  /**
   * Test case ID: TC-13.
   * Requirements: F010b.
   * @author Johannes Rosengren
   */
  @Test
  public void postponeActivityWithoutImageWithOtherActivitiesPresent() {
    ActivityManager activityManager = new ActivityManager("src/test/resources/activities_test.json");

    activityManager.createActivity("Random activity 1", "Test instruction", "Test info");
    activityManager.createActivity("Random activity 2", "Test instruction", "Test info");
    activityManager.createActivity("Random activity 3", "Test instruction", "Test info");

    Activity postponedActivity = new Activity();
    postponedActivity.setActivityID(UUID.randomUUID().toString());
    postponedActivity.setActivityName("Test activity");
    postponedActivity.setActivityInstruction("Test instruction");
    postponedActivity.setActivityInfo("Test info");

    activityManager.enqueueActivity(postponedActivity);

    Activity returnedActivity = activityManager.getActivity().orElseThrow();

    assertEquals(postponedActivity, returnedActivity);
  }

  /**
   * Test case ID: TC-14.
   * Requirements: F010b.
   * @author Johannes Rosengren
   */
  @Test
  public void postponeActivityWithoutImageWithoutOtherActivitiesPresent() {
    ActivityManager activityManager = new ActivityManager("src/test/resources/activities_test.json");

    Activity postponedActivity = new Activity();
    postponedActivity.setActivityID(UUID.randomUUID().toString());
    postponedActivity.setActivityName("Test activity");
    postponedActivity.setActivityInstruction("Test instruction");
    postponedActivity.setActivityInfo("Test info");

    activityManager.enqueueActivity(postponedActivity);

    Activity returnedActivity = activityManager.getActivity().orElseThrow();

    assertEquals(postponedActivity, returnedActivity);
  }

  /**
   * Test case ID: TC-17.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addCustomActivityWithoutImage() {
    ActivityManager activityManager = new ActivityManager(
        "src/test/resources/activities_test.json");
    activityManager.getActivityRegister().clear();

    Activity customActivity = activityManager.createActivity("Custom activity",
        "Custom activity instruction", "Custom activity info");
    assertNotNull(customActivity);

    Activity addedCustomActivity = activityManager.getActivity().orElseThrow();
    assertEquals(customActivity, addedCustomActivity);
  }

  // Tests WITH image

  /**
   * Test case ID: TC-10.
   * Requirements: F25.
   * @author Johannes Rosengren
   */
  @Test
  public void saveFileWithImage() {
    File activitiesFile = new File("src/test/resources/activities_test.json");

    ActivityManager manager = new ActivityManager("src/test/resources/activities_test.json");

    Activity activity1Write = manager.createActivity("abcdefghijklmnopqrstuvwxyzåäö", "abcdefghijklmnopqrstuvwxyzåäö",
        "abcdefghijklmnopqrstuvwxyzåäö", "src/test/resources/test_image.png");
    Activity activity2Write = manager.createActivity("Test Activity 2", "Instructions 2", "Info 2",
        "src/test/resources/test_image.png");
    Activity activity3Write = manager.createActivity("Test Activity 3", "Instructions 3", "Info 3",
        "src/test/resources/test_image.png");

    manager.saveActivitiesToDisc("src/test/resources/activities_test.json");

    assertTrue(activitiesFile.exists());

    try (FileReader reader = new FileReader(activitiesFile)) {
      Gson gson = new GsonBuilder().create();

      ArrayList<Activity> activities = gson.fromJson(reader, new TypeToken<ArrayList<Activity>>() {
      }.getType());
      Activity activity1Read = activities.get(0);
      Activity activity2Read = activities.get(1);
      Activity activity3Read = activities.get(2);

      assertEquals(activity1Write, activity1Read);
      assertEquals(activity2Write, activity2Read);
      assertEquals(activity3Write, activity3Read);

    } catch (IOException e) {
      fail("ActivityManagerTests.saveFileWithImage(): Could not read activity input file");
    }

  }

  /**
   * Test case ID: TC-11.
   * Requirements: F26, F27.
   * @author Johannes Rosengren
   */
  @Test
  public void readFileWithImage() {
    Gson gson = new GsonBuilder()
        .create();

    ArrayList<Activity> activities = new ArrayList<>();

    Activity activity1 = new Activity("abcdefghijklmnopqrstuvwxyzåäö");
    activity1.setActivityID("1");
    activity1.setActivityInstruction("abcdefghijklmnopqrstuvwxyzåäö");
    activity1.setActivityInfo("abcdefghijklmnopqrstuvwxyzåäö");
    activity1.setActivityImage("src/test/resources/test_image.png");
    activities.add(activity1);

    Activity activity2 = new Activity("Test Activity 2");
    activity2.setActivityID("2");
    activity2.setActivityInstruction("Instructions 2");
    activity2.setActivityInfo("Info 2");
    activity2.setActivityImage("src/test/resources/test_image.png");
    activities.add(activity2);

    Activity activity3 = new Activity("Test Activity 3");
    activity3.setActivityID("3");
    activity3.setActivityInstruction("Instructions 3");
    activity3.setActivityInfo("Info 3");
    activity2.setActivityImage("src/test/resources/test_image.png");
    activities.add(activity3);

    String json = gson.toJson(activities);

    File saveFile = new File("src/test/resources/activities_test.json");
    try {
      saveFile.createNewFile();
    } catch (IOException e) {
      fail("ActivityManagerTests.readFileWithImage(): Could not create file");
    }

    try (BufferedWriter writer = new BufferedWriter(
        new FileWriter(saveFile, StandardCharsets.UTF_8))) {
      writer.write(json);
    } catch (IOException e) {
      fail("ActivityManagerTests.readFileWithImage(): Could not write to file");
    }

    ActivityManager activityManager = new ActivityManager("src/test/resources/activities_test.json");

    Activity activity1Read = activityManager.getActivity(activity1.getActivityID()).get();
    Activity activity2Read = activityManager.getActivity(activity2.getActivityID()).get();
    Activity activity3Read = activityManager.getActivity(activity3.getActivityID()).get();

    assertEquals(activity1, activity1Read);
    assertEquals(activity2, activity2Read);
    assertEquals(activity3, activity3Read);
  }

  /**
   * Test case ID: TC-12.
   * Requirements: F25, F26, F27.
   * @author Johannes Rosengren
   */
  @Test
  public void saveAndLoadFileWithImage() {
    ActivityManager activityManagerSave = new ActivityManager("src/test/resources/activities_test.json");

    Activity activity1Write = activityManagerSave.createActivity("abcdefghijklmnopqrstuvwxyzåäö",
        "abcdefghijklmnopqrstuvwxyzåäö", "abcdefghijklmnopqrstuvwxyzåäö", "src/test/resources/test_image.png");
    Activity activity2Write = activityManagerSave.createActivity("Test Activity 2",
        "Instructions 2", "Info 2", "src/test/resources/test_image.png");
    Activity activity3Write = activityManagerSave.createActivity("Test Activity 3",
        "Instructions 3", "Info 3", "src/test/resources/test_image.png");

    activityManagerSave.saveActivitiesToDisc("src/test/resources/activities_test.json");

    ActivityManager activityManagerLoad = new ActivityManager("src/test/resources/activities_test.json");

    Activity activity1Read = activityManagerLoad.getActivity(activity1Write.getActivityID()).get();
    Activity activity2Read = activityManagerLoad.getActivity(activity2Write.getActivityID()).get();
    Activity activity3Read = activityManagerLoad.getActivity(activity3Write.getActivityID()).get();

    assertEquals(activity1Write, activity1Read);
    assertEquals(activity2Write, activity2Read);
    assertEquals(activity3Write, activity3Read);
  }

  /**
   * Test case ID: TC-15.
   * Requirements: F010b.
   * @author Johannes Rosengren
   */
  @Test
  public void postponeActivityWithImageWithOtherActivitiesPresent() {
    ActivityManager activityManager = new ActivityManager("src/test/resources/activities_test.json");

    activityManager.createActivity("Random activity 1", "Test instruction", "Test info", "src/test/resources/test_image.png");
    activityManager.createActivity("Random activity 2", "Test instruction", "Test info", "src/test/resources/test_image.png");
    activityManager.createActivity("Random activity 3", "Test instruction", "Test info", "src/test/resources/test_image.png");

    Activity postponedActivity = new Activity();
    postponedActivity.setActivityID(UUID.randomUUID().toString());
    postponedActivity.setActivityName("Test activity");
    postponedActivity.setActivityInstruction("Test instruction");
    postponedActivity.setActivityInfo("Test info");
    postponedActivity.setActivityImage("src/test/resources/test_image.png");

    activityManager.enqueueActivity(postponedActivity);

    Activity returnedActivity = activityManager.getActivity().orElseThrow();

    assertEquals(postponedActivity, returnedActivity);
  }

  /**
   * Test case ID: TC-16.
   * Requirements: F010b.
   * @author Johannes Rosengren
   */
  @Test
  public void postponeActivityWithImageWithoutOtherActivitiesPresent() {
    ActivityManager activityManager = new ActivityManager("src/test/resources/activities_test.json");

    Activity postponedActivity = new Activity();
    postponedActivity.setActivityID(UUID.randomUUID().toString());
    postponedActivity.setActivityName("Test activity");
    postponedActivity.setActivityInstruction("Test instruction");
    postponedActivity.setActivityInfo("Test info");
    postponedActivity.setActivityImage("src/test/resources/test_image.png");

    activityManager.enqueueActivity(postponedActivity);

    Activity returnedActivity = activityManager.getActivity().orElseThrow();

    assertEquals(postponedActivity, returnedActivity);
  }

  /**
   * Test case ID: TC-18.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addCustomActivityWithImage() {
    ActivityManager activityManager = new ActivityManager(
        "src/test/resources/activities_test.json");
    activityManager.getActivityRegister().clear();

    Activity customActivity = activityManager.createActivity("Custom activity",
        "Custom activity instruction", "Custom activity info", "src/test/resources/test_image.png");
    assertNotNull(customActivity);

    Activity addedCustomActivity = activityManager.getActivity().orElseThrow();
    assertEquals(customActivity, addedCustomActivity);
  }

  // IO exception related tests

  /**
   * Test case ID: TC-56.
   * Requirements: F25.
   * @author Johannes Rosengren
   */
  @Test
  public void cantCreateSaveFile_shouldThrow() throws IOException {
    String filePath = "src/test/resources/activities_test.json";

    ActivityManager activityManager = spy(new ActivityManager(filePath));

    when(activityManager.fileExists(filePath)).thenReturn(false);
    doThrow(IOException.class).when(activityManager).createFile(filePath);

    try {
      activityManager.saveActivitiesToDisc(filePath);
    } catch (RuntimeException e) {
      return;
    }

    fail("Should have thrown exception when trying to save the file");
  }

  /**
   * Test case ID: TC-57.
   * Requirements: F26.
   * @author Johannes Rosengren
   */
  @Test
  public void cantReadExistingSaveFile_shouldThrow() {
    String filePath = "src/test/resources/activities_test.json";

    ActivityManager activityManager = spy(new ActivityManager(filePath));

    when(activityManager.fileExists(filePath)).thenReturn(true);

    try {
      activityManager.readActivitiesFromDisc(filePath);
    } catch (RuntimeException e) {
      return;
    }

    fail("Should have thrown exception when trying to read the file");
  }

  /**
   * Test case ID: TC-58.
   * Requirements: F26.
   * @author Johannes Rosengren
   */
  @Test
  public void emptyExistingSaveFile_ShouldNotThrow() throws IOException {
    String filePath = "src/test/resources/activities_test.json";
    new File(filePath).getAbsoluteFile().createNewFile();

    ActivityManager activityManager = new ActivityManager(filePath);

    activityManager.readActivitiesFromDisc(filePath);
  }

  /**
   * Test case ID: TC-59.
   * Requirements: F26.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_NoActivitiesAvailable_ShouldReturnEmptyOptional() {
    String filePath = "src/test/resources/activities_test.json";
    ActivityManager activityManager = new ActivityManager(filePath);

    Optional<Activity> returnValue = activityManager.getActivity();

    assertTrue(returnValue.isEmpty());
  }

  /**
   * Test case ID: TC-60.
   * Requirements: F26.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivityById_NoActivityWithSpecifiedIdExists_ShouldReturnEmptyOptional() {
    String filePath = "src/test/resources/activities_test.json";
    ActivityManager activityManager = new ActivityManager(filePath);

    Optional<Activity> returnValue = activityManager.getActivity("activity_that_doesnt_exist");

    assertTrue(returnValue.isEmpty());
  }

  /**
   * Test case ID: TC-61.
   * Requirements: F25.
   * @author Johannes Rosengren
   */
  @Test
  public void createActivityCantSaveActivity_ShouldThrowError() {
    String filePath = "src/test/resources/activities_test.json";
    ActivityManager activityManager = spy(new ActivityManager(filePath));

    when(activityManager.getActivity(anyString())).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> activityManager.createActivity("Custom activity",
        "Custom activity instruction", "Custom activity info", "src/test/resources/test_image.png"));
  }

  @AfterEach
  public void cleanUp() {
    File activitiesFile = new File("src/test/resources/activities_test.json");
    if (activitiesFile.exists()) {
      activitiesFile.delete();
    }
  }

}

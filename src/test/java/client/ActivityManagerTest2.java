package client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class ActivityManagerTest2 {

  /*
  @Test
  public void testSaveActivitiesToDisc() {
    ActivityManager manager = new ActivityManager("activities.txt");
    // Lägg till några aktiviteter för att testa
    manager.createActivity("Test Activity 1", "Instructions 1", "Info 1");
    manager.createActivity("Test Activity 2", "Instructions 2", "Info 2");

    // Testa spara aktiviteter till disk
    try {
      manager.saveActivitiesToDisc("activities.txt");
      File file = new File("activities.txt");
      assertTrue(file.exists()); // Kontrollera om filen skapades

      // Kontrollera om data i filen är korrekt
      try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        int lineNumber = 0;
        int expectedNumberOfActivities = 2; // Förväntat antal aktiviteter
        while ((line = reader.readLine()) != null) {
          lineNumber++;
          if (lineNumber == 1) { // Första raden ska innehålla antalet aktiviteter
            int actualNumberOfActivities = Integer.parseInt(line);
            assertEquals(expectedNumberOfActivities, actualNumberOfActivities);
          } else {
            // Processera övriga rader för aktivitetsdata
            // För att hålla det enkelt, ignorera det här exemplet
          }
        }
      } catch (IOException e) {
        fail("Kunde inte läsa från filen: " + e.getMessage());
      }

      // För att rensa upp testet, ta bort filen
      file.delete();
    } catch (Exception e) {
      fail("Kastade ett undantag: " + e.getMessage());
    }
  }
   */

}
package client;

import client.OSDetection.OS;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author edvintopa
 * @project project-course-2
 * @created 2024-02-20
 */
class OSDetectionTest {

  /*
   * ONLY ONE OF THESE ARE SUPPOSED TO PASS
   */
  @Test
  void getOS_Mac() {
    OSDetection.OS expectedOS = OS.MAC;
    OSDetection.OS actualOS = OSDetection.getOS();

    assertEquals(expectedOS, actualOS);
  }
  @Test
  void getOS_Windows() {
    OSDetection.OS expectedOS = OS.WINDOWS;
    OSDetection.OS actualOS = OSDetection.getOS();

    assertEquals(expectedOS, actualOS);
  }

  @Test
  void getOS_Unsupported() {
    OSDetection.OS expectedOS = OS.UNSUPPORTED;
    OSDetection.OS actualOS = OSDetection.getOS();

    assertEquals(expectedOS, actualOS);
  }
}
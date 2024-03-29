package client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import client.OSDetection.OS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;

/**
 * @author edvintopa
 * @author Johannes Rosengren
 */
public class OSDetectionTests {

  /**
   * Test case ID: TC-1.
   * Requirements: F29.
   */
  @Test
  @EnabledOnOs(org.junit.jupiter.api.condition.OS.MAC)
  public void getOS_Mac() {
    OSDetection.OS expectedOS = OS.MAC;
    OSDetection.OS actualOS = OSDetection.getOS();

    assertEquals(expectedOS, actualOS);
  }

  /**
   * Test case ID: TC-2.
   * Requirements: F29.
   */
  @Test
  @EnabledOnOs(org.junit.jupiter.api.condition.OS.WINDOWS)
  public void getOS_Windows() {
    OSDetection.OS expectedOS = OS.WINDOWS;
    OSDetection.OS actualOS = OSDetection.getOS();

    assertEquals(expectedOS, actualOS);
  }

  /**
   * Test case ID: TC-3.
   * Requirements: F29.
   */
  @Test
  @DisabledOnOs({org.junit.jupiter.api.condition.OS.MAC,
      org.junit.jupiter.api.condition.OS.WINDOWS})
  public void getOS_Unsupported() {
    OSDetection.OS expectedOS = OS.UNSUPPORTED;
    OSDetection.OS actualOS = OSDetection.getOS();

    assertEquals(expectedOS, actualOS);
  }

}
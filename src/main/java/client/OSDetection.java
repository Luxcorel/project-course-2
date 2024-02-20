package client;

/**
 * This class is used to detect the operating system used by the client.
 */
public class OSDetection {

  public enum OS {
    MAC, WINDOWS, UNSUPPORTED
  }

  /**
   * Returns the operating system used: Windows, Mac or Unsupported
   *
   * @return {@link OS}
   * @implNote Part of requirement F29
   */
  public static OS getOS() {
    String operatingSystem = System.getProperty("os.name").toLowerCase();

    if (operatingSystem.contains("windows")) {
      return OS.WINDOWS;
    }

    if (operatingSystem.contains("mac")) {
      return OS.MAC;
    }

    return OS.UNSUPPORTED;
  }

}

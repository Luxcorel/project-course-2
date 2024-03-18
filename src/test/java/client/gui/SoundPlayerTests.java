package client.gui;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class SoundPlayerTests {

  /**
   * Test case ID: TC-71.
   * Requirements: F034.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void soundPlayerValidAudioFileAsInput_ShouldNotThrow() {
    SoundPlayer soundPlayer = new SoundPlayer("src/test/resources/test_sound.wav");
    soundPlayer.play();
  }

  /**
   * Test case ID: TC-72.
   * Requirements: F034.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void soundPlayerEmptyStringAsInput_ShouldThrowError() {
    assertThrows(RuntimeException.class, () -> new SoundPlayer(""));
  }

  /**
   * Test case ID: TC-73.
   * Requirements: F034.
   *
   * @author Johannes Rosengren
   */
  @Test
  public void soundPlayerNullAsInput_ShouldThrowError() {
    assertThrows(RuntimeException.class, () -> new SoundPlayer(null));
  }

}

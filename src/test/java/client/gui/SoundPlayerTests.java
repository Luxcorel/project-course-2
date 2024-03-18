package client.gui;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.sound.sampled.AudioSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

@DisabledIf("noAudioMixerAvailable")
public class SoundPlayerTests {

  public boolean noAudioMixerAvailable() {
    return AudioSystem.getMixerInfo().length == 0;
  }

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

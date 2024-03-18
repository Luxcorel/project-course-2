package client.gui;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
  private Clip clip;

  public SoundPlayer(String filePath) {
    try {

      AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

      clip = AudioSystem.getClip();

      clip.open(audioStream);
    } catch (Exception e) {
      throw new RuntimeException("Could not initialize SoundPlayer", e);
    }
  }

  public void play() {
    clip.start();
  }
}


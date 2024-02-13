package client.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import javax.swing.JButton;
import javax.swing.JComboBox;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ButtonListenerTest {

  @Test
  public void testChangeTimerInterval() throws Exception {
    // Create an instance of AppPanel
    MainPanel mainPanelMock = Mockito.mock(MainPanel.class);
    AppPanel appPanelTest = new AppPanel(mainPanelMock);

    Field fieldCmbTimeLimit = AppPanel.class.getDeclaredField("cmbTimeLimit");
    fieldCmbTimeLimit.setAccessible(true);
    JComboBox cmbTimeLimit = (JComboBox) fieldCmbTimeLimit.get(appPanelTest);

    Field fieldBtnInterval = AppPanel.class.getDeclaredField("btnInterval");
    fieldBtnInterval.setAccessible(true);
    JButton btnInterval = (JButton) fieldBtnInterval.get(appPanelTest);

    // Set a value in the cmbTimeLimit combo box
    cmbTimeLimit.setSelectedItem("30");

    // Simulate a button click on the btnInterval button
    btnInterval.doClick();

    Field fieldMinuteInterval = AppPanel.class.getDeclaredField("minuteInterval");
    fieldMinuteInterval.setAccessible(true);
    int minuteInterval = (int) fieldMinuteInterval.get(appPanelTest);

    // Assert that the minuteInterval field has been updated to the expected value
    assertEquals(30, minuteInterval);
  }
}


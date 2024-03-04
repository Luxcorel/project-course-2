package client.gui;

import client.ClientController;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.lang.reflect.Field;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AppPanelTest {
  /*
  @Test
  void testBtnStartTimerClick() throws IllegalAccessException, NoSuchFieldException, InterruptedException {
    MainFrame mainFrameMock = Mockito.mock(MainFrame.class);
    MainPanel mainPanelTest = new MainPanel(mainFrameMock, "Test");
    AppPanel appPanelTest = new AppPanel(mainPanelTest);

    Field fieldBtn = AppPanel.class.getDeclaredField("btnStartTimer");
    fieldBtn.setAccessible(true);
    JButton btnStartTimer = (JButton) fieldBtn.get(appPanelTest);

    Field fieldLbl = AppPanel.class.getDeclaredField("lblTimerInfo");
    fieldLbl.setAccessible(true);
    JLabel lblTimerInfo = (JLabel) fieldLbl.get(appPanelTest);

    String initialText = lblTimerInfo.getText();
    btnStartTimer.doClick();
    Thread.sleep(1000);
    String afterClickText = lblTimerInfo.getText();

    assertNotEquals(initialText, afterClickText);
  }
   */

}
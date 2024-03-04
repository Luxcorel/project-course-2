package client;

import client.gui.AppPanel;
import client.gui.MainFrame;
import client.gui.MainPanel;
import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import java.lang.reflect.Field;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ClientControllerTest {
  /*
  @Test
  void testNoLogin() throws NoSuchFieldException, IllegalAccessException {
    MainFrame mainFrameMock = Mockito.mock(MainFrame.class);
    AppPanel appPanel = new AppPanel(new MainPanel(mainFrameMock, null));

    Field field = appPanel.getClass().getDeclaredField("btnInterval");
    field.setAccessible(true);
    JButton btnInterval = (JButton) field.get(appPanel);

    assertNotNull(btnInterval);
  }
  */

}
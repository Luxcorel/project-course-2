package client.gui;

import client.gui.AppPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppPanelTest {
    private AppPanel appPanel;

    @BeforeEach
    public void setup() {
        appPanel = new AppPanel(null); // Pass null as MainPanel since it's not used in the test
    }

    @Test
    public void testCountTimerInterval() throws NoSuchFieldException, IllegalAccessException {
        int newInterval = 20;
        appPanel.countTimerInterval(newInterval);

        Field minuteIntervalField = AppPanel.class.getDeclaredField("minuteInterval");
        minuteIntervalField.setAccessible(true);
        int minuteInterval = (int) minuteIntervalField.get(appPanel);

        assertEquals(newInterval - 1, minuteInterval);
    }
}
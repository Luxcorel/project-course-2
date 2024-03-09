package client.gui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import client.Activity;
import client.ClientController;
import java.awt.Component;
import javax.swing.JOptionPane;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CustomActivityUITests {

  // CustomActivityUI.addActivity() cases

  // ACTIVITY SHOULD NOT BE ADDED CASES

  /**
   * Test case ID: TC-19.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithEmptyStrings_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("", "", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);

    assertThrows(IllegalArgumentException.class, ui::addCustomActivity);
  }

  /**
   * Test case ID: TC-20.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithNulls_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity(null, null, null))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName(null);
    ui.setActivityInstruction(null);
    ui.setActivityInfo(null);
    ui.setImage(null);

    assertThrows(IllegalArgumentException.class, ui::addCustomActivity);
  }

  /**
   * Test case ID: TC-21.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithNameButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("Test name", "", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");

    assertThrows(IllegalArgumentException.class, ui::addCustomActivity);
  }

  /**
   * Test case ID: TC-22.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithNameAndInfoButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("Test name", "", "Test info"))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setImage("Test info");

    assertThrows(IllegalArgumentException.class, ui::addCustomActivity);
  }

  /**
   * Test case ID: TC-23.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithNameAndInstructionButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("Test name", "Test instruction", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");

    assertThrows(IllegalArgumentException.class, ui::addCustomActivity);
  }

  /**
   * Test case ID: TC-24.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithInstructionButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("", "Test instruction", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityInstruction("Test instruction");

    assertThrows(IllegalArgumentException.class, ui::addCustomActivity);
  }

  /**
   * Test case ID: TC-25.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithInstructionAndInfoButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("", "Test instruction", "Test info"))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");

    assertThrows(IllegalArgumentException.class, ui::addCustomActivity);
  }

  /**
   * Test case ID: TC-26.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithInfoButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("", "", "Test info"))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityInfo("Test info");

    assertThrows(IllegalArgumentException.class, ui::addCustomActivity);
  }

  /**
   * Test case ID: TC-27.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_enterCustomActivityWithEmptyStringsButCancel_shouldNotAddActivity() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("", "", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.CANCEL_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        fail("Should not have encountered error message");
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);

    assertTrue(ui.addCustomActivity().isEmpty());
  }

  /**
   * Test case ID: TC-28.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_enterCustomActivityWithNameAndDescriptionAndInfoAndImageButCancel_shouldNotAddActivity() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenThrow(new AssertionError("Should not have added activity on cancel"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.CANCEL_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          fail("Shouldn't have encountered error message");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");
    ui.setImage("src/test/resources/test_image.png");

    assertTrue(ui.addCustomActivity().isEmpty());
  }

  /**
   * Test case ID: TC-29.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithInvalidInputThenCancel_shouldNotAddActivity() {
    ClientController mock = mock(ClientController.class);

    when(mock.addActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenThrow(new AssertionError("Should not have added activity on cancel"));

    IMessageProvider messageProvider = Mockito.mock(IMessageProvider.class);
    when(messageProvider.showConfirmDialog(any(), any(Object.class), anyString(), anyInt(),
        anyInt())).thenReturn(
        JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION);

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);

    assertTrue(ui.addCustomActivity().isEmpty());
  }

  // ACTIVITY SHOULD BE ADDED CASES

  /**
   * Test case ID: TC-30.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithNameAndDescriptionAndInfo_shouldAddActivity() {
    ClientController mock = mock(ClientController.class);

    Activity expected = new Activity();
    expected.setActivityName("Test name");
    expected.setActivityInstruction("Test instruction");
    expected.setActivityInfo("Test info");

    when(mock.addActivity("Test name", "Test instruction", "Test info"))
        .thenReturn(expected);

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          fail("Should not have encountered error message");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");

    Activity added = ui.addCustomActivity().orElseThrow();
    assertEquals(expected, added);
  }

  /**
   * Test case ID: TC-31.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_submitCustomActivityWithNameAndDescriptionAndInfoAndImage_shouldAddActivity() {
    ClientController mock = mock(ClientController.class);

    Activity expected = new Activity();
    expected.setActivityName("Test name");
    expected.setActivityInstruction("Test instruction");
    expected.setActivityInfo("Test info");
    expected.setActivityImage("src/test/resources/test_image.png");

    when(mock.addActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenReturn(expected);

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          fail("Should not have encountered error message");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");
    ui.setImage("src/test/resources/test_image.png");

    Activity added = ui.addCustomActivity().orElseThrow();
    assertEquals(expected, added);
  }

  /**
   * Test case ID: TC-32.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void addActivity_enterCustomActivityWithNameAndDescriptionAndInfoAndImageButDeselectImage_shouldAddActivityWithoutImage() {
    ClientController mock = mock(ClientController.class);

    Activity expected = new Activity();
    expected.setActivityName("Test name");
    expected.setActivityInstruction("Test instruction");
    expected.setActivityInfo("Test info");

    when(mock.addActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenThrow(new AssertionError("Should not have added with image"));

    when(mock.addActivity("Test name", "Test instruction", "Test info"))
        .thenReturn(expected);

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          fail("Should not have encountered error message");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");
    ui.setImage("src/test/resources/test_image.png");
    ui.resetImage();

    Activity added = ui.addCustomActivity().orElseThrow();
    assertEquals(expected, added);
  }

  /**
   * Test case ID: TC-33.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void beginCustomActivityWithInvalidInputsButThenEnterNameAndDescriptionAndInfoAndImage_shouldAddActivity() {
    ClientController mock = mock(ClientController.class);

    Activity expected = new Activity();
    expected.setActivityName("Test name");
    expected.setActivityInstruction("Test instruction");
    expected.setActivityInfo("Test info");
    expected.setActivityImage("src/test/resources/test_image.png");

    when(mock.addActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenReturn(expected);

    IMessageProvider messageProvider = Mockito.mock(IMessageProvider.class);
    when(messageProvider.showConfirmDialog(any(), any(Object.class), anyString(), anyInt(),
        anyInt())).thenReturn(
        JOptionPane.OK_OPTION);

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);

    doAnswer(invocation -> {
      ui.setActivityName("Test name");
      ui.setActivityInstruction("Test instruction");
      ui.setActivityInfo("Test info");
      ui.setImage("src/test/resources/test_image.png");
      return null;
    }).when(messageProvider).showMessageDialog(any(), any(Object.class), anyString(), anyInt());

    Activity added = ui.addCustomActivity().orElseThrow();
    assertEquals(expected, added);
  }

  // CustomActivityUI.getActivity() cases

  // ACTIVITY SHOULD NOT BE RETURNED CASES

  /**
   * Test case ID: TC-34.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithEmptyStrings_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("", "", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);

    assertThrows(IllegalArgumentException.class, ui::getCustomActivity);
  }

  /**
   * Test case ID: TC-35.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithNulls_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity(null, null, null))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName(null);
    ui.setActivityInstruction(null);
    ui.setActivityInfo(null);
    ui.setImage(null);

    assertThrows(IllegalArgumentException.class, ui::getCustomActivity);
  }

  /**
   * Test case ID: TC-36.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithNameButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("Test name", "", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");

    assertThrows(IllegalArgumentException.class, ui::getCustomActivity);
  }

  /**
   * Test case ID: TC-37.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithNameAndInfoButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("Test name", "", "Test info"))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setImage("Test info");

    assertThrows(IllegalArgumentException.class, ui::getCustomActivity);
  }

  /**
   * Test case ID: TC-38.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithNameAndInstructionButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("Test name", "Test instruction", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");

    assertThrows(IllegalArgumentException.class, ui::getCustomActivity);
  }

  /**
   * Test case ID: TC-39.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithInstructionButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("", "Test instruction", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityInstruction("Test instruction");

    assertThrows(IllegalArgumentException.class, ui::getCustomActivity);
  }

  /**
   * Test case ID: TC-40.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithInstructionAndInfoButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("", "Test instruction", "Test info"))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");

    assertThrows(IllegalArgumentException.class, ui::getCustomActivity);
  }

  /**
   * Test case ID: TC-41.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithInfoButOthersEmptyString_shouldFail() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("", "", "Test info"))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          throw new IllegalArgumentException("Incomplete activity was blocked from being added");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityInfo("Test info");

    assertThrows(IllegalArgumentException.class, ui::getCustomActivity);
  }

  /**
   * Test case ID: TC-42.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_enterCustomActivityWithEmptyStringsButCancel_shouldNotReturnActivity() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("", "", ""))
        .thenThrow(new AssertionError("Should not have tried to add incomplete activity"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.CANCEL_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        fail("Should not have encountered error message");
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);

    assertTrue(ui.getCustomActivity().isEmpty());
  }

  /**
   * Test case ID: TC-43.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_enterCustomActivityWithNameAndDescriptionAndInfoAndImageButCancel_shouldNotReturnActivity() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenThrow(new AssertionError("Should not have added activity on cancel"));

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.CANCEL_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          fail("Shouldn't have encountered error message");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");
    ui.setImage("src/test/resources/test_image.png");

    assertTrue(ui.getCustomActivity().isEmpty());
  }

  /**
   * Test case ID: TC-44.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithInvalidInputThenCancel_shouldNotReturnActivity() {
    ClientController mock = mock(ClientController.class);

    when(mock.packageActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenThrow(new AssertionError("Should not have added activity on cancel"));

    IMessageProvider messageProvider = Mockito.mock(IMessageProvider.class);
    when(messageProvider.showConfirmDialog(any(), any(Object.class), anyString(), anyInt(),
        anyInt())).thenReturn(
        JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION);

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);

    assertTrue(ui.getCustomActivity().isEmpty());
  }

  // ACTIVITY SHOULD BE RETURNED CASES

  /**
   * Test case ID: TC-45.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithNameAndDescriptionAndInfo_shouldReturnActivity() {
    ClientController mock = mock(ClientController.class);

    Activity expected = new Activity();
    expected.setActivityName("Test name");
    expected.setActivityInstruction("Test instruction");
    expected.setActivityInfo("Test info");

    when(mock.packageActivity("Test name", "Test instruction", "Test info"))
        .thenReturn(expected);

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          fail("Should not have encountered error message");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");

    Activity added = ui.getCustomActivity().orElseThrow();
    assertEquals(expected, added);
  }

  /**
   * Test case ID: TC-46.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithNameAndDescriptionAndInfoAndImage_shouldReturnActivity() {
    ClientController mock = mock(ClientController.class);

    Activity expected = new Activity();
    expected.setActivityName("Test name");
    expected.setActivityInstruction("Test instruction");
    expected.setActivityInfo("Test info");
    expected.setActivityImage("src/test/resources/test_image.png");

    when(mock.packageActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenReturn(expected);

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          fail("Should not have encountered error message");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");
    ui.setImage("src/test/resources/test_image.png");

    Activity added = ui.getCustomActivity().orElseThrow();
    assertEquals(expected, added);
  }

  /**
   * Test case ID: TC-47.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_submitCustomActivityWithNameAndDescriptionAndInfoAndImageButDeselectImage_shouldReturnActivityWithoutImage() {
    ClientController mock = mock(ClientController.class);

    Activity expected = new Activity();
    expected.setActivityName("Test name");
    expected.setActivityInstruction("Test instruction");
    expected.setActivityInfo("Test info");

    when(mock.packageActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenThrow(new AssertionError("Should not have added with image"));

    when(mock.packageActivity("Test name", "Test instruction", "Test info"))
        .thenReturn(expected);

    IMessageProvider messageProvider = new IMessageProvider() {
      @Override
      public int showConfirmDialog(Component parentComponent, Object message, String title,
          int optionType, int messageType) {
        return JOptionPane.OK_OPTION;
      }

      @Override
      public void showMessageDialog(Component parentComponent, Object message, String title,
          int messageType) {
        if (messageType == JOptionPane.ERROR_MESSAGE) {
          fail("Should not have encountered error message");
        }
      }

    };

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);
    ui.setActivityName("Test name");
    ui.setActivityInstruction("Test instruction");
    ui.setActivityInfo("Test info");
    ui.setImage("src/test/resources/test_image.png");
    ui.resetImage();

    Activity added = ui.getCustomActivity().orElseThrow();
    assertEquals(expected, added);
  }

  /**
   * Test case ID: TC-48.
   * Requirements: F011.
   * @author Johannes Rosengren
   */
  @Test
  public void getActivity_beginCustomActivityWithInvalidInputsButThenEnterNameAndDescriptionAndInfoAndImage_shouldReturnActivity() {
    ClientController mock = mock(ClientController.class);

    Activity expected = new Activity();
    expected.setActivityName("Test name");
    expected.setActivityInstruction("Test instruction");
    expected.setActivityInfo("Test info");
    expected.setActivityImage("src/test/resources/test_image.png");

    when(mock.packageActivity("Test name", "Test instruction", "Test info",
        "src/test/resources/test_image.png"))
        .thenReturn(expected);

    IMessageProvider messageProvider = Mockito.mock(IMessageProvider.class);
    when(messageProvider.showConfirmDialog(any(), any(Object.class), anyString(), anyInt(),
        anyInt())).thenReturn(
        JOptionPane.OK_OPTION);

    CustomActivityUI ui = new CustomActivityUI(mock, messageProvider);

    doAnswer(invocation -> {
      ui.setActivityName("Test name");
      ui.setActivityInstruction("Test instruction");
      ui.setActivityInfo("Test info");
      ui.setImage("src/test/resources/test_image.png");
      return null;
    }).when(messageProvider).showMessageDialog(any(), any(Object.class), anyString(), anyInt());

    Activity added = ui.getCustomActivity().orElseThrow();
    assertEquals(expected, added);
  }

}

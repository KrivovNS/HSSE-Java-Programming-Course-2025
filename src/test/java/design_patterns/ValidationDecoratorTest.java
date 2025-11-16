package design_patterns;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ValidationDecoratorTest {

  SimpleDataService origin;
  ValidationDecorator decorator;

  @BeforeEach
  void setUp() {
    origin = new SimpleDataService();
    decorator = new ValidationDecorator(origin);
  }

  @Test
  void testFindDataByKey_ValidKey() {
    decorator.saveData("test", "data");

    Optional<String> result = decorator.findDataByKey("test");

    assertTrue(result.isPresent());
    assertEquals("data", result.get());
  }

  @Test
  void testFindDataByKey_InvalidKey() {
    assertThrows(IllegalArgumentException.class, () -> decorator.findDataByKey(""));

    assertThrows(IllegalArgumentException.class, () -> decorator.findDataByKey("   "));

    assertThrows(IllegalArgumentException.class, () -> decorator.findDataByKey(null));
  }

  @Test
  void testSaveData_ValidInput() {

    decorator.saveData("test", "data");

    Optional<String> result = origin.findDataByKey("test");
    assertTrue(result.isPresent());
    assertEquals("data", result.get());
  }

  @Test
  void testSaveData_InvalidInput() {
    assertThrows(IllegalArgumentException.class, () -> decorator.saveData("", "data"));

    assertThrows(IllegalArgumentException.class, () -> decorator.saveData("test", ""));

    assertThrows(IllegalArgumentException.class, () -> decorator.saveData(null, "data"));

    assertThrows(IllegalArgumentException.class, () -> decorator.saveData("test", null));
  }

  @Test
  void testDeleteData_ValidKey() {
    decorator.saveData("test", "data");

    boolean result = decorator.deleteData("test");

    assertTrue(result);
  }

  @Test
  void testDeleteData_InvalidKey() {
    assertThrows(IllegalArgumentException.class, () -> decorator.deleteData(""));

    assertThrows(IllegalArgumentException.class, () -> decorator.deleteData("   "));

    assertThrows(IllegalArgumentException.class, () -> decorator.deleteData(null));
  }
}
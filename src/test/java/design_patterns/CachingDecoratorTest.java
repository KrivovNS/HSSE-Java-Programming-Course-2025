package design_patterns;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CachingDecoratorTest {
  SimpleDataService origin;
  CachingDecorator decorator;

  @BeforeEach
  void setUp() {
    origin = new SimpleDataService();
    decorator = new CachingDecorator(origin);
  }

  @Test
  void testFindByKey() {
    decorator.saveData("test", "data");
    origin.saveData("test", "otherData");

    Optional<String> result1 = decorator.findDataByKey("test");
    Optional<String> result2 = origin.findDataByKey("test");

    assertTrue(result1.isPresent());
    assertTrue(result2.isPresent());

    assertNotEquals(result1, result2); // Проверка, что данные кэшируются
  }

  @Test
  void testSaveData() {
    decorator.saveData("test", "data");

    Optional<String> result1 = origin.findDataByKey("test");
    Optional<String> result2 = decorator.findDataByKey("test");

    assertTrue(result1.isPresent());
    assertTrue(result2.isPresent());
    assertEquals(result1, result2);
  }

  @Test
  void testDeleteData() {
    decorator.saveData("test", "data");

    assertTrue(decorator.deleteData("test"));

    Optional<String> result = decorator.findDataByKey("test");

    assertThrows(NoSuchElementException.class, result::get);
  }
}

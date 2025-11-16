package design_patterns;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class LoggingDecoratorTest {

  SimpleDataService origin = new SimpleDataService();
  LoggingDecorator decorator = new LoggingDecorator(origin);

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(outputStream));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
  }

  @Test
  void testFindDataByKey() {
    decorator.findDataByKey("test");
    assertTrue(outputStream.toString().contains("не удалось найти данные"));

    outputStream.reset();

    decorator.saveData("test", "data");
    decorator.findDataByKey("test");
    assertTrue(outputStream.toString().contains("данные найдены"));
  }

  @Test
  void testSaveData() {
    decorator.saveData("newKey", "newData");

    String output = outputStream.toString();

    assertTrue(output.contains("Сохранение данных по ключу: newKey"));
    assertTrue(output.contains("Данные сохранены успешно"));
  }

  @Test
  void testDeleteData() {
    decorator.saveData("deleteKey", "data");

    decorator.deleteData("deleteKey");

    String output = outputStream.toString();

    assertTrue(output.contains("Удаление данных по ключу: deleteKey"));
    assertTrue(output.contains("Удалено успешно"));
  }
}
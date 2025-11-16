package design_patterns;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class MetricableDecoratorTest {
  private MetricableDecorator decorator;

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @BeforeEach
  void setUp() {
    SimpleDataService origin = new SimpleDataService();
    decorator = new MetricableDecorator(origin);
    System.setOut(new PrintStream(outputStream));
  }

  @AfterEach
  void tearDown() {
    System.setOut(originalOut);
  }

  @Test
  void testFindDataByKey_SendsMetric() {
    decorator.saveData("test", "data");

    decorator.findDataByKey("test");

    String output = outputStream.toString();
    assertTrue(output.contains("Метод выполнялся: PT"));
  }

  @Test
  void testSaveData_SendsMetric() {
    decorator.saveData("test", "data");

    String output = outputStream.toString();
    assertTrue(output.contains("Метод выполнялся: PT"));
  }

  @Test
  void testDeleteData_SendsMetric() {
    decorator.saveData("test", "data");

    decorator.deleteData("test");

    String output = outputStream.toString();
    assertTrue(output.contains("Метод выполнялся: PT"));
  }
}
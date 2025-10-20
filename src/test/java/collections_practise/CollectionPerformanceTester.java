package collections_practise;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Тестер производительности для сравнения ArrayList и LinkedList
 */
public class CollectionPerformanceTester {

  private static final int ELEMENT_COUNT = 10_000;

  @Test
  void testCollectionPerformance() {
    System.out.println("Сравнение производительности ArrayList и LinkedList");
    System.out.println("Количество элементов: " + ELEMENT_COUNT);
    System.out.println();

    System.out.printf("%-30s %-20s %-20s%n", "Операция", "ArrayList (мс)", "LinkedList (мс)");
    System.out.println("------------------------------------------------------------------");

    testAddToEnd();
    testAddToBeginning();
    testInsertInMiddle();
    testRandomAccess();
    testRemoveFromBeginning();
    testRemoveFromEnd();
  }

  /**
   * Тестирование добавления в конец
   */
  private void testAddToEnd() {
    List<Integer> arrayList = new ArrayList<>();
    long startTime = System.nanoTime();

    for (int i = 0; i < ELEMENT_COUNT; i++) {
      arrayList.add(i);
    }

    double arrayListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    List<Integer> linkedList = new LinkedList<>();
    startTime = System.nanoTime();

    for (int i = 0; i < ELEMENT_COUNT; i++) {
      linkedList.add(i);
    }

    double linkedListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    System.out.printf("%-30s %-20.3f %-20.3f%n", "Добавление в конец", arrayListTime, linkedListTime);
  }

  /**
   * Тестирование добавления в начало
   */
  private void testAddToBeginning() {
    List<Integer> arrayList = new ArrayList<>();
    long startTime = System.nanoTime();

    for (int i = 0; i < ELEMENT_COUNT; i++) {
      arrayList.add(0, i);
    }

    double arrayListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    List<Integer> linkedList = new LinkedList<>();
    startTime = System.nanoTime();

    for (int i = 0; i < ELEMENT_COUNT; i++) {
      linkedList.add(0, i);
    }

    double linkedListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    System.out.printf("%-30s %-20.3f %-20.3f%n", "Добавление в начало", arrayListTime, linkedListTime);
  }

  /**
   * Тестирование вставки в середину
   */
  private void testInsertInMiddle() {
    // Тестируем вставку 1000 элементов в середину для скорости
    int insertCount = 10000;

    List<Integer> arrayList = new ArrayList<>();
    // Предварительно заполняем
    for (int i = 0; i < insertCount; i++) {
      arrayList.add(i);
    }

    long startTime = System.nanoTime();

    for (int i = 0; i < insertCount; i++) {
      arrayList.add(arrayList.size() / 2, i + insertCount);
    }

    double arrayListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    List<Integer> linkedList = new LinkedList<>();
    // Предварительно заполняем
    for (int i = 0; i < insertCount; i++) {
      linkedList.add(i);
    }

    startTime = System.nanoTime();

    for (int i = 0; i < insertCount; i++) {
      linkedList.add(linkedList.size() / 2, i + insertCount);
    }

    double linkedListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    System.out.printf("%-30s %-20.3f %-20.3f%n", "Вставка в середину", arrayListTime, linkedListTime);
  }

  /**
   * Тестирование доступа по индексу (случайный доступ)
   */
  private void testRandomAccess() {
    List<Integer> arrayList = new ArrayList<>();
    for (int i = 0; i < ELEMENT_COUNT; i++) {
      arrayList.add(i);
    }

    long startTime = System.nanoTime();

    for (int i = 0; i < ELEMENT_COUNT; i++) {
      arrayList.get(i);
    }

    double arrayListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    List<Integer> linkedList = new LinkedList<>();
    for (int i = 0; i < ELEMENT_COUNT; i++) {
      linkedList.add(i);
    }

    startTime = System.nanoTime();

    for (int i = 0; i < ELEMENT_COUNT; i++) {
      linkedList.get(i);
    }

    double linkedListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    System.out.printf("%-30s %-20.3f %-20.3f%n", "Доступ по индексу", arrayListTime, linkedListTime);
  }

  /**
   * Тестирование удаления из начала
   */
  private void testRemoveFromBeginning() {
    List<Integer> arrayList = new ArrayList<>();
    for (int i = 0; i < ELEMENT_COUNT; i++) {
      arrayList.add(i);
    }

    long startTime = System.nanoTime();

    while (!arrayList.isEmpty()) {
      arrayList.remove(0);
    }

    double arrayListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    List<Integer> linkedList = new LinkedList<>();
    for (int i = 0; i < ELEMENT_COUNT; i++) {
      linkedList.add(i);
    }

    startTime = System.nanoTime();

    while (!linkedList.isEmpty()) {
      linkedList.remove(0);
    }

    double linkedListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    System.out.printf("%-30s %-20.3f %-20.3f%n", "Удаление из начала", arrayListTime, linkedListTime);
  }

  /**
   * Тестирование удаления из конца
   */
  private void testRemoveFromEnd() {
    List<Integer> arrayList = new ArrayList<>();
    for (int i = 0; i < ELEMENT_COUNT; i++) {
      arrayList.add(i);
    }

    long startTime = System.nanoTime();

    while (!arrayList.isEmpty()) {
      arrayList.remove(arrayList.size() - 1);
    }

    double arrayListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    List<Integer> linkedList = new LinkedList<>();
    for (int i = 0; i < ELEMENT_COUNT; i++) {
      linkedList.add(i);
    }

    startTime = System.nanoTime();

    while (!linkedList.isEmpty()) {
      linkedList.remove(linkedList.size() - 1);
    }

    double linkedListTime = (System.nanoTime() - startTime) / 1_000_000.0;

    System.out.printf("%-30s %-20.3f %-20.3f%n", "Удаление из конца", arrayListTime, linkedListTime);
  }
}
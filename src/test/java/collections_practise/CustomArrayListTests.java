package collections_practise;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Тестовый класс для проверки корректности методов CustomArrayList.
 */
public class CustomArrayListTests {

  private CustomArrayList<String> list;

  @BeforeEach
  void setUp() {
    list = new CustomArrayList<>();
  }

  @Test
  void testAddMethodIncreasesSize() {
    list.add("A");
    assertEquals(1, list.size());

    list.add("B");
    assertEquals(2, list.size());
  }

  @Test
  void testAddMethodStoresElements() {
    list.add("First");
    list.add("Second");

    assertEquals("First", list.get(0));
    assertEquals("Second", list.get(1));
  }

  @Test
  void testAddMethodRejectsNull() {
    assertThrows(IllegalArgumentException.class, () -> list.add(null));
  }

  @Test
  void testGetMethodReturnsCorrectElement() {
    list.add("A");
    list.add("B");
    list.add("C");

    assertEquals("A", list.get(0));
    assertEquals("B", list.get(1));
    assertEquals("C", list.get(2));
  }

  @Test
  void testGetMethodThrowsExceptionForInvalidIndex() {
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));

    list.add("A");
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
  }

  @Test
  void testRemoveMethodReturnsCorrectElement() {
    list.add("A");
    list.add("B");
    list.add("C");

    assertEquals("B", list.remove(1));
    assertEquals("A", list.remove(0));
    assertEquals("C", list.remove(0));
  }

  @Test
  void testRemoveMethodDecreasesSize() {
    list.add("A");
    list.add("B");
    list.add("C");

    list.remove(1);
    assertEquals(2, list.size());

    list.remove(0);
    assertEquals(1, list.size());

    list.remove(0);
    assertEquals(0, list.size());
  }

  @Test
  void testRemoveMethodThrowsExceptionForInvalidIndex() {
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));

    list.add("A");
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
  }

  @Test
  void testSizeMethodReturnsCorrectValue() {
    assertEquals(0, list.size());

    list.add("A");
    assertEquals(1, list.size());

    list.add("B");
    assertEquals(2, list.size());

    list.remove(0);
    assertEquals(1, list.size());

    list.remove(0);
    assertEquals(0, list.size());
  }

  @Test
  void testIsEmptyMethodReturnsCorrectValue() {
    assertTrue(list.isEmpty());

    list.add("A");
    assertFalse(list.isEmpty());

    list.remove(0);
    assertTrue(list.isEmpty());
  }

  @Test
  void testIteratorHasNextMethod() {
    Iterator<String> iterator = list.iterator();
    assertFalse(iterator.hasNext());

    list.add("A");
    iterator = list.iterator();
    assertTrue(iterator.hasNext());

    iterator.next();
    assertFalse(iterator.hasNext());
  }

  @Test
  void testIteratorNextMethodReturnsElements() {
    list.add("A");
    list.add("B");
    list.add("C");

    Iterator<String> iterator = list.iterator();
    assertEquals("A", iterator.next());
    assertEquals("B", iterator.next());
    assertEquals("C", iterator.next());
  }

  @Test
  void testIteratorNextMethodThrowsExceptionWhenNoElements() {
    Iterator<String> iterator = list.iterator();
    assertThrows(NoSuchElementException.class, iterator::next);

    list.add("A");
    iterator = list.iterator();
    iterator.next();
    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  void testIteratorRemoveMethodRemovesElement() {
    list.add("A");
    list.add("B");
    list.add("C");

    Iterator<String> iterator = list.iterator();
    iterator.next(); // A
    iterator.next(); // B
    iterator.remove(); // Удаляем B

    assertEquals(2, list.size());
    assertEquals("A", list.get(0));
    assertEquals("C", list.get(1));
  }

  @Test
  void testIteratorRemoveMethodThrowsExceptionWhenNoNextCalled() {
    list.add("A");
    Iterator<String> iterator = list.iterator();

    assertThrows(IllegalStateException.class, iterator::remove);
  }

  @Test
  void testIteratorRemoveMethodThrowsExceptionWhenCalledTwice() {
    list.add("A");
    list.add("B");

    Iterator<String> iterator = list.iterator();
    iterator.next();
    iterator.remove();

    assertThrows(IllegalStateException.class, iterator::remove);
  }
}
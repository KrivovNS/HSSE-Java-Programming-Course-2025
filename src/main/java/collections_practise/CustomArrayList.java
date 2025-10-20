package collections_practise;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Реализация динамического массива с возможностью автоматического расширения. Поддерживает основные
 * операции работы со списком и итерацию по элементам.
 *
 * @param <A> тип элементов в списке
 */
public class CustomArrayList<A> implements CustomList<A> {

  /**
   * Стандартная начальная емкость списка.
   */
  private static final int DEFAULT_CAPACITY = 10;

  /**
   * Коэффициент расширения массива при достижении предела емкости.
   */
  private static final double COEFFICIENT_OF_EXPANSION = 1.5;

  /**
   * Внутренний массив для хранения элементов списка.
   */
  private Object[] array;

  /**
   * Текущее количество элементов в списке.
   */
  private int size;

  /**
   * Текущая емкость внутреннего массива.
   */
  private int capacity;

  /**
   * Создает пустой список со стандартной начальной емкостью.
   */
  public CustomArrayList() {
    this.capacity = DEFAULT_CAPACITY;
    this.size = 0;
    this.array = new Object[DEFAULT_CAPACITY];
  }

  /**
   * Создает пустой список с указанной начальной емкостью.
   *
   * @param initialCapacity начальная емкость списка
   * @throws IllegalArgumentException если initialCapacity отрицательное
   */
  public CustomArrayList(int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("Initial capacity can`t be negative: " + initialCapacity);
    }
    this.capacity = initialCapacity;
    this.array = new Object[initialCapacity];
    this.size = 0;
  }

  /**
   * Возвращает элемент по указанному индексу в списке.
   *
   * @param index индекс возвращаемого элемента
   * @return элемент по указанному индексу
   * @throws IndexOutOfBoundsException если индекс выходит за границы списка
   */
  @Override
  @SuppressWarnings("unchecked")
  public A get(int index) {
    checkIndex(index);
    return (A) array[index];
  }

  /**
   * Добавляет указанный элемент в конец списка. Список автоматически расширяется при
   * необходимости.
   *
   * @param element элемент для добавления в список
   * @throws IllegalArgumentException если element равен null
   */
  @Override
  public void add(A element) {
    if (element == null) {
      throw new IllegalArgumentException("Element can`t be null");
    }
    ensureCapacity();
    array[size] = element;
    size++;
  }

  /**
   * Удаляет элемент по указанному индексу из списка. Все последующие элементы сдвигаются влево.
   *
   * @param index индекс удаляемого элемента
   * @return удаленный элемент
   * @throws IndexOutOfBoundsException если индекс выходит за границы списка
   */
  @Override
  @SuppressWarnings("unchecked")
  public A remove(int index) {
    checkIndex(index);

    Object removedValue = array[index];

    for (int i = index; i < size - 1; i++) {
      array[i] = array[i + 1];
    }

    array[size - 1] = null;
    size--;

    return (A) removedValue;
  }

  /**
   * Возвращает количество элементов в списке.
   *
   * @return количество элементов в списке
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Проверяет, пуст ли список.
   *
   * @return true если список не содержит элементов, иначе false
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Возвращает итератор для последовательного обхода элементов списка.
   *
   * @return итератор для элементов списка
   */
  public Iterator<A> iterator() {
    return new CustomArrayListIterator();
  }

  /**
   * Проверяет корректность индекса.
   *
   * @param index проверяемый индекс
   * @throws IndexOutOfBoundsException если индекс выходит за границы списка
   */
  private void checkIndex(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(
          "Index: " + index + "is out of range(" + size + ")");
    }
  }

  /**
   * Обеспечивает достаточную емкость внутреннего массива. Если текущий массив заполнен, создается
   * новый массив большего размера с коэффициентом расширения {@value #COEFFICIENT_OF_EXPANSION}.
   */
  private void ensureCapacity() {
    if (size == capacity) {
      int newCapacity = (int) (capacity * COEFFICIENT_OF_EXPANSION);
      if (capacity == 0) {
        newCapacity = 1;
      }
      array = Arrays.copyOf(array, newCapacity);
      capacity = newCapacity;
    }
  }

  /**
   * Итератор для последовательного обхода элементов CustomArrayList. Поддерживает операцию удаления
   * текущего элемента.
   */
  public class CustomArrayListIterator implements Iterator<A> {

    /**
     * Индекс следующего элемента для возврата.
     */
    int cursor;

    /**
     * Индекс последнего возвращенного элемента; -1 если такого нет.
     */
    int lastRet = -1;

    /**
     * Проверяет наличие следующего элемента в списке.
     *
     * @return true если в списке есть следующий элемент, иначе false
     */
    @Override
    public boolean hasNext() {
      return cursor != size;
    }

    /**
     * Возвращает следующий элемент в списке.
     *
     * @return следующий элемент в списке
     * @throws NoSuchElementException если в списке больше нет элементов
     */
    @Override
    @SuppressWarnings("unchecked")
    public A next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      int i = cursor;
      cursor = i + 1;
      return (A) array[lastRet = i];
    }

    /**
     * Удаляет из списка последний элемент, возвращенный этим итератором.
     *
     * @throws IllegalStateException если метод next еще не был вызван, или remove уже был вызван
     *                               после последнего вызова next
     */
    @Override
    public void remove() {
      if (lastRet < 0) {
        throw new IllegalStateException();
      }

      CustomArrayList.this.remove(lastRet);
      cursor = lastRet;
      lastRet = -1;
    }
  }
}
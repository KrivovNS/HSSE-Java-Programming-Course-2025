package generic_practise;

public class Pair<K, V> {

  K key;
  V value;

  public Pair(K objK, V objV) {
    this.key = objK;
    this.value = objV;
  }

  public K getKey() {
    return this.key;
  }

  public V getValue() {
    return this.value;
  }

  public void setKey(K objK) {
    this.key = objK;
  }

  public void setValue(V objV) {
    this.value = objV;
  }

  public Pair<V, K> swap() {
    return new Pair<>(value, key);
  }

  @Override
  public String toString() {
    String keyToString = (key == null) ? ("null") : key.toString();
    String valueToString = (value == null) ? ("null") : value.toString();
    return String.format("Pair{key=%s, value=%s}", keyToString, valueToString);
  }

  public static void main(String[] args) {
    // пример использования
    final Pair<String, Integer> pair = new Pair<>("Age", 25);
    System.out.println(pair); // Ожидаем: Pair{key=Age, value=25}
    final Pair<Integer, String> swapped = pair.swap();
    System.out.println(swapped); // Ожидаем: Pair{key=25, value=Age}
  }
}
package design_patterns;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class CachingDecorator implements DataService {
  DataService origin;
  Map<String, String> cache;

  public CachingDecorator(DataService original) {
    this.origin = original;
    this.cache = new HashMap<>();
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    if (cache.containsKey(key)) {
      return Optional.ofNullable(cache.get(key));
    }

    Optional<String> result = origin.findDataByKey(key);

    result.ifPresent(value -> cache.put(key, value));

    return result;
  }

  @Override
  public void saveData(String key, String data) {
    origin.saveData(key, data);

    cache.put(key, data);
  }

  @Override
  public boolean deleteData(String key) {
    boolean wasDeleted = origin.deleteData(key);

    cache.remove(key);
    return wasDeleted;
  }
}
package design_patterns;

import java.util.Optional;

class ValidationDecorator implements DataService {
  DataService origin;

  public ValidationDecorator(DataService original) {
    this.origin = original;
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    if (key == null || key.trim().isEmpty()) {
      throw new IllegalArgumentException("Ключ не может быть пустым");
    }

    return origin.findDataByKey(key);
  }

  @Override
  public void saveData(String key, String data) {
    if (key == null || key.trim().isEmpty()) {
      throw new IllegalArgumentException("Ключ не может быть пустым");
    }

    if (data == null || data.trim().isEmpty()) {
      throw new IllegalArgumentException("Данные не могут быть пустыми");
    }

    origin.saveData(key, data);
  }

  @Override
  public boolean deleteData(String key) {
    if (key == null || key.trim().isEmpty()) {
      throw new IllegalArgumentException("Ключ не может быть пустым");
    }

    return origin.deleteData(key);
  }
}
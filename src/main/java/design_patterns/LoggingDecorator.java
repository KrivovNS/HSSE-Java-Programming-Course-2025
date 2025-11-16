package design_patterns;

import java.util.Optional;

class LoggingDecorator implements DataService {
  DataService origin;

  public LoggingDecorator(DataService original) {
    this.origin = original;

  }

  @Override
  public Optional<String> findDataByKey(String key) {
    Optional<String> result = origin.findDataByKey(key);

    System.out.println("Поиск данных по ключу: " + key);

    System.out.println("Результаты поиска: " + (result.isPresent() ? "данные найдены" : "не удалось найти данные"));

    return result;
  }

  @Override
  public void saveData(String key, String data) {
    System.out.println("Сохранение данных по ключу: " + key);

    origin.saveData(key, data);

    System.out.println("Данные сохранены успешно");
  }

  @Override
  public boolean deleteData(String key) {
    System.out.println("Удаление данных по ключу: " + key);

    boolean wasDeleted = origin.deleteData(key);

    System.out.println(wasDeleted ? "Удалено успешно" : "Не удалось удалить данные");

    return wasDeleted;
  }
}
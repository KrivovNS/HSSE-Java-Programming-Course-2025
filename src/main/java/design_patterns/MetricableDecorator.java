package design_patterns;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

class MetricableDecorator implements DataService {
  DataService origin;
  MetricService metricService;

  public MetricableDecorator (DataService original) {
    this.origin = original;
    this.metricService = new MetricService();
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    Instant startTime = Instant.now();
    Optional<String> result = origin.findDataByKey(key);
    Instant endTime = Instant.now();
    metricService.sendMetric(Duration.between(startTime, endTime));
    return result;
  }

  @Override
  public void saveData(String key, String data) {
    Instant startTime = Instant.now();
    origin.saveData(key, data);
    Instant endTime = Instant.now();
    metricService.sendMetric(Duration.between(startTime, endTime));
  }

  @Override
  public boolean deleteData(String key) {
    Instant startTime = Instant.now();
    boolean wasDeleted = origin.deleteData(key);
    Instant endTime = Instant.now();
    metricService.sendMetric(Duration.between(startTime, endTime));
    return wasDeleted;
  }

  public static class MetricService {
    public void sendMetric(Duration duration) {
      System.out.println("Метод выполнялся: " + duration.toString());
    }
  }
}

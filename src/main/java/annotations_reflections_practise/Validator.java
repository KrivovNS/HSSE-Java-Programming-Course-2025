package annotations_reflections_practise;

import annotations_reflections_practise.annotations.*;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class Validator {

  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,}$");

  public static ValidationResult validate(Object object) {
    ValidationResult result = new ValidationResult();

    if (object == null) {
      result.addError("object не может быть null");
      return result;
    }

    final Class<?> objectClass = object.getClass();

    Field[] fields = objectClass.getDeclaredFields();

    for (Field field : fields) {
      field.setAccessible(true);
      validateField(object, field, result);
    }

    return result;
  }

  private static void validateField(Object object, Field field, ValidationResult result) {
    try {
      Object value = field.get(object);

      if (field.isAnnotationPresent(NotNull.class)) {
        validateNotNull(value, field, result);
      }

      if (value == null) {
        return;
      }

      if (field.isAnnotationPresent(Size.class)) {
        if (value instanceof String) {
          validateSize((String) value, field, result);
        } else {
          result.addError("@Size применяется только к полям типа String");
        }
      }

      if (field.isAnnotationPresent(Range.class)) {
        if (value instanceof Number) {
          validateRange(value, field, result);
        } else {
          result.addError("@Range применяется к полям числового типа");
        }
      }

      if (field.isAnnotationPresent(Email.class)) {
        if (value instanceof String) {
          validateEmail((String) value, field, result);
        } else {
          result.addError("@Email применяется к полям типа String");
        }
      }

    } catch (Exception e) {
      result.addError("Непредвиденная ошибка при валидации поля: " + field.getName());
    }
  }

  private static void validateNotNull(Object value, Field field, ValidationResult result) {
    NotNull annotation = field.getAnnotation(NotNull.class);

    if (value == null) {
      result.addError(annotation.message());
    }
  }

  private static void validateSize(String value, Field field, ValidationResult result) {
    Size annotation = field.getAnnotation(Size.class);
    if (annotation.min() > value.length() || annotation.max() < value.length()) {
      String message = annotation.message().replace("{min}", String.valueOf(annotation.min()))
          .replace("{max}", String.valueOf(annotation.max()));
      result.addError(message);
    }
  }

  private static void validateRange(Object value, Field field, ValidationResult result) {
    Range annotation = field.getAnnotation(Range.class);

    if (annotation.min() > ((Number) value).longValue() || annotation.max() < ((Number) value).longValue()) {
      String message = annotation.message().replace("{min}", String.valueOf(annotation.min()))
          .replace("{max}", String.valueOf(annotation.max()));
      result.addError(message);
    }
  }

  private static void validateEmail(String value, Field field, ValidationResult result) {
    Email annotation = field.getAnnotation(Email.class);

    if (!EMAIL_PATTERN.matcher(value).matches()) {
      result.addError(annotation.message());
    }
  }
}

package annotations_reflections_practise;

import annotations_reflections_practise.annotations.*;
import annotations_reflections_practise.examples.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidationTest {

  private User validUser;

  @BeforeEach
  void setUp() {
    validUser = new User(
        "Иван Иванов",
        "ivan@example.com",
        25,
        "Это корректное описание пользователя с достаточным количеством символов"
    );
  }

  @Test
  void testValidUser() {
    ValidationResult result = Validator.validate(validUser);
    assertTrue(result.isValid());
    assertEquals(0, result.getErrors().size());
  }

  @Test
  void testNullName() {
    User user = new User(null, "test@example.com", 25, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("name не может быть null"));
  }

  @Test
  void testShortName() {
    User user = new User("А", "test@example.com", 25, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("Длина имени должна быть от 2 до 50 символов"));
  }

  @Test
  void testLongName() {
    StringBuilder longNameBuilder = new StringBuilder();
    for (int i = 0; i < 51; i++) {
      longNameBuilder.append("А");
    }
    User user = new User(longNameBuilder.toString(), "test@example.com", 25, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("Длина имени должна быть от 2 до 50 символов"));
  }

  @Test
  void testNameBoundaryMin() {
    User user = new User("Ив", "test@example.com", 25, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertTrue(result.isValid());
  }

  @Test
  void testNameBoundaryMax() {
    StringBuilder maxNameBuilder = new StringBuilder();
    for (int i = 0; i < 50; i++) {
      maxNameBuilder.append("И");
    }
    User user = new User(maxNameBuilder.toString(), "test@example.com", 25, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertTrue(result.isValid());
  }

  @Test
  void testNullEmail() {
    User user = new User("Иван", null, 25, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("email не может быть null"));
  }

  @Test
  void testInvalidEmail() {
    User user = new User("Иван", "Иван@example.com", 25, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("email должен быть валидным"));
  }

  @Test
  void testValidEmails() {
    String[] validEmails = {
        "user@example.com",
        "user.name@example.com",
        "user+tag@example.org",
        "user@example.co.uk"
    };

    for (String email : validEmails) {
      User user = new User("Иван", email, 25, "Валидное описание");
      ValidationResult result = Validator.validate(user);
      assertTrue(result.isValid());
    }
  }

  @Test
  void testAgeBelowMin() {
    User user = new User("Иван", "test@example.com", 6, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("Возраст должен быть в диапазоне от 7 до 100 лет"));
  }

  @Test
  void testAgeAboveMax() {
    User user = new User("Иван", "test@example.com", 101, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("Возраст должен быть в диапазоне от 7 до 100 лет"));
  }

  @Test
  void testAgeBoundaryMin() {
    User user = new User("Иван", "test@example.com", 7, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertTrue(result.isValid());
  }

  @Test
  void testAgeBoundaryMax() {
    User user = new User("Иван", "test@example.com", 100, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertTrue(result.isValid());
  }

  @Test
  void testNullAge() {
    User user = new User("Иван", "test@example.com", null, "Валидное описание");
    ValidationResult result = Validator.validate(user);
    assertTrue(result.isValid());
  }

  @Test
  void testShortDescription() {
    User user = new User("Иван", "test@example.com", 25, "А");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("Описание должно содержать от 2 до 300 символов"));
  }

  @Test
  void testLongDescription() {
    StringBuilder longDescBuilder = new StringBuilder();
    for (int i = 0; i < 301; i++) {
      longDescBuilder.append("О");
    }
    User user = new User("Иван", "test@example.com", 25, longDescBuilder.toString());
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("Описание должно содержать от 2 до 300 символов"));
  }

  @Test
  void testDescriptionBoundaryMin() {
    User user = new User("Иван", "test@example.com", 25, "Оп");
    ValidationResult result = Validator.validate(user);
    assertTrue(result.isValid());
  }

  @Test
  void testDescriptionBoundaryMax() {
    StringBuilder maxDescBuilder = new StringBuilder();
    for (int i = 0; i < 300; i++) {
      maxDescBuilder.append("О");
    }
    User user = new User("Иван", "test@example.com", 25, maxDescBuilder.toString());
    ValidationResult result = Validator.validate(user);
    assertTrue(result.isValid());
  }

  @Test
  void testNullDescription() {
    User user = new User("Иван", "test@example.com", 25, null);
    ValidationResult result = Validator.validate(user);
    assertTrue(result.isValid());
  }

  @Test
  void testMultipleErrors() {
    User user = new User(null, "неправильный-email", 5, "К");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertEquals(4, result.getErrors().size());
  }

  @Test
  void testEmptyUser() {
    User user = new User();
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("name не может быть null"));
    assertTrue(result.getErrors().contains("email не может быть null"));
  }
}

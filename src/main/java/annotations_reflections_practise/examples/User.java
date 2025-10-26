package annotations_reflections_practise.examples;

import annotations_reflections_practise.annotations.*;

public class User {

  @NotNull(message = "name не может быть null")
  @Size(min = 2, max = 50, message = "Длина имени должна быть от 2 до 50 символов")
  private String name;

  @NotNull(message = "email не может быть null")
  @Email(message = "email должен быть валидным")
  private String email;

  @Range(min = 7, max = 100, message = "Возраст должен быть в диапазоне от 7 до 100 лет")
  private Integer age;

  @Size(min = 2, max = 300, message = "Описание должно содержать от 2 до 300 символов")
  private String description;

  public User() {
  }

  public User(String name, String email, Integer age, String description) {
    this.name = name;
    this.email = email;
    this.age = age;
    this.description = description;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
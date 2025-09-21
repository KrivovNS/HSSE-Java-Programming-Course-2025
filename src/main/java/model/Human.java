package model;

public class Human {

  private String name;
  private String secondName;
  private int age;
  private boolean isEmployed;

  public String getName() {
    return name;
  }

  public String getSecondName() {
    return secondName;
  }

  public int getAge() {
    return age;
  }

  public boolean getIsEmployed() {
    return isEmployed;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setIsEmployed(boolean isEmployed) {
    this.isEmployed = isEmployed;
  }
}

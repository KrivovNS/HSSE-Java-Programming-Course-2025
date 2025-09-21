package com.mipt.nikitakrivov;

public class MainClass {

  private int intBox;
  private String stringBox;
  protected static double doubleBox;
  public final long longBox = 1234567890;

  public static void main(String[] args) {
    for (int i = 0; i < 15; i++) {
      System.out.println("Iter: " + i);
    }
  }
}

interface Students {
  Object study(Object object);
}

abstract class Workers {
  public abstract void work(int someInt);

  public boolean goHome(String str1, String str2) {
    return str1.equals(str2);
  }
}
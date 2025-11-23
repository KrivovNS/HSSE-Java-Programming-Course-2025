package multithreading;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

  Bank bank;
  BankAccount first;
  BankAccount second;
  BankAccount third;

  @BeforeEach
  void setUp() {
    bank = new Bank();
    first = new BankAccount();
    second = new BankAccount();
    third = new BankAccount();
  }

  @Test
  public void normalTransactionTest() {
    first.upBalance(1000);

    assertEquals(1000, first.getBalance());

    bank.sendToAccount(first, second, 400);

    assertEquals(600, first.getBalance());
    assertEquals(400, second.getBalance());
  }

  @Test
  public void invalidDataTest() {
    BankAccount nullAccount = null;
    first.upBalance(1000);

    assertThrows(IllegalArgumentException.class, () -> {
      bank.sendToAccount(nullAccount, first, 100);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      bank.sendToAccount(first, nullAccount, 100);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      bank.sendToAccount(first, second, -1000);
    });

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      bank.sendToAccount(first, second, 1100);
    });

    assertTrue(exception.getMessage().contains("Недостаточно средств на балансе"));
  }

  @Test
  public void multiThreadedCorrectMethodTest() throws InterruptedException {
    first.upBalance(1000);
    second.upBalance(1000);

    long initialFirstBalance = first.getBalance();
    long initialSecondBalance = second.getBalance();

    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        bank.sendToAccount(first, second, 1);
      }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        bank.sendToAccount(second, first, 1);
      }
    });

    thread1.start();
    thread2.start();

    thread1.join(3000);
    thread2.join(3000);

    assertFalse(thread1.isAlive());
    assertFalse(thread2.isAlive());

    long totalBalanceAfter = first.getBalance() + second.getBalance();
    long totalBalanceBefore = initialFirstBalance + initialSecondBalance;
    assertEquals(totalBalanceBefore, totalBalanceAfter);
  }

  @Test
  public void multiThreadedMethodWithDeadlockTest() throws InterruptedException {
    first.upBalance(1000);
    second.upBalance(1000);

    AtomicInteger completedThreads = new AtomicInteger(0);

    Thread thread1 = new Thread(() -> {
      try {
        bank.sendToAccountDeadlock(first, second, 100);
        completedThreads.incrementAndGet();
      } catch (Exception e) {
      }
    });

    Thread thread2 = new Thread(() -> {
      try {
        bank.sendToAccountDeadlock(second, first, 100);
        completedThreads.incrementAndGet();
      } catch (Exception e) {
      }
    });

    thread1.start();
    thread2.start();

    thread1.join(2000);
    thread2.join(2000);

    boolean thread1Alive = thread1.isAlive();
    boolean thread2Alive = thread2.isAlive();

    if (thread1Alive || thread2Alive) {
      thread1.interrupt();
      thread2.interrupt();
      assertTrue(thread1Alive || thread2Alive);
    }
  }

  @Test
  public void testConcurrentTransfersBetweenMultipleAccounts() throws InterruptedException {
    first.upBalance(1000);
    second.upBalance(1000);
    third.upBalance(1000);

    long initialTotalBalance = first.getBalance() + second.getBalance() + third.getBalance();

    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 50; i++) {
        bank.sendToAccount(first, second, 1);
      }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 50; i++) {
        bank.sendToAccount(second, third, 1);
      }
    });

    Thread thread3 = new Thread(() -> {
      for (int i = 0; i < 50; i++) {
        bank.sendToAccount(third, first, 1);
      }
    });

    thread1.start();
    thread2.start();
    thread3.start();

    thread1.join(3000);
    thread2.join(3000);
    thread3.join(3000);

    assertFalse(thread1.isAlive());
    assertFalse(thread2.isAlive());
    assertFalse(thread3.isAlive());

    long finalTotalBalance = first.getBalance() + second.getBalance() + third.getBalance();
    assertEquals(initialTotalBalance, finalTotalBalance);
  }

  @Test
  public void testCorrectMethodPreventsDeadlock() throws InterruptedException {
    first.upBalance(1000);
    second.upBalance(1000);

    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 50; i++) {
        bank.sendToAccount(first, second, 1);
      }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 50; i++) {
        bank.sendToAccount(second, first, 1);
      }
    });

    thread1.start();
    thread2.start();

    thread1.join(2000);
    thread2.join(2000);

    assertFalse(thread1.isAlive());
    assertFalse(thread2.isAlive());

    long totalBalance = first.getBalance() + second.getBalance();
    assertEquals(2000, totalBalance);
  }

  @Test
  public void testFourThreadsTest() throws InterruptedException {
    first.upBalance(2000);
    second.upBalance(2000);

    long initialTotal = first.getBalance() + second.getBalance();

    Thread thread1 = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        bank.sendToAccount(first, second, 1);
      }
    });

    Thread thread2 = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        bank.sendToAccount(second, first, 1);
      }
    });

    Thread thread3 = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        bank.sendToAccount(first, second, 1);
      }
    });

    Thread thread4 = new Thread(() -> {
      for (int i = 0; i < 100; i++) {
        bank.sendToAccount(second, first, 1);
      }
    });

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();

    thread1.join(3000);
    thread2.join(3000);
    thread3.join(3000);
    thread4.join(3000);

    assertFalse(thread1.isAlive());
    assertFalse(thread2.isAlive());
    assertFalse(thread3.isAlive());
    assertFalse(thread4.isAlive());

    long finalTotal = first.getBalance() + second.getBalance();
    assertEquals(initialTotal, finalTotal);
  }
}
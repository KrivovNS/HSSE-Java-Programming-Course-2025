package multithreading;

import java.util.UUID;

public class BankAccount {

  private long balance;
  private final UUID id;

  public BankAccount() {
    balance = 0L;
    this.id = UUID.randomUUID();
  }

  public void upBalance(long toAdd) {
    balance += toAdd;
  }

  public void debitFromBalance(long toDebit) {
    balance -= toDebit;
  }

  public long getBalance() {
    return balance;
  }

  public UUID getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Аккаунт: " + id + ", ,баланс: " + balance;
  }
}

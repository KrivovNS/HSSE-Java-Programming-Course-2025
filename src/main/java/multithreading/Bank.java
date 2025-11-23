package multithreading;

public class Bank {

  public void sendToAccountDeadlock(BankAccount from, BankAccount to, long amount) {
    if (amount < 0 || from == null || to == null) {
      throw new IllegalArgumentException("Переданы невалидные данные");
    }

    synchronized (from) {
      synchronized (to) {
        if (from.getBalance() >= amount) {
          from.debitFromBalance(amount);
          to.upBalance(amount);
          System.out.println(
              "Перевод в размере: " + amount + " руб\nCо счета: " + from.getId() + "\nНа счет: "
                  + to.getId() + "\nУспешно выполнен.");
        } else {
          throw new IllegalArgumentException("Недостаточно средств на балансе: " + from.getId());
        }
      }
    }
  }

  public void sendToAccount(BankAccount from, BankAccount to, long amount) {
    if (amount < 0 || from == null || to == null) {
      throw new IllegalArgumentException("Переданы невалидные данные");
    }

    BankAccount first;
    BankAccount second;

    if (from.getId().compareTo(to.getId()) > 0) {
      first = from;
      second = to;
    } else {
      first = to;
      second = from;
    }

    synchronized (first) {
      synchronized (second) {
        if (from.getBalance() >= amount) {
          from.debitFromBalance(amount);
          to.upBalance(amount);
          System.out.println(
              "Перевод в размере: " + amount + " руб\nCо счета: " + from.getId() + "\nНа счет: "
                  + to.getId() + "\nУспешно выполнен.");
        } else {
          throw new IllegalArgumentException("Недостаточно средств на балансе: " + from.getId());
        }
      }
    }
  }
}

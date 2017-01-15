package entity;


public class Account {
    private int balance;
    public Account(int initialBalance) {
        this.balance = initialBalance;
    }

    // Skal besvare om kontoen indeholder et givent beløb
    public boolean doesContain(int amount){
        return balance >= amount;
    }
    // Indsæt på kontoen
    public void deposit(int amount) {
        this.balance += amount;
    }
    // Træk fra kontoen
    public void withdraw(int amount) { this.balance -= amount; }

    // Hent kontoens balance (hvad står der på kontoen)
    public int getBalance() {
        return this.balance;
    }
}

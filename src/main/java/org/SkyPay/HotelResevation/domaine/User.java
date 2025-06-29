package org.SkyPay.HotelResevation.domaine;

public class User {
    private final int id;
    private int balance;

    public User(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void deductBalance(int amount) {
        if (amount > balance) throw new IllegalArgumentException("Insufficient balance.");
        balance -= amount;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", balance=" + balance + "}";
    }
}

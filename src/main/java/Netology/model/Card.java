package Netology.model;

import Netology.errors.DataError;
import Netology.repository.Repository;

import java.util.Calendar;

public class Card {
    private final String number;
    private final String validTill;
    private final String cvv;
    private Amount balance;

    public Card(String number, String validTill, String cvv, Amount balance) {
        this.number = number;
        this.validTill = validTill;
        this.cvv = cvv;
        this.balance = balance;
    }


    public String getNumber() {
        return number;
    }

    public String getValidTill() {
        return validTill;
    }

    public String getCvv() {
        return cvv;
    }

    public Amount getBalance() {
        return balance;
    }

    public void setBalance(Amount balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number='" + number + '\'' +
                ", validTill='" + validTill + '\'' +
                ", cvv='" + cvv + '\'' +
                ", balance=" + balance +
                '}';
    }

}

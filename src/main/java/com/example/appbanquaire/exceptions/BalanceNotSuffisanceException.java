package com.example.appbanquaire.exceptions;

public class BalanceNotSuffisanceException extends Throwable {
    public BalanceNotSuffisanceException(String balanceNotSufficient) {
        super(balanceNotSufficient);
    }
}

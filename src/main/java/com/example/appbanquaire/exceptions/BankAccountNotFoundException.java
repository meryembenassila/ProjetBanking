package com.example.appbanquaire.exceptions;

public class BankAccountNotFoundException extends Exception {

    public BankAccountNotFoundException(String accountNotFound) {
        super(accountNotFound);
    }
}

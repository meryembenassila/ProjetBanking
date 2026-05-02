package com.example.appbanquaire.exceptions;

public class CustomerNotFoundException extends Exception {//nous devons faire throws dans la methode ou on a l'exeption
    public CustomerNotFoundException(String customerNotFound) {
        super(customerNotFound);
    }
}

package com.example.appbanquaire.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@DiscriminatorValue("SA")
public class SavingAccount extends BankAccount{
    private double interestRate;
}

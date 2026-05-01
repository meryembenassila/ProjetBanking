package com.example.appbanquaire.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity @AllArgsConstructor @NoArgsConstructor @Data
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Date date;
    private double amount;
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;

}

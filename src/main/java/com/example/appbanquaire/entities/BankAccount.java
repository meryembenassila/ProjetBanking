package com.example.appbanquaire.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class  BankAccount {//pour ne pas etre instancier
    @Id @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id ;
    private Date createdAt;
    private double balance;
    private AccountStatus status;
    private String currency;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperation> operations;

}

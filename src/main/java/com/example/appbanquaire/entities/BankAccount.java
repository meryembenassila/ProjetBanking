package com.example.appbanquaire.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE",length = 4)
@AllArgsConstructor
@NoArgsConstructor
@Data

public abstract class  BankAccount {//pour ne pas etre instancier
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Date createdAt;
    private double balance;
    @Enumerated(EnumType.STRING) //pour stocker enum sous forme de string
    private AccountStatus status;
    private String currency;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount",fetch = FetchType.EAGER)
    private List<AccountOperation> operations;


}

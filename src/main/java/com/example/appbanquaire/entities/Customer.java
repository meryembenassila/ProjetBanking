package com.example.appbanquaire.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity  @AllArgsConstructor @NoArgsConstructor
@Data

public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;


    @OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
    private List<BankAccount> bankAccounts;
}

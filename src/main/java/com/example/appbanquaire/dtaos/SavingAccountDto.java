package com.example.appbanquaire.dtaos;

import com.example.appbanquaire.entities.AccountStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data

public  class SavingAccountDto extends BankAccountDto{ //!!!!!!!!attention abstract

    private Long id ;
    private Date createdAt;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
   private CustomerDTO customerDTO; //on peut avoir juste le nom un string c'est selon l'utilsation dans la partie web
    private double interestRate;



}

package com.example.appbanquaire.dtaos;

import com.example.appbanquaire.entities.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;


@Data

public  class  CurrentAccountDto extends BankAccountDto{//pour ne pas etre instancier

    private Long id ;
    private Date createdAt;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double overDraft;




}

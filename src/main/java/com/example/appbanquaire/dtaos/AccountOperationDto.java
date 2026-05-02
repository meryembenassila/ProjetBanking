package com.example.appbanquaire.dtaos;

import com.example.appbanquaire.entities.OperationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDto {
    private Long id ;
    private Date date;
    private double amount;
    private String description;
    @Enumerated(EnumType.STRING)
    private OperationType type;

}

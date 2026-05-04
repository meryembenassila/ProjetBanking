package com.example.appbanquaire.dtaos;


import lombok.Data;

@Data
public class Virement {
    Long accountSource;
    Long accountDestination;
    double amount;
}

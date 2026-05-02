package com.example.appbanquaire.dtaos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDto {
    private double balance ;
    private Long accountId;
    private List<AccountOperationDto> accountOperationDtos;
    private  int currentPage;
    private int pageSize;
    private int totalPages;
}

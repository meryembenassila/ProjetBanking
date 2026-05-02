package com.example.appbanquaire.web;


import com.example.appbanquaire.dtaos.*;
import com.example.appbanquaire.entities.BankAccount;
import com.example.appbanquaire.exceptions.BankAccountNotFoundException;
import com.example.appbanquaire.exceptions.CustomerNotFoundException;
import com.example.appbanquaire.mappers.BankAccountMapperIMpl;
import com.example.appbanquaire.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class BankRestController {
    private BankAccountService bankAccountService;


    @GetMapping("/Accounts")
    public List<BankAccountDto> gettAccounts(){
        return bankAccountService.gettAccounts();
    }

    @GetMapping("/Accounts/{id}")
    public BankAccountDto gettAccount(@PathVariable Long id) throws BankAccountNotFoundException {
        return bankAccountService.getAccount(id);
    }

    @PostMapping("/SavingAccounts")
    public SavingAccountDto saveSavingAccount(@RequestBody SavingAccountDto savingAccountDto) throws CustomerNotFoundException {
        return bankAccountService.savesavingaccount(savingAccountDto.getBalance(), savingAccountDto.getInterestRate(), savingAccountDto.getCustomerDTO().getId());
    }

    @PostMapping("/CurrentAccounts")
    public CurrentAccountDto saveCurrentAccount(@RequestBody CurrentAccountDto currentAccountDto) throws CustomerNotFoundException {
        return bankAccountService.savecurrentaccount(currentAccountDto.getBalance(), currentAccountDto.getOverDraft(), currentAccountDto.getCustomerDTO().getId());
    }


    @GetMapping("/Accounts/{accountId}/operations")
    public List<AccountOperationDto> gettAccounOperations(@PathVariable Long accountId){
        return bankAccountService.gettOperationofbankAccount(accountId);

    }

    @GetMapping("/Accounts/{accountId}/pageOperations")
    public AccountHistoryDto gettAccounHistory
            (@PathVariable Long accountId,
             @RequestParam(name="page" ,defaultValue = "0")int page ,
             @RequestParam(name="size" ,defaultValue = "0")int size
    ) throws BankAccountNotFoundException {

  return bankAccountService.getAccountHistory(accountId,page,size);
    }





    @PostMapping("/Account/{accountId}/debiter")
    public void debiter(@PathVariable Long accountId,@RequestBody OperationRequestDto operationRequestDto) throws BankAccountNotFoundException {
        bankAccountService.credit(accountId,operationRequestDto.getAmount(),operationRequestDto.getDescription());

    }

    @PostMapping("/Account/{accountId}/crediter")
    public void crediter(@PathVariable Long accountId,@RequestBody OperationRequestDto operationRequestDto) throws BankAccountNotFoundException {
        bankAccountService.credit(accountId,operationRequestDto.getAmount(),operationRequestDto.getDescription());

    }




}

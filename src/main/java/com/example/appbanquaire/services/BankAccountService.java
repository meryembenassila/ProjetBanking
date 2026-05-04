package com.example.appbanquaire.services;

import com.example.appbanquaire.dtaos.*;
import com.example.appbanquaire.exceptions.BalanceNotSuffisanceException;
import com.example.appbanquaire.exceptions.BankAccountNotFoundException;
import com.example.appbanquaire.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    //pas besoin de faire les methodes public dans une interface


    CustomerDTO saveCustommer(CustomerDTO customerDTO);

    List<CustomerDTO> getCustomers();

    CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerNotFoundException;

    void deleteCustomer(Long customerId);

    List<CustomerDTO> search(String motif);

    CurrentAccountDto savecurrentaccount(double initbalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccountDto savesavingaccount(double initbalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    BankAccountDto getAccount(Long accountId) throws BankAccountNotFoundException;
    void credit(Long accounId, double amount , String description) throws BankAccountNotFoundException;
    void debit(Long accounId, double amount , String description) throws BankAccountNotFoundException, BalanceNotSuffisanceException;
    void transfert(Long accounIdSource, Long accounIDestination,double amount) throws BankAccountNotFoundException, BalanceNotSuffisanceException;
    List<BankAccountDto> gettAccounts();


    CustomerDTO getCustomer(Long customerid) throws CustomerNotFoundException;

    List<AccountOperationDto> gettOperationofbankAccount(Long accountId);

    AccountHistoryDto getAccountHistory(Long accountId, int page, int size) throws BankAccountNotFoundException;
}

package com.example.appbanquaire.services;

import com.example.appbanquaire.entities.BankAccount;
import com.example.appbanquaire.entities.CurrentAccount;
import com.example.appbanquaire.entities.Customer;
import com.example.appbanquaire.entities.SavingAccount;
import com.example.appbanquaire.exceptions.BalanceNotSuffisanceException;
import com.example.appbanquaire.exceptions.BankAccountNotFoundException;
import com.example.appbanquaire.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    //pas besoin de faire les methodes public dans une interface
    Customer saveCustommer(Customer customer);
    List<Customer> getCustomers();
    CurrentAccount savecurrentaccount(double initbalance,double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount savesavingaccount(double initbalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    BankAccount getAccount(Long accountId) throws BankAccountNotFoundException;
    void credit(Long accounId, double amount , String description) throws BankAccountNotFoundException;
    void debit(Long accounId, double amount , String description) throws BankAccountNotFoundException, BalanceNotSuffisanceException;
    void transfert(Long accounIdSource, Long accounIDestination,double amount) throws BankAccountNotFoundException, BalanceNotSuffisanceException;
    List<BankAccount> gettAccounts();



}

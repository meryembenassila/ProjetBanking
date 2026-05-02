package com.example.appbanquaire.services;

import com.example.appbanquaire.entities.*;
import com.example.appbanquaire.exceptions.BalanceNotSuffisanceException;
import com.example.appbanquaire.exceptions.BankAccountNotFoundException;
import com.example.appbanquaire.exceptions.CustomerNotFoundException;
import com.example.appbanquaire.repositories.AccountOperationRepository;
import com.example.appbanquaire.repositories.BanAccountRepository;
import com.example.appbanquaire.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//on peut faire l'injection des dependance avec AllArgsConstructor
@Service
@AllArgsConstructor
@Transactional
@Slf4j //pour les log
public class BanAccountServiceImp implements BankAccountService{
    private CustomerRepository customerRepository;
    private BanAccountRepository banAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    @Override
    public Customer saveCustommer(Customer customer) {
        Customer newCustomer = customerRepository.save(customer);
        return newCustomer;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public CurrentAccount savecurrentaccount(double initbalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        CurrentAccount currentAccount = new CurrentAccount();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            //throw new RuntimeException();//simplement
            throw  new CustomerNotFoundException("CustomerNot found");
        }

        currentAccount.setBalance(initbalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(overDraft);
        banAccountRepository.save(currentAccount);
        return currentAccount;
    }

    @Override
        public SavingAccount savesavingaccount(double initbalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        SavingAccount savingAccount = new SavingAccount();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            //throw new RuntimeException();//simplement
            throw  new CustomerNotFoundException("CustomerNot found");
        }

        savingAccount.setBalance(initbalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        banAccountRepository.save(savingAccount);
        return savingAccount;
    }



    @Override
    public BankAccount getAccount(Long accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = banAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null) throw new BankAccountNotFoundException("Account Not Found");
        return bankAccount;
    }

    @Override
    public void credit(Long accounId, double amount, String description) throws BankAccountNotFoundException {
        BankAccount bankAccount = getAccount(accounId);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setDate(new Date());
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);

    }

    @Override
    public void debit(Long accounId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuffisanceException {
        BankAccount bankAccount = getAccount(accounId);
        if(bankAccount.getBalance()<amount)  throw new BalanceNotSuffisanceException("Balance not Sufficient");
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setDate(new Date());
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperationRepository.save(accountOperation);


    }

    @Override
    public void transfert(Long accounIdSource, Long accounIDestination,double amount) throws BankAccountNotFoundException, BalanceNotSuffisanceException {
        debit(accounIdSource,amount,"Transfert to " + accounIDestination);
        credit(accounIDestination,amount,"Transfert from + "+accounIDestination);


    }

    @Override
    public List<BankAccount> gettAccounts() {
        return banAccountRepository.findAll();
    }

}

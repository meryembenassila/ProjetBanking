package com.example.appbanquaire.services;

import com.example.appbanquaire.dtaos.*;
import com.example.appbanquaire.entities.*;
import com.example.appbanquaire.exceptions.BalanceNotSuffisanceException;
import com.example.appbanquaire.exceptions.BankAccountNotFoundException;
import com.example.appbanquaire.exceptions.CustomerNotFoundException;
import com.example.appbanquaire.mappers.BankAccountMapperIMpl;
import com.example.appbanquaire.repositories.AccountOperationRepository;
import com.example.appbanquaire.repositories.BanAccountRepository;
import com.example.appbanquaire.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
   private BankAccountMapperIMpl bankAccountMapperIMpl;

    @Override
    public CustomerDTO saveCustommer(CustomerDTO customerDTO) {
        Customer customer = bankAccountMapperIMpl.fromCustomerDTO(customerDTO);
        Customer save = customerRepository.save(customer);

        return bankAccountMapperIMpl.fromCustomer(save);
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerRepository.findAll().forEach(customer -> {
            customerDTOS.add(bankAccountMapperIMpl.fromCustomer(customer));

                }
        );

        return customerDTOS;
    }

    @Override
    public CustomerDTO getCustomer(Long customerid) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerid).orElse(null);
        if (customer == null) throw new CustomerNotFoundException("Customer Not Found");
        CustomerDTO customerDTO = bankAccountMapperIMpl.fromCustomer(customer);

        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO)  {
        Customer customer = bankAccountMapperIMpl.fromCustomerDTO(customerDTO);
        Customer save = customerRepository.save(customer);
        return bankAccountMapperIMpl.fromCustomer(save);
    }

    @Override
    public void deleteCustomer(Long customerId) {
       customerRepository.deleteById(customerId);

    }

/// ///////////////////////////Gestion des Comptes///////////////////////////
    @Override
    public CurrentAccountDto savecurrentaccount(double initbalance, double overDraft, Long customerId) throws CustomerNotFoundException {
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


        return bankAccountMapperIMpl.fromCurrentAccount(currentAccount) ;
    }




    @Override
        public SavingAccountDto savesavingaccount(double initbalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        SavingAccount savingAccount = new SavingAccount();
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if(customer == null){
            //throw new RuntimeException();//simplement
            throw  new CustomerNotFoundException("CustomerNot found");
        }
        savingAccount.setStatus(AccountStatus.CREATED);

        savingAccount.setBalance(initbalance);
        savingAccount.setCreatedAt(new Date());
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(interestRate);
        banAccountRepository.save(savingAccount);
        return bankAccountMapperIMpl.fromSavingAccount(savingAccount);
    }



    @Override
    public BankAccountDto getAccount(Long accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = banAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null) throw new BankAccountNotFoundException("Account Not Found");
        if(bankAccount instanceof SavingAccount){
           return bankAccountMapperIMpl.fromSavingAccount((SavingAccount) bankAccount);
        }else{
           return  bankAccountMapperIMpl.fromCurrentAccount((CurrentAccount) bankAccount);
        }

    }

    @Override
    public void credit(Long accounId, double amount, String description) throws BankAccountNotFoundException {
    //c'est pas tres partiqe d'utilser getaccount car il retourne DTO puis faire le mappers
        BankAccount bankAccount = banAccountRepository.findById(accounId).orElse(null);
        if(bankAccount == null) throw new BankAccountNotFoundException("Account Not Found");
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

        BankAccount bankAccount = banAccountRepository.findById(accounId).orElse(null);
        if(bankAccount == null) throw new BankAccountNotFoundException("Account Not Found");
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
    public List<BankAccountDto> gettAccounts() {
        List<BankAccountDto> bankAccountDtos =new ArrayList<>();
        banAccountRepository.findAll().forEach(
                bankAccount -> {
                    if(bankAccount instanceof SavingAccount){
                        bankAccountDtos.add(bankAccountMapperIMpl.fromSavingAccount((SavingAccount) bankAccount));
                    }else{
                        bankAccountDtos.add(bankAccountMapperIMpl.fromCurrentAccount((CurrentAccount) bankAccount));
                    }


                }
        );

        return bankAccountDtos;
    }

    @Override
    public List<AccountOperationDto> gettOperationofbankAccount(Long accountId){
        List<AccountOperationDto>  accountOperationDtos= new ArrayList<>();

        for (AccountOperation accountOperation : accountOperationRepository.findByBankAccount_Id(accountId)) {
            accountOperationDtos.add(bankAccountMapperIMpl.fromAccountOperation(accountOperation));
        }

        return accountOperationDtos;

    }

    @Override
    public AccountHistoryDto getAccountHistory(Long accountId, int page, int size) throws BankAccountNotFoundException {

        Page<AccountOperation> accountoperation = accountOperationRepository.findByBankAccount_Id(accountId, PageRequest.of(page, size));
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        //////!!!!!!!
        List<AccountOperationDto> accountOperationDtos= new ArrayList<>();
        for (AccountOperation accountOperation : accountoperation.getContent()) {
            accountOperationDtos.add(bankAccountMapperIMpl.fromAccountOperation(accountOperation));
        }

        accountHistoryDto.setAccountOperationDtos(accountOperationDtos);
        BankAccount bankAccount = banAccountRepository.findById(accountId).orElse(null);
        if (bankAccount == null) throw new  BankAccountNotFoundException("not fount Account");
        accountHistoryDto.setAccountId(bankAccount.getId());
        accountHistoryDto.setBalance(bankAccount.getBalance());
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPages(accountoperation.getTotalPages());

        return accountHistoryDto;
    };

    ;




}

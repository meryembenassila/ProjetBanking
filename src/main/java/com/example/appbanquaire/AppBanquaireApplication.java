package com.example.appbanquaire;

import com.example.appbanquaire.entities.*;
import com.example.appbanquaire.exceptions.BalanceNotSuffisanceException;
import com.example.appbanquaire.exceptions.BankAccountNotFoundException;
import com.example.appbanquaire.exceptions.CustomerNotFoundException;
import com.example.appbanquaire.repositories.AccountOperationRepository;
import com.example.appbanquaire.repositories.BanAccountRepository;
import com.example.appbanquaire.repositories.CustomerRepository;
import com.example.appbanquaire.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class AppBanquaireApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppBanquaireApplication.class, args);

    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BanAccountRepository banAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args ->{
            Stream<String> noms = Stream.of("Hassan", "Yassine", "Aicha");
            noms.forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);

                for (Customer customer1 : customerRepository.findAll()) {
                    CurrentAccount currentAccount = new CurrentAccount();
                    currentAccount.setCustomer(customer1);
                    currentAccount.setCreatedAt(new Date());
                    currentAccount.setStatus(AccountStatus.CREATED);
                    currentAccount.setBalance(Math.random()*90000);
                    currentAccount.setOverDraft(9000);
                    banAccountRepository.save(currentAccount);
                    SavingAccount savingAccount = new SavingAccount();
                    savingAccount.setCustomer(customer1);
                    savingAccount.setCreatedAt(new Date());
                    savingAccount.setStatus(AccountStatus.CREATED);
                    savingAccount.setBalance(Math.random()*90000);
                    savingAccount.setInterestRate(5.5);
                    banAccountRepository.save(savingAccount);

                }
                for(BankAccount bankaccount :banAccountRepository.findAll()){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setAmount(Math.random()*300);
                    accountOperation.setDate(new Date());
                    accountOperation.setBankAccount(bankaccount);
                    accountOperation.setType(Math.random()>0.5?OperationType.CREDIT:OperationType.DEBIT);
                    accountOperationRepository.save(accountOperation);

                }

                BankAccount bankAccount = banAccountRepository.findById(1L).get();
                System.out.println("*****************************");
                System.out.println(bankAccount.getCreatedAt());
                System.out.println(bankAccount.getBalance());
                System.out.println(bankAccount.getStatus());
                System.out.println(bankAccount.getCustomer().getName());
                //afficher type de compte
                System.out.println(bankAccount.getClass().getSimpleName());
                if(bankAccount instanceof CurrentAccount){
                    System.out.println("CurrentAccount :"+((CurrentAccount) bankAccount).getOverDraft());
                }else{
                    System.out.println("SavingAccount :"+((SavingAccount) bankAccount).getInterestRate());
                }
                bankAccount.getOperations().forEach(accountOperation -> {
                    System.out.println(accountOperation.getType());
                    System.out.println(accountOperation.getDate());
                    System.out.println(accountOperation.getAmount());

                });

            });


        };
    }

    @Bean
    CommandLineRunner start(BankAccountService bankAccountService) {
        return args -> {
            Stream<String> noms = Stream.of("Hassan", "Yassine", "Aicha");
            noms.forEach(name-> {

                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustommer(customer);
            });

                bankAccountService.getCustomers().forEach(customer1 -> {

                    try {
                        bankAccountService.savecurrentaccount(Math.random()*1200,Math.random()*200,customer1.getId());
                        bankAccountService.savesavingaccount(Math.random()*1200,Math.random()*200,customer1.getId());
                    } catch (CustomerNotFoundException e) {
                        e.printStackTrace();
                    }




                });
            bankAccountService.gettAccounts().forEach(bankAccount -> {
                for (int i = 0 ; i<10 ; i++){
                    System.out.println("cc");
                    try {

                        bankAccountService.credit(bankAccount.getId(),Math.random()*100,"Credit");
                        bankAccountService.debit(bankAccount.getId(),Math.random()*100,"Debit");
                    } catch (BankAccountNotFoundException|BalanceNotSuffisanceException e) {
                        e.printStackTrace();

                    }


                }

            });


    };}                }






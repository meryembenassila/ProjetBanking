package com.example.appbanquaire.mappers;


import com.example.appbanquaire.dtaos.AccountOperationDto;
import com.example.appbanquaire.dtaos.CurrentAccountDto;
import com.example.appbanquaire.dtaos.CustomerDTO;
import com.example.appbanquaire.dtaos.SavingAccountDto;
import com.example.appbanquaire.entities.AccountOperation;
import com.example.appbanquaire.entities.CurrentAccount;
import com.example.appbanquaire.entities.Customer;
import com.example.appbanquaire.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperIMpl {


    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer = new Customer();
        //customer.setId(customerDTO.getId());
        //customer.setName(customer.getName());
        //customer.setEmail(customerDTO.getEmail());
        BeanUtils.copyProperties(customerDTO,customer);
    return customer;
    }

    public CustomerDTO fromCustomer(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
        return customerDTO;
    }

    public SavingAccount fromSavingAccountDto(SavingAccountDto savingAccountDto){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDto,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingAccountDto.getCustomerDTO()));
        return  savingAccount;
    }

    public SavingAccountDto fromSavingAccount (SavingAccount savingAccount){
        SavingAccountDto savingAccountDto = new SavingAccountDto();
        BeanUtils.copyProperties(savingAccount,savingAccountDto);
        savingAccountDto.setType(savingAccount.getClass().getSimpleName());
       savingAccountDto.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
        return  savingAccountDto;
    }


    public CurrentAccount fromCurrentAccountDto(CurrentAccountDto currentAccountDto){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto,currentAccount);
        ///!!!!
        currentAccount.setCustomer(fromCustomerDTO(currentAccountDto.getCustomerDTO()));
        return  currentAccount;
    }

    public CurrentAccountDto fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccount,currentAccountDto);
        currentAccountDto.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        //!!!!!!!!!!!!!!!!!!!!pour utilser le type de compte dans la partie Ui
        currentAccountDto.setType(currentAccount.getClass().getSimpleName());
        return  currentAccountDto;
    }


    public AccountOperation fromAccountOperationDto(AccountOperationDto accountOperationDto){
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDto,accountOperation);
        return  accountOperation;

    }

    public  AccountOperationDto fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation,accountOperationDto);
        return accountOperationDto;
    }

}

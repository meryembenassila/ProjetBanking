package com.example.appbanquaire.web;


import com.example.appbanquaire.dtaos.CustomerDTO;
import com.example.appbanquaire.entities.BankAccount;
import com.example.appbanquaire.entities.Customer;
import com.example.appbanquaire.exceptions.CustomerNotFoundException;
import com.example.appbanquaire.mappers.BankAccountMapperIMpl;
import com.example.appbanquaire.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor //l'injection des dependances
public class CustomerController {
    private final BankAccountMapperIMpl bankAccountMapperIMpl;
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.getCustomers();
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(id);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO request){//requestBody transforme json qui dans le corps pas dasn l'url de la requete en un objet
        return bankAccountService.saveCustommer(request);
    }

    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id ,@RequestBody CustomerDTO request) throws CustomerNotFoundException {//requestBody transforme json qui dans le corps pas dasn l'url de la requete en un objet
        request.setId(id);
        return bankAccountService.updateCustomer(request);
    }

    @DeleteMapping("/customers/delete/{id}")
    public void delete(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }

}

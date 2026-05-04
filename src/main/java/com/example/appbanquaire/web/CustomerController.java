package com.example.appbanquaire.web;


import com.example.appbanquaire.dtaos.CustomerDTO;
import com.example.appbanquaire.entities.BankAccount;
import com.example.appbanquaire.entities.Customer;
import com.example.appbanquaire.exceptions.CustomerNotFoundException;
import com.example.appbanquaire.mappers.BankAccountMapperIMpl;
import com.example.appbanquaire.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor //l'injection des dependances
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {
    private final BankAccountMapperIMpl bankAccountMapperIMpl;
    private BankAccountService bankAccountService;


    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_USER')")//dans notre cas on a score dans les authorités
    public List<CustomerDTO> customers(){
        return bankAccountService.getCustomers();
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(id);
    }


    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO request){//requestBody transforme json qui dans le corps pas dasn l'url de la requete en un objet
        return bankAccountService.saveCustommer(request);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/customers/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id ,@RequestBody CustomerDTO request) throws CustomerNotFoundException {//requestBody transforme json qui dans le corps pas dasn l'url de la requete en un objet
        request.setId(id);
        return bankAccountService.updateCustomer(request);
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/customers/delete/{id}")
    public void delete(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }


    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/customers/search")
    public List<CustomerDTO> search(@RequestParam (name = "keyword" ,defaultValue = "")String keyword){
        return bankAccountService.search(keyword);

    }


}


package com.example.appbanquaire.repositories;

import com.example.appbanquaire.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}

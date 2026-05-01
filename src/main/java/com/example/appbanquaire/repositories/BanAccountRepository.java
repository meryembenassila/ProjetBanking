package com.example.appbanquaire.repositories;

import com.example.appbanquaire.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanAccountRepository extends JpaRepository<BankAccount,Long> {
}

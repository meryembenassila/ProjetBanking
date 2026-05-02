package com.example.appbanquaire.repositories;

import com.example.appbanquaire.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccount_Id(Long banKAccountId);
    Page<AccountOperation> findByBankAccount_Id(Long banKAccountId, Pageable pageable);

}

package com.pet.bank.repository;

import com.pet.bank.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {

    List<BankAccount> findAllBankAccountsByOwnerId(UUID userId);

    BankAccount findBankAccountById(UUID bankAccountId);

}

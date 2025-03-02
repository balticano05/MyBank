package com.pet.bank.repository;


import com.pet.bank.dto.mapper.TransactionMapper;
import com.pet.bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    List<Transaction> findAllTransactionsByFromAccountId(UUID bankAccountId);

    Transaction findByTransactionId(UUID transactionId);

}

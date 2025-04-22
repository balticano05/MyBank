package com.pet.bank.repository;

import com.pet.bank.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment, UUID> {
}

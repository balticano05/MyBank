package com.pet.bank.repository;

import com.pet.bank.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

    List<Loan> findAllLoansByUserId(UUID userId);

    Loan findByLoanId(UUID loanId);

}

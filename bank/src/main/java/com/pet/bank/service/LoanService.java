package com.pet.bank.service;

import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.response.loan.LoanFullResponseDto;

import java.util.UUID;

public interface LoanService {

    AllUserLoansResponseDto findAllLoansByUserId(UUID userId);

    LoanFullResponseDto findLoanById(UUID loanId);

}

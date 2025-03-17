package com.pet.bank.service;

import com.pet.bank.dto.request.RepayLoanRequestDto;
import com.pet.bank.dto.request.loan.LoanCreationRequestDto;
import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.response.loan.LoanCreationResponseDto;
import com.pet.bank.dto.response.loan.LoanFullResponseDto;

import java.util.UUID;

public interface LoanService {

    AllUserLoansResponseDto findAllLoansByUserId(UUID userId);

    LoanFullResponseDto findLoanById(UUID loanId);

    LoanCreationResponseDto createLoanForUser(UUID userId, UUID bankId, LoanCreationRequestDto loanRequest);

    void repayForLoan(UUID loanId, RepayLoanRequestDto repayLoanRequest);

}
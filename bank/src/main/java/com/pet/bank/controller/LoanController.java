package com.pet.bank.controller;

import com.pet.bank.dto.request.RepayLoanRequestDto;
import com.pet.bank.dto.request.loan.LoanCreationRequestDto;
import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.response.loan.LoanCreationResponseDto;
import com.pet.bank.dto.response.loan.LoanFullResponseDto;
import com.pet.bank.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public AllUserLoansResponseDto findAllLoansByUserId(@PathVariable UUID userId) {
        return loanService.findAllLoansByUserId(userId);
    }

    @GetMapping("/{loanId}")
    public LoanFullResponseDto findLoanById(@PathVariable UUID loanId) {
        return loanService.findLoanById(loanId);
    }

    @PostMapping("/{bankId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public LoanCreationResponseDto createLoanForUser(
            @PathVariable UUID userId,
            @PathVariable UUID bankId,
            @RequestBody @Valid LoanCreationRequestDto loanRequest) {
        return loanService.createLoanForUser(userId, bankId, loanRequest);
    }

    @PostMapping("/{loanId}/repayments")
    public void repayForLoan(@PathVariable UUID loanId,
                             @RequestBody @Valid RepayLoanRequestDto repayLoanRequest) {
        loanService.repayForLoan(loanId, repayLoanRequest);
    }

}
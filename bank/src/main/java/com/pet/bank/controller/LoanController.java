package com.pet.bank.controller;

import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.response.loan.LoanFullResponseDto;
import com.pet.bank.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public AllUserLoansResponseDto findAllLoansByUserId(@PathVariable UUID userId){
        return loanService.findAllLoansByUserId(userId);
    }

    @GetMapping("/{loanId}")
    public LoanFullResponseDto findLoanById(@PathVariable UUID loanId){
        return loanService.findLoanById(loanId);
    }

}

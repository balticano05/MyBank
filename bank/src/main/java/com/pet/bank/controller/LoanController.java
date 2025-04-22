package com.pet.bank.controller;

import com.pet.bank.dto.request.RepayLoanRequestDto;
import com.pet.bank.dto.request.loan.LoanCreationRequestDto;
import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.response.loan.LoanCreationResponseDto;
import com.pet.bank.dto.response.loan.LoanFullResponseDto;
import com.pet.bank.service.LinkService;
import com.pet.bank.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class LoanController {

    private final LinkService linkService;
    private final LoanService loanService;

    @GetMapping("/{userId}/loans")
    public AllUserLoansResponseDto findAllLoansByUserId(@PathVariable UUID userId) {
        AllUserLoansResponseDto allUserLoansResponseDto = loanService.findAllLoansByUserId(userId);
        return linkService.addLinksToAllUserLoansResponseDto(allUserLoansResponseDto, userId);
    }

    @GetMapping("/{userId}/loans/{loanId}")
    public LoanFullResponseDto findLoanById(@PathVariable UUID loanId) {
        return loanService.findLoanById(loanId);
    }

    @PostMapping("/{userId}/loans/{bankId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public LoanCreationResponseDto createLoanForUser(
            @PathVariable UUID userId,
            @PathVariable UUID bankId,
            @RequestBody @Valid LoanCreationRequestDto loanRequest) {
        return loanService.createLoanForUser(userId, bankId, loanRequest);
    }

    @PostMapping("/{userId}/loans/{loanId}/repayments")
    public void repayForLoan(@PathVariable UUID loanId,
                             @RequestBody @Valid RepayLoanRequestDto repayLoanRequest) {
        loanService.repayForLoan(loanId, repayLoanRequest);
    }

}
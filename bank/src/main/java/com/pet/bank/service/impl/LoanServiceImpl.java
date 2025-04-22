package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.LoanMapper;
import com.pet.bank.dto.request.RepayLoanRequestDto;
import com.pet.bank.dto.request.loan.LoanCreationRequestDto;
import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.response.loan.LoanCreationResponseDto;
import com.pet.bank.dto.response.loan.LoanFullResponseDto;
import com.pet.bank.entity.BankAccount;
import com.pet.bank.entity.Loan;
import com.pet.bank.entity.LoanPayment;
import com.pet.bank.entity.User;
import com.pet.bank.entity.enums.LoanPaymentStatus;
import com.pet.bank.entity.enums.LoanStatus;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.exception.type.BadRequestException;
import com.pet.bank.repository.*;
import com.pet.bank.service.LoanService;
import com.pet.bank.utils.validator.FieldValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final LoanPaymentRepository loanPaymentRepository;
    private final CurrencyRepository currencyRepository;
    private final BankAccountRepository bankAccountRepository;
    private final DataValidationService dataValidationService;

    @Override
    public AllUserLoansResponseDto findAllLoansByUserId(UUID userId) {
        return LoanMapper.mapEntitiesToAllUserLoansResponseDto(loanRepository.findAllLoansByUserId(userId));
    }

    public LoanFullResponseDto findLoanById(UUID loanId) {
        dataValidationService.existsLoanById(loanId, HttpStatus.NOT_FOUND);

        return LoanMapper.mapEntityToFullResponseDto(loanRepository.findLoanById(loanId));
    }

    @Override
    @Transactional
    public LoanCreationResponseDto createLoanForUser(UUID userId, UUID bankId, LoanCreationRequestDto loanRequest) {

        dataValidationService.existsUserById(userId, HttpStatus.NOT_FOUND);
        dataValidationService.existsBankAccountById(bankId, HttpStatus.NOT_FOUND);
        dataValidationService.existsCurrencyByCode(loanRequest.getCurrencyCode(), HttpStatus.NOT_FOUND);

        User user = userRepository.findUserById(userId);
        BankAccount bankAccount = bankAccountRepository.findBankAccountById(bankId);

        validateLoanRequest(loanRequest);

        Loan loan = Loan.builder()
                .user(user)
                .bankAccount(bankAccount)
                .currency(currencyRepository.findCurrencyByCode(loanRequest.getCurrencyCode()))
                .amount(loanRequest.getAmount())
                .interestRate(loanRequest.getInterestRate())
                .startDate(loanRequest.getStartDate())
                .endDate(loanRequest.getEndDate())
                .status(LoanStatus.ACTIVE.getValue())
                .build();

        return LoanMapper.mapEntityToLoanCreationResponseDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public void repayForLoan(UUID loanId, RepayLoanRequestDto repayLoanRequest) {

        dataValidationService.existsLoanById(loanId, HttpStatus.NOT_FOUND);

        Loan loan = loanRepository.findLoanById(loanId);

        dataValidationService.existsBankAccountById(loan.getBankAccount().getId(), HttpStatus.NOT_FOUND);

        BankAccount bankAccount = bankAccountRepository.findBankAccountById(loan.getBankAccount().getId());

        validateRepayment(loan, repayLoanRequest, bankAccount);

        LoanPayment payment = LoanPayment.builder()
                .paymentAmount(repayLoanRequest.getAmount())
                .paymentDate(new Date())
                .loan(loan)
                .status(LoanPaymentStatus.COMPLETED.getValue())
                .build();

        loan.addLoanPayment(payment);

        loanPaymentRepository.save(payment);

        if (isLoanFullyRepaid(loan)) {
            loan.setStatus(LoanStatus.REPAID.getValue());
        }

    }

    private void validateLoanRequest(LoanCreationRequestDto loanRequest) {

        if (!FieldValidator.isNotNegative(loanRequest.getAmount())) {
            throw new BadRequestException("Amount must be positive");
        }

        if (!FieldValidator.isNotNull(loanRequest.getCurrencyCode())) {
            throw new BadRequestException("Currency is required");
        }

        if (!FieldValidator.isNotNegative(loanRequest.getInterestRate())) {
            throw new BadRequestException("Interest rate must be non-negative");
        }

        if (!FieldValidator.isNotNull(loanRequest.getStartDate())) {
            throw new BadRequestException("Start date is required");
        }

        if (!FieldValidator.isNotNull(loanRequest.getEndDate())) {
            throw new BadRequestException("End date is required");
        }

        if (loanRequest.getEndDate().before(loanRequest.getStartDate())) {
            throw new BadRequestException("End date must be after start date");
        }
    }

    private void validateRepayment(Loan loan, RepayLoanRequestDto request, BankAccount account) {

        if (!LoanStatus.ACTIVE.getValue().equals(loan.getStatus())) {
            throw new BadRequestException("Loan is not active");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Invalid payment amount");
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new BadRequestException("Insufficient funds");
        }

        if (request.getAmount().compareTo(loan.getAmount()) > 0) {
            throw new BadRequestException("Payment amount exceeds the remaining loan amount");
        }
    }

    private boolean isLoanFullyRepaid(Loan loan) {

        BigDecimal totalPaid = loan.getLoanPayments().stream()
                .map(LoanPayment::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalToRepay = loan.getAmount()
                .multiply(BigDecimal.ONE.add(loan.getInterestRate().divide(BigDecimal.valueOf(100))));

        return totalPaid.compareTo(totalToRepay) >= 0;
    }

}
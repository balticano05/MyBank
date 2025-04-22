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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

        Loan loan = null;
        LoanPayment payment = null;

        try {

            dataValidationService.existsLoanById(loanId, HttpStatus.NOT_FOUND);
            loan = loanRepository.findLoanById(loanId);
            dataValidationService.existsBankAccountById(loan.getBankAccount().getId(), HttpStatus.NOT_FOUND);
            BankAccount bankAccount = bankAccountRepository.findBankAccountById(loan.getBankAccount().getId());

            payment = LoanPayment.builder()
                    .paymentAmount(repayLoanRequest.getAmount())
                    .paymentDate(new Date())
                    .loan(loan)
                    .status(LoanPaymentStatus.FAILED.getValue())
                    .build();

            validateRepayment(loan, repayLoanRequest, bankAccount);

            payment.setStatus(LoanPaymentStatus.COMPLETED.getValue());

            BigDecimal newAmount = loan.getAmount().subtract(repayLoanRequest.getAmount());
            loan.setAmount(newAmount);

            loan.addLoanPayment(payment);
            loanPaymentRepository.save(payment);
            loanRepository.save(loan);

            if (isLoanFullyRepaid(loan)) {
                loan.setStatus(LoanStatus.REPAID.getValue());
            }

        } catch (Exception e) {

            if (payment != null) {
                payment.setStatus(LoanPaymentStatus.FAILED.getValue());
                loanPaymentRepository.save(payment);
            }
            throw new BadRequestException("Payment failed: " + e.getMessage());
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
                .filter(p -> LoanPaymentStatus.COMPLETED.getValue().equals(p.getStatus()))
                .map(LoanPayment::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long daysBetween = ChronoUnit.DAYS.between(
                loan.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                LocalDate.now()
        );
        BigDecimal years = BigDecimal.valueOf(daysBetween).divide(BigDecimal.valueOf(365), 10, RoundingMode.HALF_UP);

        BigDecimal interest = loan.getAmount()
                .multiply(loan.getInterestRate())
                .multiply(years)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

        BigDecimal totalToRepay = loan.getAmount().add(interest);

        return totalPaid.compareTo(totalToRepay) >= 0;
    }

}
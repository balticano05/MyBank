package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.LoanMapper;
import com.pet.bank.dto.request.RepayLoanRequestDto;
import com.pet.bank.dto.response.loan.AllUserLoansResponseDto;
import com.pet.bank.dto.request.loan.LoanCreationRequestDto;
import com.pet.bank.dto.response.loan.LoanCreationResponseDto;
import com.pet.bank.dto.response.loan.LoanFullResponseDto;
import com.pet.bank.entity.BankAccount;
import com.pet.bank.entity.Loan;
import com.pet.bank.entity.LoanPayment;
import com.pet.bank.entity.LoanPaymentStatus;
import com.pet.bank.entity.LoanStatus;
import com.pet.bank.entity.User;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.repository.BankAccountRepository;
import com.pet.bank.repository.CurrencyRepository;
import com.pet.bank.repository.LoanRepository;
import com.pet.bank.repository.UserRepository;
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
    private final CurrencyRepository currencyRepository;
    private final BankAccountRepository bankAccountRepository;
    private final DataValidationService dataValidationService;

    @Override
    public AllUserLoansResponseDto findAllLoansByUserId(UUID userId) {
        return LoanMapper.mapEntitiesToAllUserLoansResponseDto(loanRepository.findAllLoansByUserId(userId));
    }

    public LoanFullResponseDto findLoanById(UUID loanId){
        return LoanMapper.mapEntityToFullResponseDto(loanRepository.findByLoanId(loanId));
    }

    @Override
    @Transactional
    public LoanCreationResponseDto createLoanForUser(UUID userId, UUID bankId, LoanCreationRequestDto loanRequest) {

        dataValidationService.existsUserById(userId, HttpStatus.NOT_FOUND);
        dataValidationService.existsBankAccountById(bankId, HttpStatus.NOT_FOUND);
        dataValidationService.existsCurrencyByCode(loanRequest.getCurrency().getCode(), HttpStatus.NOT_FOUND);

        User user = userRepository.findUserById(userId);
        BankAccount bankAccount = bankAccountRepository.findBankAccountById(bankId);

        validateLoanRequest(loanRequest);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBankAccount(bankAccount);
        loan.setCurrency(currencyRepository.findCurrencyByCode(loanRequest.getCurrency().getCode()));

        loan.setAmount(loanRequest.getAmount());
        loan.setInterestRate(loanRequest.getInterestRate());
        loan.setStartDate(loanRequest.getStartDate());
        loan.setEndDate(loanRequest.getEndDate());
        loan.setStatus(LoanStatus.ACTIVE.getValue());

        return LoanMapper.mapEntityToLoanCreationResponseDto(loanRepository.save(loan));
    }

    @Override
    @Transactional
    public void repayForLoan(UUID loanId, RepayLoanRequestDto repayLoanRequest) {

        dataValidationService.existsLoanById(loanId, HttpStatus.NOT_FOUND);
        dataValidationService.existsBankAccountById(repayLoanRequest.getBankAccountId(), HttpStatus.NOT_FOUND);

        Loan loan = loanRepository.findByLoanId(loanId);
        BankAccount bankAccount = bankAccountRepository.findBankAccountById(repayLoanRequest.getBankAccountId());

        validateRepayment(loan, repayLoanRequest, bankAccount);

        LoanPayment payment = new LoanPayment();
        payment.setPaymentAmount(repayLoanRequest.getAmount());
        payment.setPaymentDate(new Date());
        payment.setLoan(loan);
        payment.setStatus(LoanPaymentStatus.COMPLETED.getValue());

        loan.getLoanPayments().add(payment);

        if(isLoanFullyRepaid(loan)){
            loan.setStatus(LoanStatus.REPAID.getValue());
        }

    }

    private void validateLoanRequest(LoanCreationRequestDto loanRequest) {

        if (!FieldValidator.isNotNull(loanRequest.getAmount()) ||
                loanRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (!FieldValidator.isNotNull(loanRequest.getCurrency())) {
            throw new IllegalArgumentException("Currency is required");
        }

        if (!FieldValidator.isNotNegative(loanRequest.getInterestRate())) {
            throw new IllegalArgumentException("Interest rate must be non-negative");
        }

        if (!FieldValidator.isNotNull(loanRequest.getStartDate())) {
            throw new IllegalArgumentException("Start date is required");
        }

        if (!FieldValidator.isNotNull(loanRequest.getEndDate())) {
            throw new IllegalArgumentException("End date is required");
        }

        if (loanRequest.getEndDate().before(loanRequest.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date");
        }
    }

    private void validateRepayment(Loan loan, RepayLoanRequestDto request, BankAccount account) {

        if (!"ACTIVE".equals(loan.getStatus())) {
            throw new IllegalStateException("Loan is not active");
        }

        if (!FieldValidator.isNotNegative(request.getAmount()) ||
                request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    private boolean isLoanFullyRepaid(Loan loan) {

        double totalPaid = loan.getLoanPayments().stream()
                .mapToDouble(payment -> payment.getPaymentAmount().doubleValue()) // Преобразуем BigDecimal в double
                .sum();

        double totalToRepay = loan.getAmount()
                .multiply(BigDecimal.ONE.add(loan.getInterestRate().divide(BigDecimal.valueOf(100))))
                .doubleValue();

        return totalPaid >= totalToRepay;
    }

}

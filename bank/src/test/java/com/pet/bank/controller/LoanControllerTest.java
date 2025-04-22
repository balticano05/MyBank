package com.pet.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.bank.dto.request.RepayLoanRequestDto;
import com.pet.bank.dto.request.loan.LoanCreationRequestDto;
import com.pet.bank.entity.*;
import com.pet.bank.entity.enums.BankAccountStatus;
import com.pet.bank.entity.enums.LoanStatus;
import com.pet.bank.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Transactional
public class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private User createUserWithCredentialsTest() {

        Credential credential = Credential.builder()
                .login("testuser")
                .email("user@gmail.com")
                .password("password")
                .build();

        credential = credentialRepository.save(credential);

        User user = User.builder()
                .firstName("Test")
                .lastName("User")
                .phoneNumber("+1234567890")
                .address("Test Address")
                .dateOfBirth(Date.valueOf(LocalDate.of(1980, 12, 28)))
                .credential(credential)
                .build();

        return userRepository.save(user);
    }

    private BankAccount createBankAccountTest(User user) {

        BankAccount account = BankAccount.builder()
                .owner(user)
                .balance(new BigDecimal("1000.00"))
                .status(BankAccountStatus.ACTIVE.getValue())
                .currency(createCurrencyTest())
                .build();

        return bankAccountRepository.save(account);
    }

    private Currency createCurrencyTest() {

        Currency currency = Currency.builder()
                .title("Dollar")
                .code("USD")
                .build();

        return currencyRepository.save(currency);
    }

    private Loan createLoanTest(User user, BankAccount bankAccount) {

        Loan loan = Loan.builder()
                .user(user)
                .bankAccount(bankAccount)
                .currency(bankAccount.getCurrency())
                .amount(BigDecimal.valueOf(5000))
                .interestRate(BigDecimal.valueOf(5))
                .startDate(new Date(System.currentTimeMillis()))
                .endDate(new Date(System.currentTimeMillis() + 1000000))
                .status(LoanStatus.ACTIVE.getValue())
                .build();


        return loanRepository.save(loan);
    }

    @Test
    @SneakyThrows
    void findAllLoansByUserIdShouldReturnLoans() {

        User user = createUserWithCredentialsTest();
        BankAccount account = createBankAccountTest(user);

        Loan loan = createLoanTest(user, account);

        mockMvc.perform(get("/api/v1/users/{userId}/loans", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.loans", hasSize(1)))
                .andExpect(jsonPath("$.loans[0].amount").value(5000));
    }

    @Test
    @SneakyThrows
    void findLoanByIdShouldReturnLoan() {

        User user = createUserWithCredentialsTest();
        BankAccount account = createBankAccountTest(user);

        Loan loan = createLoanTest(user, account);

        mockMvc.perform(get("/api/v1/users/{userId}/loans/{loanId}", user.getId(), loan.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(loan.getId().toString()));
    }

    @Test
    @SneakyThrows
    void createLoanValidRequestShouldCreateLoan() {

        User user = createUserWithCredentialsTest();
        BankAccount account = createBankAccountTest(user);

        LoanCreationRequestDto request = LoanCreationRequestDto.builder()
                .amount(BigDecimal.valueOf(3000))
                .currencyCode("USD")
                .interestRate(BigDecimal.valueOf(5))
                .startDate(Date.valueOf(LocalDate.of(2024, 12, 28)))
                .endDate(Date.valueOf(LocalDate.of(2025, 12, 28)))
                .build();

        mockMvc.perform(post("/api/v1/users/{userId}/loans/{bankId}", user.getId(), account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        assertEquals(1, loanRepository.count());
    }

    @Test
    @SneakyThrows
    void repayLoanValidRequestShouldUpdateBalance() {

        User user = createUserWithCredentialsTest();
        BankAccount account = createBankAccountTest(user);
        Loan loan = createLoanTest(user, account);

        RepayLoanRequestDto request = RepayLoanRequestDto.builder()
                .amount(new BigDecimal("1000.0"))
                .build();

        mockMvc.perform(post("/api/v1/users/{userId}/loans/{loanId}/repayments", user.getId(), loan.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        Loan updatedLoan = loanRepository.findById(loan.getId()).orElseThrow();

        assertEquals(new BigDecimal("1000.0"), updatedLoan.getLoanPayments().getFirst().getPaymentAmount());
    }

    @Test
    @SneakyThrows
    void createLoanWithInvalidDataShouldReturnBadRequest() {

        User user = createUserWithCredentialsTest();
        BankAccount account = createBankAccountTest(user);

        LoanCreationRequestDto invalidRequest = LoanCreationRequestDto.builder()
                .amount(BigDecimal.valueOf(-100))
                .currencyCode("USD")
                .interestRate(BigDecimal.valueOf(5))
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusYears(1)))
                .build();

        mockMvc.perform(post("/api/v1/users/{userId}/loans/{bankId}", user.getId(), account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void findNonExistingLoanShouldReturnNotFound() {

        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/users/{userId}/loans/{loanId}", UUID.randomUUID(), randomId))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void repayNonExistingLoanShouldReturnNotFound() {

        RepayLoanRequestDto request = RepayLoanRequestDto.builder()
                .amount(new BigDecimal("1000.00"))
                .build();

        mockMvc.perform(post("/api/v1/users/{userId}/loans/{loanId}/repayments", UUID.randomUUID(), UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createLoanWithInvalidCurrencyShouldReturnBadRequest() {

        User user = createUserWithCredentialsTest();
        BankAccount account = createBankAccountTest(user);

        LoanCreationRequestDto request = LoanCreationRequestDto.builder()
                .amount(BigDecimal.valueOf(-3000))
                .currencyCode("USD")
                .interestRate(BigDecimal.valueOf(5))
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusYears(1)))
                .build();

        mockMvc.perform(post("/api/v1/users/{userId}/loans/{bankId}", user.getId(), account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}
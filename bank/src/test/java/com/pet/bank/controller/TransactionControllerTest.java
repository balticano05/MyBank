package com.pet.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.bank.entity.BankAccount;
import com.pet.bank.entity.Credential;
import com.pet.bank.entity.Currency;
import com.pet.bank.entity.Transaction;
import com.pet.bank.entity.User;
import com.pet.bank.repository.BankAccountRepository;
import com.pet.bank.repository.CredentialRepository;
import com.pet.bank.repository.CurrencyRepository;
import com.pet.bank.repository.TransactionRepository;
import com.pet.bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Transactional
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Credential createCredentialsTest() {

        Credential credential = Credential.builder()
                .login("testuser")
                .email("user@test.com")
                .password("password")
                .build();

        return credentialRepository.save(credential);
    }

    private User createUserTest(Credential credential) {

        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("+1234567890")
                .address("Test Address")
                .dateOfBirth(Date.valueOf(LocalDate.of(1990, 1, 1)))
                .credential(credential)
                .build();

        return userRepository.save(user);
    }

    private BankAccount createBankAccountTest(User user, Currency currency, BigDecimal amount) {

        BankAccount bankAccount = BankAccount.builder()
                .owner(user)
                .balance(amount)
                .currency(currency)
                .status("ACTIVE")
                .build();

        return bankAccountRepository.save(bankAccount);
    }

    private Currency createCurrencyTest() {

        Currency currency = Currency.builder()
                .code("USD")
                .title("US Dollar")
                .build();

        return currencyRepository.save(currency);
    }

    private Transaction createTransactionTest(BankAccount from, BankAccount to, BigDecimal amount) {

        Transaction transaction = Transaction.builder()
                .fromAccount(from)
                .toAccount(to)
                .amount(amount)
                .currency(from.getCurrency())
                .transactionType("SOME")
                .createdAt(Date.valueOf(LocalDate.now()))
                .description("Test transfer")
                .build();

        return transactionRepository.save(transaction);
    }


    @Test
    @SneakyThrows
    void findAllTransactionsValidRequestReturnTransactions() {

        Currency currency = createCurrencyTest();

        Credential credential = createCredentialsTest();

        User user = createUserTest(credential);

        BankAccount accountFrom = createBankAccountTest(user, currency, BigDecimal.valueOf(400.0));
        BankAccount accountTo = createBankAccountTest(user, currency, BigDecimal.valueOf(700.0));

        Transaction transaction = transactionRepository.save(createTransactionTest(accountFrom, accountTo, new BigDecimal("100")));

        mockMvc.perform(get("/api/v1/users/{userId}/accounts/{bankAccountId}/transactions", user.getId(), accountFrom.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions", hasSize(1)))
                .andExpect(jsonPath("$.transactions[0].id").value(transaction.getId().toString()))
                .andExpect(jsonPath("$.transactions[0].amount").value(100.00));
    }

    @Test
    @SneakyThrows
    void findTransactionByValidIdShouldReturnTransaction() {

        Currency currency = createCurrencyTest();

        Credential credential = createCredentialsTest();

        User user = createUserTest(credential);

        BankAccount accountFrom = createBankAccountTest(user, currency, BigDecimal.valueOf(400.0));
        BankAccount accountTo = createBankAccountTest(user, currency, BigDecimal.valueOf(700.0));

        Transaction transaction = createTransactionTest(accountFrom, accountTo, new BigDecimal("200"));

        mockMvc.perform(get("/api/users/{userId}/accounts/{bankAccountId}/transactions/{transactionId}",
                        user.getId(), accountFrom.getId(), transaction.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transaction.getId().toString()))
                .andExpect(jsonPath("$.transactionType").value("SOME"));
    }

    @Test
    @SneakyThrows
    void findAllTransactionsByInvalidAccountIdShouldReturnNotFound() {

        UUID randomUserId = UUID.randomUUID();
        UUID randomAccountId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/users/{userId}/accounts/{bankAccountId}/transactions", randomUserId, randomAccountId))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void findTransactionByInvalidIdShouldReturnNotFound() {

        Currency currency = createCurrencyTest();

        Credential credential = createCredentialsTest();

        User user = createUserTest(credential);

        BankAccount accountFrom = createBankAccountTest(user, currency, BigDecimal.valueOf(400.0));
        BankAccount accountTo = createBankAccountTest(user, currency, BigDecimal.valueOf(700.0));

        Transaction transaction = createTransactionTest(accountFrom, accountTo, new BigDecimal("200"));

        UUID randomTransactionId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/users/{userId}/accounts/{bankAccountId}/transactions/{transactionId}",
                        user.getId(), accountFrom.getId(), randomTransactionId))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void findTransactionInvalidAccountIdShouldReturnNotFound() {

        Currency currency = createCurrencyTest();

        Credential credential = createCredentialsTest();

        User user = createUserTest(credential);

        BankAccount accountFrom = createBankAccountTest(user, currency, BigDecimal.valueOf(400.0));
        BankAccount accountTo = createBankAccountTest(user, currency, BigDecimal.valueOf(700.0));

        Transaction transaction = createTransactionTest(accountFrom, accountTo, new BigDecimal("200"));

        mockMvc.perform(get("/api/v1/users/{userId}/accounts/{bankAccountId}/transactions/{transactionId}",
                        user.getId(), UUID.randomUUID(), transaction.getId()))
                .andExpect(status().isNotFound());
    }

}

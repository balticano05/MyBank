package com.pet.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.bank.dto.mapper.CurrencyMapper;
import com.pet.bank.dto.request.bank.account.BankAccountCreationRequestDto;
import com.pet.bank.dto.request.bank.account.BankAccountUpdateRequestDto;
import com.pet.bank.entity.BankAccount;
import com.pet.bank.entity.Credential;
import com.pet.bank.entity.Currency;
import com.pet.bank.entity.User;
import com.pet.bank.entity.enums.BankAccountStatus;
import com.pet.bank.repository.BankAccountRepository;
import com.pet.bank.repository.CredentialRepository;
import com.pet.bank.repository.CurrencyRepository;
import com.pet.bank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Transactional
public class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private User createUserWithCredentials() {

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
                .dateOfBirth(Date.valueOf(LocalDate.of(1990, 1, 1)))
                .credential(credential)
                .build();

        return userRepository.save(user);
    }

    private Currency createTestCurrency() {

        Currency currency = Currency.builder()
                .title("Dollar")
                .code("USD")
                .build();

        return currencyRepository.save(currency);
    }

    private BankAccount createTestBankAccount(User user) {

        BankAccount account = BankAccount.builder()
                .owner(user)
                .balance(new BigDecimal("1000.00"))
                .status(BankAccountStatus.ACTIVE.getValue())
                .currency(createTestCurrency())
                .build();

        return bankAccountRepository.save(account);
    }

    @Test
    @SneakyThrows
    void findAllBankAccountsTest() {

        User user = createUserWithCredentials();
        BankAccount bankAccount = createTestBankAccount(user);

        ArrayList<BankAccount> bankAccounts = new ArrayList<>();
        bankAccounts.add(bankAccount);

        user.setBankAccounts(bankAccounts);

        mockMvc.perform(get("/api/v1/users/{userId}/accounts", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bankAccounts", hasSize(1)))
                .andExpect(jsonPath("$.bankAccounts[0].balance").value("1000.0"));
    }

    @Test
    @SneakyThrows
    void findBankAccountByIdValidRequestShouldReturnBankAccountTest() {

        User user = createUserWithCredentials();
        BankAccount account = createTestBankAccount(user);

        mockMvc.perform(get("/api/v1/users/{userId}/accounts/{bankAccountId}", user.getId(), account.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(account.getId().toString()))
                .andExpect(jsonPath("$.balance").value("1000.0"));
    }

    @Test
    @SneakyThrows
    void findBankAccountByInvalidIdShouldReturnNotFoundTest() {

        User user = createUserWithCredentials();
        UUID randomId = UUID.randomUUID();

        mockMvc.perform(get("/api/v1/users/{userId}/accounts/{bankAccountId}", user.getId(), randomId))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createBankAccountForUserShouldReturnUserTest() {

        User user = createUserWithCredentials();

        Currency currency = createTestCurrency();

        BankAccountCreationRequestDto requestDto = BankAccountCreationRequestDto.builder()
                .balance(new BigDecimal("500.00"))
                .status("ACTIVE")
                .currency(CurrencyMapper.mapEntityToCurrencyDto(currency))
                .build();

        mockMvc.perform(post("/api/v1/users/{userId}/accounts", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        assertEquals(1, bankAccountRepository.count());
    }

    @Test
    @SneakyThrows
    void updateBankAccountShouldUpdateBankAccountTest() {

        User user = createUserWithCredentials();
        BankAccount account = createTestBankAccount(user);

        Currency currency = Currency.builder()
                .title("Euro")
                .code("EUR")
                .build();

        currencyRepository.save(currency);

        BankAccountUpdateRequestDto updateDto = BankAccountUpdateRequestDto.builder()
                .balance(new BigDecimal("2000.0"))
                .status("INACTIVE")
                .currency(CurrencyMapper.mapEntityToCurrencyDto(currency))
                .build();

        mockMvc.perform(put("/api/v1/users/{userId}/accounts/{bankAccountId}", user.getId(), account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$.balance").value("2000.0"))
                .andExpect(jsonPath("$.status").value("INACTIVE"));
    }


    @Test
    @SneakyThrows
    void deleteBankAccountShouldDeleteBankAccountTest() {

        User user = createUserWithCredentials();
        BankAccount account = createTestBankAccount(user);

        mockMvc.perform(delete("/api/v1/users/{userId}/accounts/{bankAccountId}", user.getId(), account.getId()))
                .andExpect(status().isNoContent());

        assertFalse(bankAccountRepository.existsById(account.getId()));
    }

    @Test
    @SneakyThrows
    void createBankAccountInvalidRequestShouldReturnBadRequestTest() {

        User user = createUserWithCredentials();

        BankAccountCreationRequestDto invalidRequest = BankAccountCreationRequestDto.builder()
                .balance(new BigDecimal("-100.00"))
                .status("INVALID_STATUS")
                .build();

        mockMvc.perform(post("/api/v1/users/{userId}/accounts", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

}
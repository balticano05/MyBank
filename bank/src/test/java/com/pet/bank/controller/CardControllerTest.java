package com.pet.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.bank.dto.request.card.CardCreationRequestDto;
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
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@Transactional
public class CardControllerTest {

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
    private CardRepository cardRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    private Currency createCurrencyTest(String code, String name) {

        Currency currency = Currency.builder()
                .code(code)
                .title(name)
                .build();

        return currencyRepository.save(currency);
    }

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

    private BankAccount createBankAccountTest(User user) {

        BankAccount account = BankAccount.builder()
                .owner(user)
                .balance(new BigDecimal("1000.00"))
                .status(BankAccountStatus.ACTIVE.getValue())
                .currency(createCurrencyTest("USD", "Dollar"))
                .build();

        return bankAccountRepository.save(account);
    }

    private Card createCardTest(BankAccount account) {

        Card card = Card.builder()
                .bankAccount(account)
                .cardNumber("4111111111111111")
                .cardType("some")
                .isActive(true)
                .build();

        return cardRepository.save(card);
    }

    @Test
    @SneakyThrows
    public void findAllCardsForAccountTest() {

        Credential credential = createCredentialsTest();
        User user = createUserTest(credential);
        BankAccount bankAccount = createBankAccountTest(user);

        Card card = createCardTest(bankAccount);

        mockMvc.perform(get("/api/v1/users/{userId}/{bankAccountId}/cards", user.getId(), bankAccount.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cards", hasSize(1)))
                .andExpect(jsonPath("$.cards[0].cardNumber").value("4111111111111111"));
    }

    @Test
    @SneakyThrows
    void createCardValidRequestShouldCreateCard() {

        Credential credential = createCredentialsTest();
        User user = createUserTest(credential);
        BankAccount bankAccount = createBankAccountTest(user);

        CardCreationRequestDto request = CardCreationRequestDto.builder()
                .cardType("SOME")
                .build();

        mockMvc.perform(post("/api/v1/users/{userId}/{bankAccountId}/cards", user.getId(), bankAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        assertEquals(1, cardRepository.count());
    }

    @Test
    @SneakyThrows
    void deleteCardExistingCardShouldDeleteCard() {

        Credential credential = createCredentialsTest();
        User user = createUserTest(credential);
        BankAccount bankAccount = createBankAccountTest(user);

        Card card = cardRepository.save(createCardTest(bankAccount));

        mockMvc.perform(delete("/api/v1/users/{userId}/{accountId}/cards/{cardId}",
                        user.getId(), bankAccount.getId(), card.getId()))
                .andExpect(status().isNoContent());

        assertFalse(cardRepository.existsById(card.getId()));
    }

    @Test
    @SneakyThrows
    void createCardInvalidRequestShouldReturnBadRequest() {

        Credential credential = createCredentialsTest();
        User user = createUserTest(credential);
        BankAccount bankAccount = createBankAccountTest(user);

        CardCreationRequestDto invalidRequest = CardCreationRequestDto.builder().build();

        mockMvc.perform(post("/api/v1/users/{userId}/{accountId}/cards", user.getId(), bankAccount.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    void deleteCardNoExistingCardShouldReturnNotFound() {

        Credential credential = createCredentialsTest();
        User user = createUserTest(credential);
        BankAccount bankAccount = createBankAccountTest(user);

        UUID randomId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/users/{userId}/{accountId}/cards/{cardId}",
                        user.getId(), bankAccount.getId(), randomId))
                .andExpect(status().isNotFound());
    }

}
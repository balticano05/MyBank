package com.pet.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.bank.dto.request.user.UserCreationRequestDto;
import com.pet.bank.dto.request.user.UserUpdateRequestDto;
import com.pet.bank.entity.Credential;
import com.pet.bank.entity.User;
import com.pet.bank.repository.CredentialRepository;
import com.pet.bank.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    private Credential createCredentialsTest() {
        return Credential.builder()
                .login("XASWE312")
                .email("asd@gmail.com")
                .password("123")
                .build();
    }

    private User createUserTest() {
        return User.builder()
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("+1234567890")
                .address("Street 1")
                .dateOfBirth(Date.valueOf(LocalDate.of(1980, 1, 3)))
                .build();
    }

    @Test
    @SneakyThrows
    public void findAllUsersTest() {

        Credential credential = createCredentialsTest();
        credentialRepository.save(credential);

        User user = createUserTest();
        user.setCredential(credential);

        userRepository.save(user);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].firstName").value("John"));
    }

    @Test
    @SneakyThrows
    void findUserByIdValidIdRequestShouldReturnUserTest() {

        Credential credential = createCredentialsTest();
        credentialRepository.save(credential);

        User user = createUserTest();
        user.setCredential(credential);

        user = userRepository.save(user);

        mockMvc.perform(get("/api/v1/users/{userId}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.user.firstName").value("John"));
    }

    @Test
    @SneakyThrows
    void createUserValidRequestShouldCreateUserTest() {

        Credential credential = createCredentialsTest();
        credential = credentialRepository.save(credential);

        UserCreationRequestDto requestDto = UserCreationRequestDto.builder()
                .firstName("Alice")
                .lastName("Smith")
                .credentialId(credential.getId())
                .phoneNumber("+0987654321")
                .address("Street 2")
                .dateOfBirth(Date.valueOf(LocalDate.of(2000, 12, 31)))
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());

        assertEquals(1, userRepository.count());
    }

    @Test
    @SneakyThrows
    void updateUserValidRequestShouldUpdateUser() {

        Credential credential = createCredentialsTest();
        credentialRepository.save(credential);

        User user = createUserTest();
        user.setCredential(credential);

        user = userRepository.save(user);

        UserUpdateRequestDto requestDto = UserUpdateRequestDto.builder()
                .firstName("Alice")
                .lastName("NoSmith")
                .build();

        mockMvc.perform(put("/api/v1/users/{userId}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("NoSmith"));

        User updatedUser = userRepository.findById(user.getId()).orElseThrow();
        assertEquals("NoSmith", updatedUser.getLastName());
    }

    @Test
    @SneakyThrows
    void deleteUserByValidIdShouldDeleteUser() {

        Credential credential = createCredentialsTest();
        credentialRepository.save(credential);

        User user = createUserTest();
        user.setCredential(credential);

        user = userRepository.save(user);

        mockMvc.perform(delete("/api/v1/users/{userId}", user.getId()))
                .andExpect(status().isNoContent());

        assertFalse(userRepository.existsById(user.getId()));
    }

    @Test
    @SneakyThrows
    void findUserByInvalidIdShouldReturnNotFound() {
        UUID invalidId = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/users/{userId}", invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void createUserInvalidRequestShouldReturnBadRequest() {

        Credential credential = createCredentialsTest();
        credentialRepository.save(credential);

        UserCreationRequestDto requestDto = UserCreationRequestDto.builder()
                .firstName("Alice")
                .lastName("Smith")
                .credentialId(credential.getId())
                .phoneNumber("+1241242142424142124fffffffffffffffffffffff")
                .address("Street 2")
                .dateOfBirth(Date.valueOf(LocalDate.of(2025, 12, 31)))
                .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

}
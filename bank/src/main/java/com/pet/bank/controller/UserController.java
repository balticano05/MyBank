package com.pet.bank.controller;

import com.pet.bank.dto.request.user.UserCreationRequestDto;
import com.pet.bank.dto.request.user.UserUpdateRequestDto;
import com.pet.bank.dto.response.user.AllUsersShortResponseDto;
import com.pet.bank.dto.response.user.UserCreationResponseDto;
import com.pet.bank.dto.response.user.UserFullResponseDto;
import com.pet.bank.dto.response.user.UserUpdateResponseDto;
import com.pet.bank.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public AllUsersShortResponseDto findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public UserFullResponseDto findUserById(@PathVariable UUID userId) {
        return userService.findUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserCreationResponseDto createUser(@Valid @RequestBody UserCreationRequestDto userCreationRequest) {
        return userService.createUser(userCreationRequest);
    }

    @PutMapping("/{userId}")
    public UserUpdateResponseDto createUpdate(
            @PathVariable UUID userId,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.updateUserById(userId, userUpdateRequestDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("userId") UUID userId) {
        userService.deleteUserById(userId);
    }

}
package com.pet.bank.controller;

import com.pet.bank.dto.request.user.UserSearchParametersRequest;
import com.pet.bank.dto.request.user.UserUpdateRequestDto;
import com.pet.bank.dto.response.user.AllUsersShortResponseDto;
import com.pet.bank.dto.response.user.UserUpdateResponseDto;
import com.pet.bank.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public AllUsersShortResponseDto findAllUsersByParameters(@Valid @RequestBody UserSearchParametersRequest userSearchParametersRequest){
        return userService.findAllUsersByParameters(userSearchParametersRequest);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    public UserUpdateResponseDto update(
            @PathVariable UUID userId,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return userService.updateUserById(userId, userUpdateRequestDto);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CONSULTANT')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public UUID deleteUserById(@PathVariable("userId") UUID userId) {
        return userService.deleteUserById(userId);
    }

}
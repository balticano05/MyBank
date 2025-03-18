package com.pet.bank.security.controller;

import com.pet.bank.security.dto.request.CredentialAuthRequestDto;
import com.pet.bank.security.dto.request.CredentialRegisterRequestDto;
import com.pet.bank.security.dto.response.CredentialAuthResponseDto;
import com.pet.bank.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CredentialAuthResponseDto register(@RequestBody CredentialRegisterRequestDto credentialAuthRequest) {
        return authService.register(credentialAuthRequest);
    }

    @PostMapping("/authenticate")
    public CredentialAuthResponseDto authenticate(@RequestBody CredentialAuthRequestDto credentialAuthRequestDto) {
        return authService.authenticate(credentialAuthRequestDto);
    }

}
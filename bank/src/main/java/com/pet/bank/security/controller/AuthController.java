package com.pet.bank.security.controller;

import com.pet.bank.annotations.Loggable;
import com.pet.bank.security.dto.request.CredentialAuthRequestDto;
import com.pet.bank.security.dto.request.CredentialRegisterRequestDto;
import com.pet.bank.security.dto.response.CredentialAuthResponseDto;
import com.pet.bank.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Loggable
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CredentialAuthResponseDto register(@RequestBody CredentialRegisterRequestDto credentialAuthRequest) {
        log.warn("Register endpoint called with request: {}", credentialAuthRequest);
        return authService.register(credentialAuthRequest);
    }

    @GetMapping("/authenticate")
    public CredentialAuthResponseDto authenticate(@RequestBody CredentialAuthRequestDto credentialAuthRequestDto) {
        log.warn("Authenticate endpoint called with request: {}", credentialAuthRequestDto);
        return authService.authenticate(credentialAuthRequestDto);
    }

}
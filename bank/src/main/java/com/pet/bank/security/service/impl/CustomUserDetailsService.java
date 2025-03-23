package com.pet.bank.security.service.impl;

import com.pet.bank.entity.Credential;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.repository.CredentialRepository;
import com.pet.bank.security.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CredentialRepository credentialRepository;
    private final DataValidationService dataValidationService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) {

        log.warn("Loading user by username: {}", login);

        dataValidationService.existsCredentialByLogin(login, HttpStatus.NOT_FOUND);
        Credential credential = credentialRepository.findCredentialByLogin(login);

        log.info("User loaded successfully: {}", login);

        return new CustomUserDetails(credential);
    }

}
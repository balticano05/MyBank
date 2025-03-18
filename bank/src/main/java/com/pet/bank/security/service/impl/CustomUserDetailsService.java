package com.pet.bank.security.service.impl;

import com.pet.bank.entity.Credential;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.repository.CredentialRepository;
import com.pet.bank.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final CredentialRepository credentialRepository;
    private final DataValidationService dataValidationService;

    @Override
    public UserDetails loadUserByUsername(String login) {

        dataValidationService.existsCredentialByLogin(login, HttpStatus.NOT_FOUND);
        Credential credential = credentialRepository.findCredentialByLogin(login);

        return new CustomUserDetails(credential);
    }

}
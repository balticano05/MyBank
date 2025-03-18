package com.pet.bank.security.service;

import com.pet.bank.security.dto.request.CredentialAuthRequestDto;
import com.pet.bank.security.dto.request.CredentialRegisterRequestDto;
import com.pet.bank.security.dto.response.CredentialAuthResponseDto;

public interface AuthService {

    CredentialAuthResponseDto register(CredentialRegisterRequestDto registerRequestDto);

    CredentialAuthResponseDto authenticate(CredentialAuthRequestDto credentialAuthRequestDto);

}
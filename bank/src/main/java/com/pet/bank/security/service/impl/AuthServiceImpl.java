package com.pet.bank.security.service.impl;

import com.pet.bank.entity.Credential;
import com.pet.bank.entity.Role;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.repository.CredentialRepository;
import com.pet.bank.repository.RoleRepository;
import com.pet.bank.repository.UserRepository;
import com.pet.bank.security.dto.request.CredentialAuthRequestDto;
import com.pet.bank.security.dto.request.CredentialRegisterRequestDto;
import com.pet.bank.security.dto.response.CredentialAuthResponseDto;
import com.pet.bank.security.service.AuthService;
import com.pet.bank.security.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final DataValidationService dataValidationService;
    private final CredentialRepository credentialRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public CredentialAuthResponseDto register(CredentialRegisterRequestDto registerRequestDto) {

        List<Role> validRoles = roleRepository.findAllById(registerRequestDto.getRoles().getRolesId());

        Credential credential = Credential.builder()
                .login(registerRequestDto.getLogin())
                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                .email(registerRequestDto.getEmail())
                .roles(validRoles)
                .build();

        credentialRepository.save(credential);

        return CredentialAuthResponseDto.builder()
                .token(jwtService.generateToken(credential.getLogin()))
                .build();
    }

    @Transactional
    public CredentialAuthResponseDto authenticate(CredentialAuthRequestDto credentialAuthRequestDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentialAuthRequestDto.getLogin(),
                        credentialAuthRequestDto.getPassword()
                )
        );

        dataValidationService.existsCredentialByLogin(credentialAuthRequestDto.getLogin(), HttpStatus.NOT_FOUND);

        Credential credential = credentialRepository.findCredentialByLogin(credentialAuthRequestDto.getLogin());
        String jwtToken = jwtService.generateToken(credential.getLogin());

        return CredentialAuthResponseDto.builder()
                .token(jwtToken)
                .build();
    }

}
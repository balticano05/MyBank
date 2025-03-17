package com.pet.bank.service.impl;

import com.pet.bank.dto.mapper.UserMapper;
import com.pet.bank.dto.request.user.UserCreationRequestDto;
import com.pet.bank.dto.request.user.UserUpdateRequestDto;
import com.pet.bank.dto.response.user.AllUsersShortResponseDto;
import com.pet.bank.dto.response.user.UserCreationResponseDto;
import com.pet.bank.dto.response.user.UserFullResponseDto;
import com.pet.bank.dto.response.user.UserUpdateResponseDto;
import com.pet.bank.entity.Credential;
import com.pet.bank.entity.User;
import com.pet.bank.exception.service.DataValidationService;
import com.pet.bank.repository.CredentialRepository;
import com.pet.bank.repository.UserRepository;
import com.pet.bank.service.UserService;
import com.pet.bank.utils.validator.FieldValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;
    private final DataValidationService dataValidationService;

    @Override
    public AllUsersShortResponseDto findAllUsers() {
        return UserMapper.mapEntitiesToAllUserShortResponseDto(userRepository.findAll());
    }

    @Override
    public UserFullResponseDto findUserById(UUID userId) {

        dataValidationService.existsUserById(userId, HttpStatus.NOT_FOUND);

        User foundUser = userRepository.findUserById(userId);

        return UserMapper.mapEntityToUserFullResponseDto(foundUser);
    }

    @Override
    @Transactional
    public UserCreationResponseDto createUser(UserCreationRequestDto userCreationRequest) {

        dataValidationService.existsCredentialById(userCreationRequest.getCredentialId(), HttpStatus.NOT_FOUND);
        dataValidationService.existsUserByCredentialId(userCreationRequest.getCredentialId(), HttpStatus.BAD_REQUEST);

        Credential foundCredential = credentialRepository.findCredentialById(userCreationRequest.getCredentialId());

        User newUser = UserMapper.mapUserCreationRequestDtoToEntity(userCreationRequest);
        newUser.setCredential(foundCredential);

        newUser = userRepository.save(newUser);

        return UserMapper.mapEntityToUserCreationResponseDto(newUser);
    }

    @Override
    @Transactional
    public UserUpdateResponseDto updateUserById(UUID userId, UserUpdateRequestDto userUpdateRequestDto) {

        dataValidationService.existsUserById(userId, HttpStatus.NOT_FOUND);
        User user = userRepository.findUserById(userId);

        if (FieldValidator.isNotEmpty(userUpdateRequestDto.getFirstName())) {
            user.setFirstName(userUpdateRequestDto.getFirstName());
        }

        if (FieldValidator.isNotEmpty(userUpdateRequestDto.getLastName())) {
            user.setLastName(userUpdateRequestDto.getLastName());
        }

        if (FieldValidator.isNotEmpty(userUpdateRequestDto.getPhoneNumber())) {
            user.setPhoneNumber(userUpdateRequestDto.getPhoneNumber());
        }

        if (FieldValidator.isNotEmpty(userUpdateRequestDto.getAddress())) {
            user.setAddress(userUpdateRequestDto.getAddress());
        }

        if (userUpdateRequestDto.getDateOfBirth() != null) {
            user.setDateOfBirth(userUpdateRequestDto.getDateOfBirth());
        }

        return UserMapper.mapEntityToUserUpdateResponseDto(user);
    }

    @Override
    public void deleteUserById(UUID userid) {

        dataValidationService.existsUserById(userid, HttpStatus.NOT_FOUND);

        userRepository.deleteById(userid);
    }

}
package com.pet.bank.service;

import com.pet.bank.dto.request.user.UserCreationRequestDto;
import com.pet.bank.dto.request.user.UserUpdateRequestDto;
import com.pet.bank.dto.response.user.AllUsersShortResponseDto;
import com.pet.bank.dto.response.user.UserCreationResponseDto;
import com.pet.bank.dto.response.user.UserFullResponseDto;
import com.pet.bank.dto.response.user.UserUpdateResponseDto;

import java.util.UUID;

public interface UserService {

    AllUsersShortResponseDto findAllUsers();

    UserFullResponseDto findUserById(UUID userId);

    UserCreationResponseDto createUser(UserCreationRequestDto userCreationRequest);

    UserUpdateResponseDto updateUserById(UUID userId, UserUpdateRequestDto userUpdateRequestDto);

    void deleteUserById(UUID userId);

}
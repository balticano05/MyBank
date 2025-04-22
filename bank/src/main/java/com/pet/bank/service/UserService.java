package com.pet.bank.service;

import com.pet.bank.dto.request.user.UserSearchParametersRequest;
import com.pet.bank.dto.request.user.UserUpdateRequestDto;
import com.pet.bank.dto.response.user.AllUsersShortResponseDto;
import com.pet.bank.dto.response.user.UserUpdateResponseDto;

import java.util.UUID;

public interface UserService {

    AllUsersShortResponseDto findAllUsersByParameters(UserSearchParametersRequest userSearchParametersRequest);

    UserUpdateResponseDto updateUserById(UUID userId, UserUpdateRequestDto userUpdateRequestDto);

    UUID deleteUserById(UUID userId);

}
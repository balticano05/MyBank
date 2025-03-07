package com.pet.bank.dto.mapper;

import com.pet.bank.dto.request.user.UserCreationRequestDto;
import com.pet.bank.dto.response.user.AllUsersShortResponseDto;
import com.pet.bank.dto.response.user.UserCreationResponseDto;
import com.pet.bank.dto.response.user.UserFullResponseDto;
import com.pet.bank.dto.response.user.UserUpdateResponseDto;
import com.pet.bank.dto.response.user.nested.UserFullDto;
import com.pet.bank.dto.response.user.nested.UserShortDto;
import com.pet.bank.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserShortDto mapEntityToUserShortDto(User source){
        return UserShortDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .address(source.getAddress())
                .dateOfBirth(source.getDateOfBirth())
                .build();
    }

    public static UserCreationResponseDto mapEntityToUserCreationResponseDto(User source){
        return UserCreationResponseDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .address(source.getAddress())
                .dateOfBirth(source.getDateOfBirth())
                .build();
    }

    public static UserUpdateResponseDto mapEntityToUserUpdateResponseDto(User source){
        return UserUpdateResponseDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .address(source.getAddress())
                .dateOfBirth(source.getDateOfBirth())
                .build();
    }

    public static User mapUserCreationRequestDtoToEntity(UserCreationRequestDto source){
        return User.builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .address(source.getAddress())
                .dateOfBirth(source.getDateOfBirth())
                .build();

    }

    public static UserFullResponseDto mapEntityToUserFullResponseDto(User source){
        return UserFullResponseDto.builder()
                .user(mapEntityToUserFullDto(source))
                .build();
    }

    private static UserFullDto mapEntityToUserFullDto(User source){
        return UserFullDto.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .phoneNumber(source.getPhoneNumber())
                .address(source.getAddress())
                .dateOfBirth(source.getDateOfBirth())
                .bankAccounts(BankAccountMapper.mapEntitiesToListBankAccountDto(source.getBankAccounts()))
                .loans(LoanMapper.mapEntitiesToListLoanDto(source.getLoans()))
                .build();
    }

    public static AllUsersShortResponseDto mapEntitiesToAllUserShortResponseDto(List<User> source){
        return AllUsersShortResponseDto.builder()
                .users(mapEntitiesToUserShortDtoList(source))
                .build();
    }

    private static List<UserShortDto> mapEntitiesToUserShortDtoList(List<User> source){

        if(source == null)
            return new ArrayList<>();

        return source.stream()
                .map(UserMapper::mapEntityToUserShortDto)
                .toList();
    }

}
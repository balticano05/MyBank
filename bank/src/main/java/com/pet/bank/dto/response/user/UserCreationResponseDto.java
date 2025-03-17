package com.pet.bank.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationResponseDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private Date dateOfBirth;

}
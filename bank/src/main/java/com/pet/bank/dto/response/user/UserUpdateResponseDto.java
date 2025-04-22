package com.pet.bank.dto.response.user;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateResponseDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private Date dateOfBirth;

}
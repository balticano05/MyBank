package com.pet.bank.dto.response.user.nested;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto extends RepresentationModel<UserShortDto> {

    private UUID id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private Date dateOfBirth;

}
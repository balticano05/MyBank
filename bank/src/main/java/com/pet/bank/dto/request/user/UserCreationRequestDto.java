package com.pet.bank.dto.request.user;

import com.pet.bank.utils.validator.ValidationConstants;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequestDto {

    @NotBlank(message = "First name is required")
    @Size(
            min = ValidationConstants.MIN_NAME_LENGTH,
            max = ValidationConstants.MAX_NAME_LENGTH,
            message = ValidationConstants.NAME_LENGTH_MESSAGE
    )
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(
            min = ValidationConstants.MIN_NAME_LENGTH,
            max = ValidationConstants.MAX_NAME_LENGTH,
            message = ValidationConstants.NAME_LENGTH_MESSAGE
    )
    private String lastName;

    @NotNull(message = "Credential ID is required")
    private UUID credentialId;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = ValidationConstants.PHONE_REGEX, message = ValidationConstants.PHONE_MESSAGE)
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Size(max = ValidationConstants.MAX_ADDRESS_LENGTH, message = ValidationConstants.ADDRESS_LENGTH_MESSAGE)
    private String address;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

}
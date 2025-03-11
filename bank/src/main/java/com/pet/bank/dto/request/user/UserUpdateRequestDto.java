package com.pet.bank.dto.request.user;

import com.pet.bank.utils.validator.ValidationConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {

    @Size(
            min = ValidationConstants.MIN_NAME_LENGTH,
            max = ValidationConstants.MAX_NAME_LENGTH,
            message = ValidationConstants.NAME_LENGTH_MESSAGE
    )
    private String firstName;

    @Size(
            min = ValidationConstants.MIN_NAME_LENGTH,
            max = ValidationConstants.MAX_NAME_LENGTH,
            message = ValidationConstants.NAME_LENGTH_MESSAGE
    )
    private String lastName;

    @Pattern(regexp = ValidationConstants.PHONE_REGEX, message = ValidationConstants.PHONE_MESSAGE)
    private String phoneNumber;

    @Size(max = ValidationConstants.MAX_ADDRESS_LENGTH, message = ValidationConstants.ADDRESS_LENGTH_MESSAGE)
    private String address;

    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

}